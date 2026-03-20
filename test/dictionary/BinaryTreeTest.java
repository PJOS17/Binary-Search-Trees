package dictionary;

import java.util.List;

/**
 * Pruebas unitarias para BinaryTree e Association.
 * No requiere JUnit — corre con: java -cp out dictionary.BinaryTreeTest
 */
public class BinaryTreeTest {

    private static int passed = 0;
    private static int failed = 0;

    private static void assertTrue(String name, boolean condition) {
        if (condition) { System.out.println("  [PASS] " + name); passed++; }
        else           { System.out.println("  [FAIL] " + name); failed++; }
    }
    private static void assertEquals(String name, Object expected, Object actual) {
        boolean ok = (expected == null && actual == null)
                  || (expected != null && expected.equals(actual));
        if (ok) { System.out.println("  [PASS] " + name); passed++; }
        else    { System.out.println("  [FAIL] " + name + "  esperado=<" + expected + ">  obtenido=<" + actual + ">"); failed++; }
    }
    private static void assertNull(String name, Object obj)    { assertTrue(name, obj == null);  }
    private static void assertNotNull(String name, Object obj) { assertTrue(name, obj != null);  }

    private static Association<String,String> assoc(String en, String es) { return new Association<>(en, es); }
    private static Association<String,String> key(String en)               { return new Association<>(en, null); }

    static void testInsert() {
        System.out.println("\n--- Pruebas: insert ---");
        BinaryTree<Association<String,String>> t = new BinaryTree<>();
        assertTrue("insert_arbol_vacio_al_inicio", t.isEmpty());
        t.insert(assoc("house","casa"));
        assertEquals("insert_un_elemento_size1", 1, t.size());
        t.insert(assoc("dog","perro"));
        t.insert(assoc("woman","mujer"));
        assertEquals("insert_tres_elementos_size3", 3, t.size());
        t.insert(assoc("house","hogar"));
        assertEquals("insert_duplicado_size_no_aumenta", 3, t.size());
        assertEquals("insert_duplicado_valor_original", "casa", t.search(key("house")).getValue());

        BinaryTree<Association<String,String>> t2 = new BinaryTree<>();
        t2.insert(assoc("town","pueblo")); t2.insert(assoc("house","casa"));
        t2.insert(assoc("dog","perro"));   t2.insert(assoc("woman","mujer"));
        t2.insert(assoc("homework","tarea")); t2.insert(assoc("yes","si"));
        List<Association<String,String>> s = t2.inOrder();
        String[] exp = {"dog","homework","house","town","woman","yes"};
        boolean ok = s.size() == exp.length;
        for (int i = 0; ok && i < exp.length; i++) ok = s.get(i).getKey().equals(exp[i]);
        assertTrue("insert_inorder_ordenado_correctamente", ok);
    }

    static void testSearch() {
        System.out.println("\n--- Pruebas: search ---");
        BinaryTree<Association<String,String>> t = new BinaryTree<>();
        t.insert(assoc("house","casa")); t.insert(assoc("dog","perro"));
        t.insert(assoc("homework","tarea")); t.insert(assoc("woman","mujer"));
        t.insert(assoc("town","pueblo")); t.insert(assoc("yes","si"));

        assertNull("search_arbol_vacio_retorna_null", new BinaryTree<Association<String,String>>().search(key("house")));
        assertNull("search_clave_inexistente_retorna_null", t.search(key("unicorn")));
        assertNotNull("search_raiz_encontrada", t.search(key("house")));
        assertEquals("search_raiz_valor_correcto", "casa", t.search(key("house")).getValue());
        assertEquals("search_subarbol_izquierdo", "perro", t.search(key("dog")).getValue());
        assertEquals("search_izquierdo_derecho",  "tarea", t.search(key("homework")).getValue());
        assertEquals("search_subarbol_derecho",   "mujer", t.search(key("woman")).getValue());
        assertEquals("search_hoja_derecha",       "si",    t.search(key("yes")).getValue());
        assertNull("search_mayuscula_no_encuentra", t.search(key("House")));
    }

    static void testAssociation() {
        System.out.println("\n--- Pruebas: Association ---");
        Association<String,String> a = assoc("apple","manzana");
        Association<String,String> b = assoc("zebra","cebra");
        Association<String,String> c = assoc("apple","manzana");
        assertTrue("association_compareTo_menor",   a.compareTo(b) < 0);
        assertTrue("association_compareTo_mayor",   b.compareTo(a) > 0);
        assertEquals("association_compareTo_igual", 0, a.compareTo(c));
        assertEquals("association_toString", "(apple, manzana)", a.toString());
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Pruebas Unitarias – BinaryTree / Association");
        System.out.println("========================================");
        testInsert(); testSearch(); testAssociation();
        System.out.println("\n========================================");
        System.out.printf("  Resultado: %d PASARON  |  %d FALLARON%n", passed, failed);
        System.out.println("========================================");
        if (failed > 0) System.exit(1);
    }
}
