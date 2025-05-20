package procesamiento;

import datos.*;
import org.junit.jupiter.api.Test;
import vectores.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatosCrudosTest {

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

