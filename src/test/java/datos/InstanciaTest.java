package datos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import vectores.Vector;

/**
 * @class InstanciaTest
 * @brief Pruebas unitarias para la clase {@link Instancia}.
 *
 * Esta clase se encarga de validar los diferentes constructores, métodos de acceso,
 * transformación y conversión de la clase {@link Instancia}.
 */
public class InstanciaTest {

    /**
     * @test Verifica que el constructor por defecto inicializa correctamente una instancia vacía.
     *
     * Comprueba que la lista de valores no sea nula y esté vacía.
     */
    @Test
    void testConstructorVacio() {
        Instancia instancia = new Instancia();
        assertNotNull(instancia.getValores());
        assertTrue(instancia.getValores().isEmpty());
    }

    /**
     * @test Verifica el constructor que recibe una lista de objetos como entrada.
     *
     * Se asegura que los valores sean almacenados correctamente.
     */
    @Test
    void testConstructorConLista() {
        List<Object> lista = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, "ClaseA"));
        Instancia instancia = new Instancia(lista);
        assertEquals(lista, instancia.getValores());
    }

    /**
     * @test Verifica el constructor que recibe una cadena separada por comas.
     *
     * La cadena debe dividirse y almacenarse como una lista de Strings.
     */
    @Test
    void testConstructorConString() {
        Instancia instancia = new Instancia("1.0,2.0,ClaseA");
        List<Object> esperado = Arrays.asList("1.0", "2.0", "ClaseA");
        assertEquals(esperado, instancia.getValores());
    }

    /**
     * @test Verifica el método {@code toString()}.
     *
     * Comprueba que la representación textual de la instancia sea correcta.
     */
    @Test
    void testToString() {
        Instancia instancia = new Instancia("1.0,2.0,ClaseA");
        String esperado = "[1.0, 2.0, ClaseA]";
        assertEquals(esperado, instancia.toString());
    }

    /**
     * @test Verifica la correcta conversión de los valores numéricos a un {@link Vector}.
     *
     * Solo los valores numéricos deben formar parte del vector.
     */
    @Test
    void testGetVector() {
        List<Object> lista = new ArrayList<>(Arrays.asList(1.0, 2, 3.5, "ClaseA"));
        Instancia instancia = new Instancia(lista);
        Vector vector = instancia.getVector();
        assertEquals(3, vector.size());
        assertEquals(1.0, vector.get(0));
        assertEquals(2.0, vector.get(1)); // El entero se convierte
        assertEquals(3.5, vector.get(2));
    }

    /**
     * @test Verifica que se obtenga correctamente el valor correspondiente a la clase (último elemento).
     */
    @Test
    void testGetClase() {
        Instancia instancia = new Instancia("1.0,2.0,ClaseA");
        assertEquals("ClaseA", instancia.getClase());
    }

    /**
     * @test Verifica que el método {@code deleteClase()} elimine correctamente el último valor de la lista.
     */
    @Test
    void testDeleteClase() {
        Instancia instancia = new Instancia("1.0,2.0,ClaseA");
        instancia.deleteClase();
        assertEquals(2, instancia.getValores().size());
        assertFalse(instancia.getValores().contains("ClaseA"));
    }

    /**
     * @test Verifica que la normalización de valores se realice correctamente.
     *
     * Asegura que:
     * - Todos los valores son de tipo {@link Double}.
     * - El valor máximo tras la normalización sea 1.0.
     */
    @Test
    void testNormalizar() {
        List<Object> lista = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, "ClaseA"));
        Instancia instancia = new Instancia(lista);
        instancia.normalizar();
        List<Object> valores = instancia.getValores();
        assertEquals(3, valores.size());
        assertTrue(valores.stream().allMatch(v -> v instanceof Double));
        assertEquals(1.0, (Double) valores.get(2), 1e-6);
    }

    /**
     * @test Verifica que la estandarización de valores se realice correctamente.
     *
     * Asegura que:
     * - Todos los valores sean de tipo {@link Double}.
     * - La media de los valores estandarizados sea aproximadamente cero.
     */
    @Test
    void testEstandarizar() {
        List<Object> lista = new ArrayList<>(Arrays.asList(2.0, 4.0, 4.0, "ClaseA"));
        Instancia instancia = new Instancia(lista);
        instancia.estandarizar();
        List<Object> valores = instancia.getValores();
        assertEquals(3, valores.size());
        assertTrue(valores.stream().allMatch(v -> v instanceof Double));
        double media = valores.stream().mapToDouble(o -> (Double) o).average().orElse(99);
        assertEquals(0.0, media, 1e-6);
    }
}
