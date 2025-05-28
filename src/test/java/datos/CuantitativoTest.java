package datos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import vectores.Vector;

import org.junit.jupiter.api.Test;

/**
 * @class CuantitativoTest
 * @brief Pruebas unitarias para la clase {@link Cuantitativo}.
 *
 * Esta clase verifica el comportamiento de los distintos métodos de la clase {@link Cuantitativo},
 * incluyendo sus constructores, manipulación de datos, estadísticas y transformaciones.
 */
public class CuantitativoTest {

    /**
     * @test Verifica el correcto funcionamiento de los constructores de la clase {@link Cuantitativo}.
     *
     * Prueba:
     * - Constructor por defecto.
     * - Constructor con nombre.
     * - Constructor con nombre y valor individual.
     * - Constructor con nombre y vector de valores.
     * - Constructor copia.
     */
    @Test
    void testConstructores() {
        Cuantitativo c1 = new Cuantitativo();
        assertEquals("", c1.getNombre());

        Cuantitativo c2 = new Cuantitativo("Edad");
        assertEquals("Edad", c2.getNombre());

        Cuantitativo c3 = new Cuantitativo("Altura", 1.75);
        assertEquals(1, c3.getValores().size());
        assertEquals(1.75, c3.getValores().get(0));

        Vector v = new Vector(List.of(1.0, 2.0, 3.0));
        Cuantitativo c4 = new Cuantitativo("Peso", v);
        assertEquals(v, c4.getValores());

        Cuantitativo copia = new Cuantitativo(c4);
        assertEquals("Peso", copia.getNombre());
        assertEquals(v, copia.getValores());
    }

    /**
     * @test Verifica los métodos {@code add}, {@code delete} y {@code getValor} en objetos {@link Cuantitativo}.
     *
     * Se asegura que los valores puedan agregarse, eliminarse y recuperarse correctamente mediante sus índices.
     */
    @Test
    void testAddDeleteGetValor() {
        Cuantitativo c = new Cuantitativo("Medidas");
        c.add(2.0);
        c.add(3.5);
        assertEquals(2.0, c.getValor(0));
        assertEquals(3.5, c.getValor(1));

        c.delete(0);
        assertEquals(3.5, c.getValor(0));
    }

    /**
     * @test Verifica los cálculos estadísticos básicos: mínimo, máximo, media y desviación estándar.
     *
     * Comprueba que:
     * - El mínimo y máximo se calculan correctamente.
     * - La media aritmética es precisa.
     * - La desviación estándar se aproxima al valor esperado con una tolerancia razonable.
     */
    @Test
    void testMinimoMaximoMediaDesviacion() {
        Cuantitativo c = new Cuantitativo("Notas", new Vector(List.of(5.0, 7.0, 10.0)));
        assertEquals(5.0, c.minimo());
        assertEquals(10.0, c.maximo());
        assertEquals((5 + 7 + 10) / 3.0, c.media(), 1e-6);

        double desviacionEsperada = Math.sqrt(((5 - 7.33) * (5 - 7.33) +
                (7 - 7.33) * (7 - 7.33) +
                (10 - 7.33) * (10 - 7.33)) / 3);
        assertEquals(desviacionEsperada, c.desviacion(), 1e-1); // margen relajado
    }

    /**
     * @test Verifica la correcta estandarización de valores (media ≈ 0) en el atributo {@link Cuantitativo}.
     *
     * Asegura que tras la transformación, la media del vector de valores sea aproximadamente cero.
     */
    @Test
    void testEstandarizacion() {
        Cuantitativo c = new Cuantitativo("X", new Vector(List.of(1.0, 2.0, 3.0)));
        c.estandarizacion();

        // Calcular media manualmente
        Vector valores = c.getValores();
        double suma = 0.0;
        for (int i = 0; i < valores.size(); i++) {
            suma += valores.get(i);
        }
        double media = suma / valores.size();
        assertTrue(Math.abs(media) < 1e-6);
    }

    /**
     * @test Verifica el funcionamiento de los métodos {@code toString()} y {@code clear()}.
     *
     * Se comprueba que:
     * - La representación textual del atributo es correcta.
     * - Los valores se eliminan completamente al usar {@code clear()}.
     */
    @Test
    void testClearYToString() {
        Cuantitativo c = new Cuantitativo("Altura", new Vector(List.of(1.8, 1.9)));
        assertEquals("[1.8, 1.9]", c.toString());

        c.clear();
        assertEquals(0, c.getValores().size());
    }
}
