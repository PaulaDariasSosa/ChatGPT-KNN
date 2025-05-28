package vectores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una matriz de números reales utilizando una lista de objetos {@code Vector}.
 * Proporciona métodos para operaciones básicas como lectura, escritura, transposición, eliminación de filas/columnas,
 * normalización y multiplicación de matrices.
 */
public class Matriz {
    private List<Vector> matrix;
    private int numRows;
    private int numCols;
    private boolean isTransposed;

    /**
     * Constructor por defecto. Crea una matriz 1x1 con el valor 0.
     */
    public Matriz() {
        this(1, 1);
        matrix = new ArrayList<>();
        matrix.add(new Vector(1));
        isTransposed = false;
    }

    /**
     * Crea una matriz de tamaño m x n con todos los elementos inicializados a 0.
     *
     * @param m número de filas
     * @param n número de columnas
     */
    public Matriz(int m, int n) {
        this.numRows = m;
        this.numCols = n;
        matrix = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            matrix.add(i, new Vector(n));
        }
        isTransposed = false;
    }

    /**
     * Crea una matriz de tamaño m x n a partir de una matriz bidimensional de valores.
     *
     * @param m número de filas
     * @param n número de columnas
     * @param coef matriz de coeficientes
     */
    public Matriz(int m, int n, double[][] coef) {
        this(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Vector aux = matrix.get(i);
                aux.set(j, coef[i][j]);
                matrix.set(i, aux);
            }
        }
        isTransposed = false;
    }

    /**
     * Crea una matriz a partir de una lista de objetos {@code Vector}.
     *
     * @param vectors lista de vectores
     * @throws IllegalArgumentException si la lista está vacía o es nula
     */
    public Matriz(List<Vector> vectors) {
        if (vectors == null || vectors.isEmpty()) {
            throw new IllegalArgumentException("ArrayList<Vector> no puede estar vacío");
        }
        this.numRows = vectors.size();
        this.numCols = vectors.get(0).size();
        matrix = vectors;
    }

    /**
     * Devuelve el número de filas de la matriz (considerando si está transpuesta).
     */
    public int getNumRows() {
        return isTransposed ? numCols : numRows;
    }

    /**
     * Devuelve el número de columnas de la matriz (considerando si está transpuesta).
     */
    public int getNumCols() {
        return isTransposed ? numRows : numCols;
    }

    /**
     * Imprime la matriz en consola utilizando el método {@code print()} de la clase {@code Vector}.
     */
    public void print() {
        for (int i = 0; i < numRows; i++) {
            matrix.get(i).print();
        }
    }

    /**
     * Multiplica dos matrices y devuelve el resultado como una nueva matriz.
     *
     * @param a primera matriz
     * @param b segunda matriz
     * @return matriz resultado
     * @throws IllegalArgumentException si las dimensiones no permiten la multiplicación
     */
    public static Matriz multiply(Matriz a, Matriz b) {
        if (a.getNumCols() != b.getNumRows()) {
            throw new IllegalArgumentException("Número de columnas de A no coincide con el número de filas de B");
        }
        Matriz result = new Matriz(a.getNumRows(), b.getNumCols());
        for (int i = 0; i < a.getNumRows(); i++) {
            for (int j = 0; j < b.getNumCols(); j++) {
                double value = a.matrix.get(i).productoEscalar(getColumn(b.matrix, j));
                Vector aux = result.matrix.get(i);
                aux.set(j, value);
                result.matrix.set(i, aux);
            }
        }
        return result;
    }

    /**
     * Devuelve la columna indicada de la matriz como un objeto {@code Vector}.
     *
     * @param matrix lista de vectores
     * @param colIndex índice de columna
     * @return vector que representa la columna
     */
    private static Vector getColumn(List<Vector> matrix, int colIndex) {
        Vector column = new Vector();
        for (Vector row : matrix) {
            column.add(row.get(colIndex));
        }
        return column;
    }

    /**
     * Lee una matriz desde un archivo de texto.
     *
     * @param filename nombre del archivo
     * @return una nueva instancia de {@code Matriz}
     * @throws IOException si hay un error de lectura
     */
    public Matriz read(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int m = Integer.parseInt(reader.readLine());
            int n = Integer.parseInt(reader.readLine());
            double[][] coef = new double[m][n];
            for (int i = 0; i < m; i++) {
                String[] lineValues = reader.readLine().split(" ");
                for (int j = 0; j < n; j++) {
                    coef[i][j] = Double.parseDouble(lineValues[j]);
                }
            }
            return new Matriz(m, n, coef);
        }
    }

    /**
     * Escribe el contenido de la matriz en un archivo de texto.
     *
     * @param filename nombre del archivo
     * @throws IOException si hay un error de escritura
     */
    public void write(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(numRows + "\n");
            writer.write(numCols + "\n");
            for (int i = 0; i < numRows; i++) {
                writer.write(matrix.get(i).toString().replace("[", "").replace("]", "").replace(",", "") + "\n");
            }
        }
    }

    /**
     * Devuelve el valor almacenado en la posición (x, y).
     *
     * @param x fila
     * @param y columna
     * @return valor de la celda
     * @throws IndexOutOfBoundsException si los índices están fuera de rango
     */
    public double get(int x, int y) {
        if (x < 0 || x >= numRows || y < 0 || y >= numCols) {
            throw new IndexOutOfBoundsException("Los índices están fuera del rango de la matriz.");
        }
        return matrix.get(x).get(y);
    }

    /**
     * Compara si dos matrices son iguales en contenido.
     *
     * @param other otra matriz
     * @return {@code true} si son iguales, {@code false} en caso contrario
     */
    public boolean equals(Matriz other) {
        if (numRows != other.numRows || numCols != other.numCols) return false;
        for (int i = 0; i < numRows; i++) {
            if (!this.matrix.get(i).equals(other.matrix.get(i))) return false;
        }
        return true;
    }

    /**
     * Transpone la matriz, intercambiando filas por columnas.
     */
    public void transpose() {
        int temp = numRows;
        numRows = numCols;
        numCols = temp;

        ArrayList<Vector> transposedMatrix = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            transposedMatrix.add(new Vector(numCols));
            for (int j = 0; j < numCols; j++) {
                transposedMatrix.get(i).set(j, matrix.get(j).get(i));
            }
        }
        matrix = transposedMatrix;
        isTransposed = !isTransposed;
    }

    /**
     * Elimina una fila de la matriz.
     *
     * @param indice índice de la fila a eliminar
     */
    public void deleteRows(int indice) {
        matrix.remove(indice);
        this.numRows -= 1;
    }

    /**
     * Elimina una columna de la matriz.
     * Internamente transpone la matriz, elimina la fila y vuelve a transponerla.
     *
     * @param indice índice de la columna a eliminar
     */
    public void deleteCols(int indice) {
        this.transpose();
        this.deleteRows(indice);
        this.numCols -= 1;
        this.transpose();
    }

    /**
     * Aumenta el número de filas en la matriz (sin añadir nuevos datos).
     */
    public void addRows() {
        this.numRows += 1;
    }

    /**
     * Aumenta el número de columnas en la matriz (sin añadir nuevos datos).
     */
    public void addCols() {
        this.numCols += 1;
    }

    /**
     * Normaliza cada columna de la matriz en el rango [0, 1].
     *
     * @return lista de vectores normalizados
     */
    public List<Vector> normalizar() {
        this.transpose();
        List<Vector> nueva = this.matrix;
        for (Vector fila : nueva) fila.normalize();
        this.transpose();
        return nueva;
    }

    /**
     * Establece el valor de la celda en la posición (i, j).
     *
     * @param i índice de fila
     * @param j índice de columna
     * @param valor nuevo valor
     */
    public void set(int i, int j, double valor) {
        Vector fila = matrix.get(i);
        fila.set(j, valor);
        matrix.set(i, fila);
    }
}
