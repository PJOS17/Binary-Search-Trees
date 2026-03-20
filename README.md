# Hoja de Trabajo #7 – Binary Search Trees
## Diccionario Inglés–Español con BST

### Estructura del proyecto

```
bst_dictionary/
├── src/dictionary/
│   ├── Association.java      ← Clase genérica Association<K,V>
│   ├── BinaryTree.java       ← Clase genérica BinaryTree<E> (BST)
│   └── Main.java             ← Programa principal
├── test/dictionary/
│   └── BinaryTreeTest.java   ← Pruebas unitarias (sin dependencias externas)
├── diccionario.txt           ← Asociaciones inglés-español
├── texto.txt                 ← Texto a traducir
├── compilar_y_ejecutar.bat   ← Script todo-en-uno para Windows
└── README.md
```

---

### Cómo ejecutar en Windows (sin Maven)

Solo necesitas **Java instalado** (JDK 8 o superior).

#### Opción A — Doble clic en el script

Haz doble clic en **`compilar_y_ejecutar.bat`**.  
Compila todo, corre las pruebas y luego traduce el texto.

#### Opción B — Línea de comandos manual

```cmd
:: Desde la raíz del proyecto
mkdir out

:: Compilar
javac -d out src\dictionary\Association.java src\dictionary\BinaryTree.java src\dictionary\Main.java
javac -cp out -d out test\dictionary\BinaryTreeTest.java

:: Pruebas unitarias
java -cp out dictionary.BinaryTreeTest

:: Programa principal
java -cp out dictionary.Main diccionario.txt texto.txt
```

---

### Clases implementadas

#### `Association<K, V>`
Par llave-valor genérico que implementa `Comparable<Association<K,V>>` comparando
por llave. Permite insertar y buscar directamente en el BST.

#### `BinaryTree<E>`
Árbol binario de búsqueda genérico.

| Método | Descripción |
|---|---|
| `insert(E)` | Inserta; ignora duplicados (mantiene el primer valor) |
| `search(E)` | Devuelve el elemento o `null` si no existe |
| `inOrder()` | Recorrido in-order → `List<E>` ordenada |
| `size()` / `isEmpty()` | Estado del árbol |

#### `Main`
1. Lee `diccionario.txt` y construye el BST.
2. Imprime las asociaciones en orden (recorrido in-order).
3. Lee `texto.txt` y traduce cada palabra. Las no encontradas se envuelven en `*asteriscos*`.

---

### Referencia
Bailey, D. A. (2003). *Java Structures: Data Structures in Java for the Principled Programmer*. McGraw-Hill. Chapter 12.  
Wikipedia – Binary search tree: https://en.wikipedia.org/wiki/Binary_search_tree
"# Binary-Search-Trees" 
