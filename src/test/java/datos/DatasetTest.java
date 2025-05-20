package datos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vectores.Vector;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DatasetTest {

    private Dataset dataset;

    @BeforeEach
    void setUp() {
        // ⚠️ IMPORTANTE: Cualitativo debe ser el último si vas a usar getClases()
        List<Atributo> atributos = new ArrayList<>();
        ArrayList<Double> numero = new ArrayList<>();
        numero.add(20.0);
        numero.add(30.0);
        numero.add(25.0);
        atributos.add(new Cuantitativo("Edad", new Vector(numero)));
        ArrayList<String> colores = new ArrayList<>();
        colores.add("Rojo");
        colores.add("Verde");
        colores.add("Azul");
        atributos.add(new Cualitativo("Color", colores));
        dataset = new Dataset(atributos);
    }

    @Test
    void testNumeroAtributos() {
        assertEquals(2, dataset.numeroAtributos());
    }

    @Test
    void testNumeroCasos() {
        assertEquals(3, dataset.numeroCasos());
    }

    @Test
    void testNombreAtributos() {
        List<String> esperado = Arrays.asList("Edad", "Color");
        assertEquals(esperado, dataset.nombreAtributos());
    }

    @Test
    void testAddListYDelete() {
        // Valores según tipo: Cuantitativo, Cualitativo
        dataset.add(Arrays.asList("40", "Amarillo"));
        assertEquals(4, dataset.numeroCasos());
        dataset.delete(3);
        assertEquals(3, dataset.numeroCasos());
    }

    @Test
    void testAddInstancia() {
        // Cuantitativo, Cualitativo
        List<Object> valores = Arrays.asList(40.0, "Amarillo");
        dataset.add(new Instancia(valores));
        assertEquals(4, dataset.numeroCasos());
    }

    @Test
    void testGetInstance() {
        Instancia instancia = dataset.getInstance(0);
        assertEquals(20.0, instancia.getValores().get(0));
        assertEquals("Rojo", instancia.getValores().get(1));
    }

    @Test
    void testGetValores() {
        List<String> valores = dataset.getValores();
        assertEquals("20.0", valores.get(0));
        assertEquals("Rojo", valores.get(1));
    }

    @Test
    void testGetClases() {
        List<String> clases = dataset.getClases();
        List<String> esperado = Arrays.asList("Rojo", "Verde", "Azul");
        assertEquals(esperado, clases);
    }

    @Test
    void testGetPesos() {
        String pesos = dataset.getPesos();
        assertTrue(pesos.contains("1.0"));
    }

    @Test
    void testCambiarPesoIndividual() {
        dataset.cambiarPeso(0, 2.5);
        assertEquals(2.5, dataset.get(0).getPeso());
    }

    @Test
    void testRestaurarOriginal() {
        dataset.setOriginal();
        double originalPeso = dataset.get(0).getPeso();
        dataset.cambiarPeso(0, 100.0);
        assertNotEquals(originalPeso, dataset.get(0).getPeso());
        dataset.restaurarOriginal();
    }

    @Test
    void testToString() {
        String salida = dataset.toString();
        assertTrue(salida.contains("Edad"));
        assertTrue(salida.contains("Rojo"));
    }

    @Test
    void testWriteAndRead() throws IOException {
        String filename = "test_dataset.csv";
        dataset.write(filename);
        Dataset nuevo = new Dataset(filename);
        assertEquals(dataset.numeroAtributos(), nuevo.numeroAtributos());
        assertEquals(dataset.numeroCasos(), nuevo.numeroCasos());
        new File(filename).delete(); // Limpieza
    }
}
