package vectores;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MatrizTest {

    private Matriz matriz;

    @BeforeEach
    void setUp() {
        matriz = new Matriz(2, 2);
        matriz.set(0, 0, 1.0);
        matriz.set(0, 1, 2.0);
        matriz.set(1, 0, 3.0);
        matriz.set(1, 1, 4.0);
    }

    @Test
    void testConstructorDefault() {
        Matriz m = new Matriz();
        assertEquals(1, m.getNumRows());
        assertEquals(1, m.getNumCols());
    }

    @Test
    void testConstructorCoef() {
        double[][] values = {{1.0, 2.0}, {3.0, 4.0}};
        Matriz m = new Matriz(2, 2, values);
        assertEquals(4.0, m.get(1, 1));
    }

    @Test
    void testConstructorFromVectorList() {
        List<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(new double[]{1, 2}));
        vectors.add(new Vector(new double[]{3, 4}));
        Matriz m = new Matriz(vectors);
        assertEquals(3.0, m.get(1, 0));
    }

    @Test
    void testGetSet() {
        matriz.set(0, 0, 9.0);
        assertEquals(9.0, matriz.get(0, 0));
    }

    @Test
    void testTranspose() {
        double[][] datos = {
                {1, 2},
                {3, 4}
        };
        Matriz matriz = new Matriz(2, 2, datos);
        matriz.transpose();  // 2x2 sigue siendo 2x2, pero transpuesta

        assertEquals(2, matriz.getNumRows());
        assertEquals(2, matriz.getNumCols());
        assertEquals(1, matriz.get(0, 0));
        assertEquals(3, matriz.get(0, 1));
    }

    @Test
    void testMultiply() {
        Matriz result = Matriz.multiply(matriz, matriz);
        assertEquals(7.0, result.get(0, 0));
        assertEquals(10.0, result.get(0, 1));
    }

    @Test
    void testEquals() {
        Matriz copia = new Matriz(2, 2);
        copia.set(0, 0, 1.0);
        copia.set(0, 1, 2.0);
        copia.set(1, 0, 3.0);
        copia.set(1, 1, 4.0);
        assertTrue(matriz.equals(copia));
    }

    @Test
    void testDeleteRows() {
        matriz.deleteRows(0);
        assertEquals(1, matriz.getNumRows());
    }

    @Test
    void testDeleteCols() {
        double[][] datos = {
                {1, 2},
                {3, 4}
        };
        Matriz matriz = new Matriz(2, 2, datos);
        matriz.deleteCols(1);  // Elimina la columna 1

        // Asegúrate de que ahora solo haya una columna
        assertEquals(1, matriz.getNumCols());
        assertEquals(1.0, matriz.get(0, 0));
    }


    @Test
    void testAddRowsCols() {
        matriz.addRows();
        matriz.addCols();
        assertEquals(3, matriz.getNumRows());
        assertEquals(3, matriz.getNumCols());
    }

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


    @Test
    void testWriteReadFile() throws IOException {
        String filename = "test_matriz.txt";
        matriz.write(filename);

        Matriz leida = new Matriz().read(filename);
        assertTrue(matriz.equals(leida));

        // Limpieza
        new File(filename).delete();
    }

    @Test
    void testGetOutOfBoundsThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> matriz.get(5, 5));
    }
}

