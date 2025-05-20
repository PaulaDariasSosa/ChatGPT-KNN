package datos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CualitativoTest {

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

    @Test
    void testClearYToString() {
        List<String> colores = new ArrayList<>(List.of("Rojo", "Azul"));
        Cualitativo c = new Cualitativo("Color", colores);
        assertEquals("[Rojo, Azul]", c.toString());
        c.clear();
        assertTrue(c.getValores().isEmpty());
    }
}

