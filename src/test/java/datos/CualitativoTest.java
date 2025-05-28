package datos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @class CualitativoTest
 * @brief Clase de pruebas unitarias para la clase {@link Cualitativo}.
 *
 * Se valida el comportamiento de los constructores, la gestión de valores,
 * el cálculo de clases y frecuencias, y los métodos auxiliares como {@code toString} y {@code clear}.
 */
public class CualitativoTest {

    /**
     * @test Verifica el correcto funcionamiento de los diferentes constructores de la clase {@link Cualitativo}.
     *
     * Comprueba:
     * - Constructor por defecto.
     * - Constructor con nombre.
     * - Constructor con nombre y valor único.
     * - Constructor con nombre y lista de valores.
     * - Constructor copia.
     */
    @Test
    void testConstructores() {
        Cualitativo c1 = new Cualitativo();
        assertEquals("", c1.getNombre());

        Cualitativo c2 = new Cualitativo("Color");
        assertEquals("Color", c2.getNombre());

        Cualitativo c3 = new Cualitativo("Color", "Rojo");
        assertEquals(List.of("Rojo"), c3.getValores());

        List<String> valores = Arrays.asList("Rojo", "Azul");
        Cualitativo c4 = new Cualitativo("Color", valores);
        assertEquals(valores, c4.getValores());

        Cualitativo copia = new Cualitativo(c4);
        assertEquals("Color", copia.getNombre());
        assertEquals(valores, copia.getValores());
    }

    /**
     * @test Verifica los métodos {@code add}, {@code delete} y {@code getValor} de la clase {@link Cualitativo}.
     *
     * Se asegura que los valores se añaden y eliminan correctamente y que se accede al valor deseado según su índice.
     */
    @Test
    void testAddDeleteGetValor() {
        Cualitativo c = new Cualitativo("Color");
        c.add("Rojo");
        c.add("Verde");
        assertEquals("Rojo", c.getValor(0));
        assertEquals("Verde", c.getValor(1));

        c.delete(0);
        assertEquals("Verde", c.getValor(0));
    }

    /**
     * @test Verifica los métodos relacionados con clases y frecuencias en un atributo cualitativo.
     *
     * Evalúa {@code clases()}, {@code nClases()} y {@code frecuencia()} asegurando que:
     * - Las clases se identifican correctamente.
     * - Se calcula el número de clases.
     * - Las frecuencias relativas son correctas y suman 1.
     */
    @Test
    void testClasesYFrecuencias() {
        Cualitativo c = new Cualitativo("Tipo", Arrays.asList("A", "B", "A", "A", "C", "B"));
        List<String> clases = c.clases();
        assertTrue(clases.containsAll(List.of("A", "B", "C")));
        assertEquals(3, c.nClases());

        List<Double> frecuencias = c.frecuencia();
        // A: 3/6, B: 2/6, C: 1/6
        assertEquals(0.5, frecuencias.get(clases.indexOf("A")), 1e-6);
        assertEquals(1.0, frecuencias.stream().mapToDouble(Double::doubleValue).sum(), 1e-6);
    }

    /**
     * @test Verifica el funcionamiento de los métodos {@code toString()} y {@code clear()}.
     *
     * Se comprueba que:
     * - La representación textual es correcta antes de limpiar los valores.
     * - La lista de valores queda vacía tras invocar {@code clear()}.
     */
    @Test
    void testClearYToString() {
        List<String> colores = new ArrayList<>(List.of("Rojo", "Azul"));
        Cualitativo c = new Cualitativo("Color", colores);
        assertEquals("[Rojo, Azul]", c.toString());
        c.clear();
        assertTrue(c.getValores().isEmpty());
    }
}
