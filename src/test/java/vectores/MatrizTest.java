package vectores;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @brief Clase de pruebas unitarias para la clase Matriz.
 *
 * Esta clase contiene test para verificar el correcto funcionamiento
 * de los constructores, métodos de acceso, mutadores, operaciones
 * básicas, y funcionalidades de la clase Matriz.
 */
class MatrizTest {

    private Matriz matriz;

    /**
     * @brief Inicializa una matriz de 2x2 con valores específicos antes de cada test.
     */
    @BeforeEach
    void setUp() {
        matriz = new Matriz(2, 2);
        matriz.set(0, 0, 1.0);
        matriz.set(0, 1, 2.0);
        matriz.set(1, 0, 3.0);
        matriz.set(1, 1, 4.0);
    }

    /**
     * @brief Test del constructor por defecto, que debe crear una matriz 1x1.
     */
    @Test
    void testConstructorDefault() {
        Matriz m = new Matriz();
        assertEquals(1, m.getNumRows());
        assertEquals(1, m.getNumCols());
    }

    /**
     * @brief Test del constructor con filas, columnas y valores iniciales.
     */
    @Test
    void testConstructorCoef() {
        double[][] values = {{1.0, 2.0}, {3.0, 4.0}};
        Matriz m = new Matriz(2, 2, values);
        assertEquals(4.0, m.get(1, 1));
    }

    /**
     * @brief Test del constructor que recibe una lista de vectores.
     */
    @Test
    void testConstructorFromVectorList() {
        List<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(new double[]{1, 2}));
        vectors.add(new Vector(new double[]{3, 4}));
        Matriz m = new Matriz(vectors);
        assertEquals(3.0, m.get(1, 0));
    }

    /**
     * @brief Test de los métodos get y set para modificar y obtener valores de la matriz.
     */
    @Test
    void testGetSet() {
        matriz.set(0, 0, 9.0);
        assertEquals(9.0, matriz.get(0, 0));
    }

    /**
     * @brief Test del método transpose para verificar la matriz transpuesta.
     */
    @Test
    void testTranspose() {
        double[][] datos = {
                {1, 2},
                {3, 4}
        };
        Matriz matriz = new Matriz(2, 2, datos);
        matriz.transpose();

        assertEquals(2, matriz.getNumRows());
        assertEquals(2, matriz.getNumCols());
        assertEquals(1, matriz.get(0, 0));
        assertEquals(3, matriz.get(0, 1));
    }

    /**
     * @brief Test de la multiplicación de matrices.
     */
    @Test
    void testMultiply() {
        Matriz result = Matriz.multiply(matriz, matriz);
        assertEquals(7.0, result.get(0, 0));
        assertEquals(10.0, result.get(0, 1));
    }

    /**
     * @brief Test del método equals para comparar matrices.
     */
    @Test
    void testEquals() {
        Matriz copia = new Matriz(2, 2);
        copia.set(0, 0, 1.0);
        copia.set(0, 1, 2.0);
        copia.set(1, 0, 3.0);
        copia.set(1, 1, 4.0);
        assertTrue(matriz.equals(copia));
    }

    /**
     * @brief Test del método deleteRows para eliminar una fila.
     */
    @Test
    void testDeleteRows() {
        matriz.deleteRows(0);
        assertEquals(1, matriz.getNumRows());
    }

    /**
     * @brief Test del método deleteCols para eliminar una columna.
     */
    @Test
    void testDeleteCols() {
        double[][] datos = {
                {1, 2},
                {3, 4}
        };
        Matriz matriz = new Matriz(2, 2, datos);
        matriz.deleteCols(1);

        assertEquals(1, matriz.getNumCols());
        assertEquals(1.0, matriz.get(0, 0));
    }

    /**
     * @brief Test para agregar filas y columnas a la matriz.
     */
    @Test
    void testAddRowsCols() {
        matriz.addRows();
        matriz.addCols();
        assertEquals(3, matriz.getNumRows());
        assertEquals(3, matriz.getNumCols());
    }

    /**
     * @brief Test del método normalizar que normaliza los vectores de la matriz.
     */
    @Test
    void testNormalize() {
        double[][] datos = {
                {1, 2},
                {3, 4}
        };
        Matriz matriz = new Matriz(2, 2, datos);
        List<Vector> normalizada = matriz.normalizar();

        for (Vector v : normalizada) {
            double max = v.getMax();
            double min = v.getMin();
            assertTrue(max <= 1.0);
            assertTrue(min >= 0.0);
        }
    }

    /**
     * @brief Test de escritura y lectura de matriz a archivo.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Test
    void testWriteReadFile() throws IOException {
        String filename = "test_matriz.txt";
        matriz.write(filename);

        Matriz leida = new Matriz().read(filename);
        assertTrue(matriz.equals(leida));

        // Limpieza
        new File(filename).delete();
    }

    /**
     * @brief Test que verifica que acceder a una posición fuera de los límites lanza excepción.
     */
    @Test
    void testGetOutOfBoundsThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> matriz.get(5, 5));
    }
}
