package datos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vectores.Vector;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @class DatasetTest
 * @brief Pruebas unitarias para la clase {@link Dataset}.
 *
 * Esta clase verifica el correcto funcionamiento de la gestión de atributos, instancias,
 * serialización y manipulación de pesos en un conjunto de datos.
 */
public class DatasetTest {

    private Dataset dataset;

    /**
     * @brief Inicializa un conjunto de datos antes de cada test.
     *
     * Se crean atributos cuantitativos y cualitativos con datos de ejemplo.
     */
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

    /**
     * @test Verifica que el número de atributos sea el esperado.
     */
    @Test
    void testNumeroAtributos() {
        assertEquals(2, dataset.numeroAtributos());
    }

    /**
     * @test Verifica que el número de casos (instancias) sea correcto.
     */
    @Test
    void testNumeroCasos() {
        assertEquals(3, dataset.numeroCasos());
    }

    /**
     * @test Verifica que los nombres de los atributos se obtienen correctamente.
     */
    @Test
    void testNombreAtributos() {
        List<String> esperado = Arrays.asList("Edad", "Color");
        assertEquals(esperado, dataset.nombreAtributos());
    }

    /**
     * @test Prueba la adición y eliminación de instancias mediante listas de Strings.
     */
    @Test
    void testAddListYDelete() {
        dataset.add(Arrays.asList("40", "Amarillo"));
        assertEquals(4, dataset.numeroCasos());
        dataset.delete(3);
        assertEquals(3, dataset.numeroCasos());
    }

    /**
     * @test Verifica la adición de una instancia del tipo {@link Instancia}.
     */
    @Test
    void testAddInstancia() {
        List<Object> valores = Arrays.asList(40.0, "Amarillo");
        dataset.add(new Instancia(valores));
        assertEquals(4, dataset.numeroCasos());
    }

    /**
     * @test Verifica la obtención de una instancia específica por índice.
     */
    @Test
    void testGetInstance() {
        Instancia instancia = dataset.getInstance(0);
        assertEquals(20.0, instancia.getValores().get(0));
        assertEquals("Rojo", instancia.getValores().get(1));
    }

    /**
     * @test Verifica que se obtienen correctamente los valores de todas las instancias.
     */
    @Test
    void testGetValores() {
        List<String> valores = dataset.getValores();
        assertEquals("20.0", valores.get(0));
        assertEquals("Rojo", valores.get(1));
    }

    /**
     * @test Verifica que se obtienen correctamente las clases posibles (valores del atributo cualitativo final).
     */
    @Test
    void testGetClases() {
        List<String> clases = dataset.getClases();
        List<String> esperado = Arrays.asList("Rojo", "Verde", "Azul");
        assertEquals(esperado, clases);
    }

    /**
     * @test Verifica la obtención de los pesos actuales de los atributos.
     */
    @Test
    void testGetPesos() {
        String pesos = dataset.getPesos();
        assertTrue(pesos.contains("1.0"));
    }

    /**
     * @test Prueba la modificación del peso de un atributo individual.
     */
    @Test
    void testCambiarPesoIndividual() {
        dataset.cambiarPeso(0, 2.5);
        assertEquals(2.5, dataset.get(0).getPeso());
    }

    /**
     * @test Verifica que el método {@code restaurarOriginal()} revierte correctamente los pesos modificados.
     */
    @Test
    void testRestaurarOriginal() {
        dataset.setOriginal();
        double originalPeso = dataset.get(0).getPeso();
        dataset.cambiarPeso(0, 100.0);
        assertNotEquals(originalPeso, dataset.get(0).getPeso());
        dataset.restaurarOriginal();
    }

    /**
     * @test Verifica el método {@code toString()} del dataset.
     */
    @Test
    void testToString() {
        String salida = dataset.toString();
        assertTrue(salida.contains("Edad"));
        assertTrue(salida.contains("Rojo"));
    }

    /**
     * @test Verifica la funcionalidad de escritura y lectura desde archivo CSV.
     *
     * @throws IOException si ocurre un error durante la escritura o lectura del archivo.
     */
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
