package dictionary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main program for Hoja de Trabajo #7 – English-Spanish BST Dictionary.
 */
public class Main {

    private final BinaryTree<Association<String, String>> bst = new BinaryTree<>();

    // -------------------------------------------------------------------------
    // Dictionary loading
    // -------------------------------------------------------------------------

    private void parseLine(String line) {
        line = line.trim();
        if (line.isEmpty() || !line.startsWith("(")) return;
        line = line.substring(1, line.lastIndexOf(')'));
        String[] parts = line.split(",", 2);
        if (parts.length != 2) return;
        String english = parts[0].trim().toLowerCase();
        String spanish = parts[1].trim();
        if (!english.isEmpty() && !spanish.isEmpty()) {
            bst.insert(new Association<>(english, spanish));
        }
    }

    public void loadDictionary(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
        reader.close();
        System.out.println("Dictionary loaded. Total unique entries: " + bst.size());
    }

    // -------------------------------------------------------------------------
    // In-order print
    // -------------------------------------------------------------------------

    public void printInOrder() {
        List<Association<String, String>> sorted = bst.inOrder();
        System.out.println("\n=== Dictionary (in-order by English word) ===");
        for (Association<String, String> assoc : sorted) {
            System.out.print(assoc + " ");
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Translation helpers
    // -------------------------------------------------------------------------

    private String translate(String word) {
        Association<String, String> found = bst.search(new Association<>(word, null));
        return (found != null) ? found.getValue() : null;
    }

    /**
     * Splits a token into [word, trailingPunctuation].
     */
    private String[] tokenParts(String token) {
        int end = token.length();
        StringBuilder punctuation = new StringBuilder();
        while (end > 0 && !Character.isLetterOrDigit(token.charAt(end - 1))) {
            punctuation.insert(0, token.charAt(end - 1));
            end--;
        }
        return new String[]{ token.substring(0, end), punctuation.toString() };
    }

    // -------------------------------------------------------------------------
    // translateFile: annotated + clean readable output
    // -------------------------------------------------------------------------

    public void translateFile(String path) throws IOException {
        // Read all lines into memory
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        // --- Section 1: annotated (asterisks on unknown words, required by assignment) ---
        System.out.println("\n=== Traduccion (palabras no encontradas entre *asteriscos*) ===");
        for (String ln : lines) {
            StringBuilder sb = new StringBuilder();
            for (String token : ln.split("(?<=\\s)|(?=\\s)")) {
                if (token.trim().isEmpty()) { sb.append(token); continue; }
                String[] p = tokenParts(token);
                String word = p[0], suffix = p[1];
                if (word.isEmpty()) { sb.append(suffix); continue; }
                String es = translate(word.toLowerCase());
                sb.append(es != null ? es : "*" + word + "*").append(suffix);
            }
            System.out.println(sb);
        }

        // --- Section 2: clean readable translation ---
        System.out.println("\n========================================");
        System.out.println("=== Texto traducido (version legible) ===");
        System.out.println("========================================");
        for (String ln : lines) {
            StringBuilder sb = new StringBuilder();
            for (String token : ln.split("(?<=\\s)|(?=\\s)")) {
                if (token.trim().isEmpty()) { sb.append(token); continue; }
                String[] p = tokenParts(token);
                String word = p[0], suffix = p[1];
                if (word.isEmpty()) { sb.append(suffix); continue; }
                String es = translate(word.toLowerCase());
                // Unknown words kept in English so the text stays readable
                sb.append(es != null ? es : word).append(suffix);
            }
            System.out.println(sb);
        }
        System.out.println("========================================");
    }

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        String dictPath = args.length > 0 ? args[0] : "diccionario.txt";
        String textPath = args.length > 1 ? args[1] : "texto.txt";

        Main app = new Main();
        try {
            app.loadDictionary(dictPath);
            app.printInOrder();
            app.translateFile(textPath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
