package datos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @class AtributoTest
 * @brief Clase de pruebas unitarias para la jerarquía de clases Atributo (Cualitativo y Cuantitativo).
 *
 * Esta clase valida el correcto funcionamiento de los métodos get y set
 * para los atributos de tipo cualitativo y cuantitativo, incluyendo nombre, peso y representación textual.
 */
public class AtributoTest {

    /**
     * @test Verifica los métodos get y set en una instancia de {@link Cualitativo}.
     *
     * Se comprueba que el nombre y el peso se asignan correctamente, así como la salida
     * del método {@code get()}, que combina ambos valores en una cadena.
     */
    @Test
    void testGetSetNombreYPesoCualitativo() {
        Cualitativo c = new Cualitativo("Color");
        c.setNombre("Tamaño");
        c.setPeso(0.7);
        assertEquals("Tamaño", c.getNombre());
        assertEquals(0.7, c.getPeso());
        assertEquals("Tamaño: 0.7", c.get());
    }

    /**
     * @test Verifica los métodos get y set en una instancia de {@link Cuantitativo}.
     *
     * Se comprueba que el nombre y el peso se asignan correctamente, así como la salida
     * del método {@code get()}, que combina ambos valores en una cadena.
     */
    @Test
    void testGetSetNombreYPesoCuantitativo() {
        Cuantitativo c = new Cuantitativo("Edad");
        c.setNombre("Altura");
        c.setPeso(2.5);
        assertEquals("Altura", c.getNombre());
        assertEquals(2.5, c.getPeso());
        assertEquals("Altura: 2.5", c.get());
    }
}
