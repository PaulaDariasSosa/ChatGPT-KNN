package datos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AtributoTest {

    @Test
    void testGetSetNombreYPesoCualitativo() {
        Cualitativo c = new Cualitativo("Color");
        c.setNombre("Tamaño");
        c.setPeso(0.7);
        assertEquals("Tamaño", c.getNombre());
        assertEquals(0.7, c.getPeso());
        assertEquals("Tamaño: 0.7", c.get());
    }

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
