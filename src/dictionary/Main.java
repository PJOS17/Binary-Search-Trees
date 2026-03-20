package dictionary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private final BinaryTree<Association<String, String>> bst = new BinaryTree<>();

    private void parseLine(String line) {
        line = line.trim();
        if (line.isEmpty() || !line.startsWith("("))
            return;
        line = line.substring(1, line.lastIndexOf(')'));
        String[] parts = line.split(",", 2);
        if (parts.length != 2)
            return;
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

    public void printInOrder() {
        List<Association<String, String>> sorted = bst.inOrder();
        System.out.println("\n=== Dictionary (in-order by English word) ===");
        for (Association<String, String> assoc : sorted) {
            System.out.print(assoc + " ");
        }
        System.out.println();
    }

    private String translate(String word) {
        Association<String, String> found = bst.search(new Association<>(word, null));
        return (found != null) ? found.getValue() : null;
    }

    private String[] tokenParts(String token) {
        int end = token.length();
        StringBuilder punctuation = new StringBuilder();
        while (end > 0 && !Character.isLetterOrDigit(token.charAt(end - 1))) {
            punctuation.insert(0, token.charAt(end - 1));
            end--;
        }
        return new String[] { token.substring(0, end), punctuation.toString() };
    }

    public void translateFile(String path) throws IOException {

        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        System.out.println("\n=== Traduccion (palabras no encontradas entre *asteriscos*) ===");
        for (String ln : lines) {
            StringBuilder sb = new StringBuilder();
            for (String token : ln.split("(?<=\\s)|(?=\\s)")) {
                if (token.trim().isEmpty()) {
                    sb.append(token);
                    continue;
                }
                String[] p = tokenParts(token);
                String word = p[0], suffix = p[1];
                if (word.isEmpty()) {
                    sb.append(suffix);
                    continue;
                }
                String es = translate(word.toLowerCase());
                sb.append(es != null ? es : "*" + word + "*").append(suffix);
            }
            System.out.println(sb);
        }

        System.out.println("\n========================================");
        System.out.println("=== Texto traducido (version legible) ===");
        System.out.println("========================================");
        for (String ln : lines) {
            StringBuilder sb = new StringBuilder();
            for (String token : ln.split("(?<=\\s)|(?=\\s)")) {
                if (token.trim().isEmpty()) {
                    sb.append(token);
                    continue;
                }
                String[] p = tokenParts(token);
                String word = p[0], suffix = p[1];
                if (word.isEmpty()) {
                    sb.append(suffix);
                    continue;
                }
                String es = translate(word.toLowerCase());

                sb.append(es != null ? es : word).append(suffix);
            }
            System.out.println(sb);
        }
        System.out.println("========================================");
    }

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
