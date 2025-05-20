package datos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import vectores.Vector;

public class InstanciaTest {

    @Test
    void testConstructorVacio() {
        Instancia instancia = new Instancia();
        assertNotNull(instancia.getValores());
        assertTrue(instancia.getValores().isEmpty());
    }

    @Test
    void testConstructorConLista() {
        List<Object> lista = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, "ClaseA"));
        Instancia instancia = new Instancia(lista);
        assertEquals(lista, instancia.getValores());
    }

    @Test
    void testConstructorConString() {
        Instancia instancia = new Instancia("1.0,2.0,ClaseA");
        List<Object> esperado = Arrays.asList("1.0", "2.0", "ClaseA");
        assertEquals(esperado, instancia.getValores());
    }

    @Test
    void testToString() {
        Instancia instancia = new Instancia("1.0,2.0,ClaseA");
        String esperado = "[1.0, 2.0, ClaseA]";
        assertEquals(esperado, instancia.toString());
    }

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

    @Test
    void testGetClase() {
        Instancia instancia = new Instancia("1.0,2.0,ClaseA");
        assertEquals("ClaseA", instancia.getClase());
    }

    @Test
    void testDeleteClase() {
        Instancia instancia = new Instancia("1.0,2.0,ClaseA");
        instancia.deleteClase();
        assertEquals(2, instancia.getValores().size());
        assertFalse(instancia.getValores().contains("ClaseA"));
    }

    @Test
    void testNormalizar() {
        List<Object> lista = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, "ClaseA"));
        Instancia instancia = new Instancia(lista);
        instancia.normalizar();
        List<Object> valores = instancia.getValores();
        assertEquals(3, valores.size());
        assertTrue(valores.stream().allMatch(v -> v instanceof Double));
        // Máximo tras normalizar debe ser 1.0
        assertEquals(1.0, (Double) valores.get(2), 1e-6);
    }

    @Test
    void testEstandarizar() {
        List<Object> lista = new ArrayList<>(Arrays.asList(2.0, 4.0, 4.0, "ClaseA"));
        Instancia instancia = new Instancia(lista);
        instancia.estandarizar();
        List<Object> valores = instancia.getValores();
        assertEquals(3, valores.size());
        assertTrue(valores.stream().allMatch(v -> v instanceof Double));
        // La media debe quedar 0 tras estandarización
        double media = valores.stream().mapToDouble(o -> (Double) o).average().orElse(99);
        assertEquals(0.0, media, 1e-6);
    }
}

