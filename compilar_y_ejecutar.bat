@echo off
echo ========================================
echo   Compilando proyecto...
echo ========================================

if not exist out mkdir out

javac -source 8 -target 8 -d out src\dictionary\Association.java src\dictionary\BinaryTree.java src\dictionary\Main.java
if errorlevel 1 (
    echo ERROR: Fallo la compilacion de clases principales.
    pause
    exit /b 1
)

javac -source 8 -target 8 -cp out -d out test\dictionary\BinaryTreeTest.java
if errorlevel 1 (
    echo ERROR: Fallo la compilacion de las pruebas.
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Corriendo pruebas unitarias...
echo ========================================
java -cp out dictionary.BinaryTreeTest

echo.
echo ========================================
echo   Corriendo programa principal...
echo ========================================
java -cp out dictionary.Main diccionario.txt texto.txt

pause
