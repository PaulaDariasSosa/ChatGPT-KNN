package procesamiento;

import datos.*;
import org.junit.jupiter.api.Test;
import vectores.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class DatosCrudosTest
 * @brief Pruebas unitarias para la clase {@link DatosCrudos}.
 *
 * Esta clase prueba que el método {@code procesar()} de {@link DatosCrudos} devuelve
 * la misma referencia de atributos sin modificaciones.
 */
public class DatosCrudosTest {

    /**
     * @test Verifica que el método {@code procesar()} devuelve la misma referencia
     * de la lista de atributos del {@link Dataset} sin alterarla.
     */
    @Test
    void testProcesarDevuelveMismaReferencia() {
        Dataset dataset = new Dataset();
        dataset.setAtributos(List.of(
                new Cuantitativo("Edad", new Vector(List.of(10.0, 20.0, 30.0))),
                new Cualitativo("Color", List.of("Rojo", "Verde", "Azul"))
        ));

        Preprocesado preprocesado = new DatosCrudos();
        List<Atributo> resultado = preprocesado.procesar(dataset);

        assertEquals(dataset.getAtributos(), resultado);
    }
}
