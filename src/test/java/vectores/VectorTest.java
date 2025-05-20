package vectores;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VectorTest {

    private Vector v1;
    private Vector v2;
    private Vector vEmpty;

    @BeforeEach
    void setUp() {
        v1 = new Vector(Arrays.asList(1.0, 2.0, 3.0));
        v2 = new Vector(Arrays.asList(4.0, 5.0, 6.0));
        vEmpty = new Vector();
    }

    @Test
    void testConstructorArray() {
        double[] arr = {1.0, 2.0, 3.0};
        Vector v = new Vector(arr);
        assertEquals(3, v.size());
        assertEquals(2.0, v.get(1));
    }

    @Test
    public void testCopyConstructor() {
        Vector original = new Vector(new double[] {1.0, 2.0, 3.0});
        Vector copia = new Vector(original);

        // Comprobamos que los contenidos son iguales
        for (int i = 0; i < original.size(); i++) {
            assertEquals(original.get(i), copia.get(i), 0.0001);
        }

        // Además, que no son la misma instancia
        assertNotSame(original, copia);
    }


    @Test
    void testAddDouble() {
        vEmpty.add(5.0);
        assertEquals(1, vEmpty.size());
        assertEquals(5.0, vEmpty.get(0));
    }

    @Test
    void testAddVector() {
        Vector copy = new Vector(v1);
        copy.add(v2);
        assertEquals(5.0, copy.get(0));
        assertEquals(7.0, copy.get(1));
    }

    @Test
    void testAddVectorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(2)));
    }

    @Test
    void testSumScalar() {
        Vector sum = v1.sum(1.0);
        assertEquals(2.0, sum.get(0));
        assertEquals(4.0, sum.get(2));
    }

    @Test
    void testSumVector() {
        Vector result = v1.sum(v2);
        assertEquals(5.0, result.get(0));
    }

    @Test
    void testSumVectorException() {
        assertThrows(IllegalArgumentException.class, () -> v1.sum(new Vector(1)));
    }

    @Test
    void testEquals() {
        Vector v3 = new Vector(Arrays.asList(1.0, 2.0, 3.0));
        assertTrue(v1.equals(v3));
        assertFalse(v1.equals(v2));
    }

    @Test
    void testEqualDimension() {
        assertTrue(v1.equalDimension(v2));
        assertFalse(v1.equalDimension(new Vector(2)));
    }

    @Test
    void testRemove() {
        v1.remove(1);
        assertEquals(2, v1.size());
        assertEquals(3.0, v1.get(1));
    }

    @Test
    void testMaxMin() {
        assertEquals(3.0, v1.getMax());
        assertEquals(1.0, v1.getMin());
    }

    @Test
    void testGetMaxInt() {
        assertEquals(2, v1.getMaxInt());
    }

    @Test
    void testProductoEscalar() {
        assertEquals(32.0, v1.productoEscalar(v2));
    }

    @Test
    void testProductoEscalarException() {
        assertThrows(IllegalArgumentException.class, () -> v1.productoEscalar(new Vector(2)));
    }

    @Test
    void testConcat() {
        Vector concat = new Vector(v1);
        concat.concat(v2);
        assertEquals(6, concat.size());
    }

    @Test
    void testModule() {
        assertEquals(Math.sqrt(14), v1.module());
    }

    @Test
    void testMultiply() {
        v1.multiply(2.0);
        assertEquals(2.0, v1.get(0));
        assertEquals(6.0, v1.get(2));
    }

    @Test
    void testNormalize() {
        Vector v = new Vector(Arrays.asList(5.0, 10.0, 15.0));
        v.normalize();
        assertEquals(0.0, v.get(0));
        assertEquals(0.5, v.get(1));
        assertEquals(1.0, v.get(2));
    }

    @Test
    void testAvg() {
        assertEquals(2.0, v1.avg());
    }

    @Test
    void testToStringAndGetValores() {
        assertEquals(Arrays.asList(1.0, 2.0, 3.0), v1.getValores());
        assertTrue(v1.toString().contains("1.0"));
    }

    @Test
    public void testReadWriteFile() throws IOException {
        File tempFile = File.createTempFile("vector_test", ".txt");
        tempFile.deleteOnExit();

        // Escribir manualmente el contenido compatible con el lector
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("1.0\n2.0\n3.0\n");
        }
        // Leer usando el constructor de Vector
        Vector vectorLeido = new Vector(tempFile);
        assertEquals(3, vectorLeido.size());
        assertEquals(1.0, vectorLeido.get(0), 0.0001);
        assertEquals(2.0, vectorLeido.get(1), 0.0001);
        assertEquals(3.0, vectorLeido.get(2), 0.0001);
    }

}

