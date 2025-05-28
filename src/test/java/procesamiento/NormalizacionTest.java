package procesamiento;

import datos.*;
import org.junit.jupiter.api.Test;
import vectores.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class NormalizacionTest
 * @brief Pruebas unitarias para la clase {@link Normalizacion}, encargada de normalizar atributos cuantitativos.
 *
 * Se comprueba que la normalización transforma correctamente los valores a un rango [0, 1], y que no afecta a atributos cualitativos.
 */
public class NormalizacionTest {

    /**
     * @test Verifica que un atributo {@link Cuantitativo} es correctamente normalizado.
     *
     * Se normaliza una lista de valores numéricos, y se comprueba que el valor mínimo es 0 y el máximo es 1.
     */
    @Test
    void testNormalizacionCuantitativo() {
        Cuantitativo cuant = new Cuantitativo("Edad", new Vector(List.of(10.0, 20.0, 30.0)));
        Dataset dataset = new Dataset();
        dataset.setAtributos(List.of(cuant));

        Preprocesado norm = new Normalizacion();
        List<Atributo> resultado = norm.procesar(dataset);

        assertTrue(resultado.get(0) instanceof Cuantitativo);
        Cuantitativo procesado = (Cuantitativo) resultado.get(0);
        Vector valores = procesado.getValores();

        // Comprobar que está normalizado (mínimo = 0, máximo = 1)
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < valores.size(); i++) {
            double val = valores.get(i);
            if (val < min) min = val;
            if (val > max) max = val;
        }

        assertEquals(0.0, min, 1e-6);
        assertEquals(1.0, max, 1e-6);
    }

    /**
     * @test Verifica que la normalización no afecta a atributos de tipo {@link Cualitativo}.
     *
     * Se asegura que los atributos cualitativos se mantienen sin modificación tras aplicar el preprocesado.
     */
    @Test
    void testNormalizacionConCualitativo() {
        Dataset dataset = new Dataset();
        dataset.setAtributos(List.of(
                new Cualitativo("Color", List.of("Rojo", "Azul", "Verde"))
        ));

        Preprocesado norm = new Normalizacion();
        List<Atributo> resultado = norm.procesar(dataset);

        assertTrue(resultado.get(0) instanceof Cualitativo);
    }
}
