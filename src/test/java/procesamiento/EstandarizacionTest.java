package procesamiento;

import datos.*;
import org.junit.jupiter.api.Test;
import vectores.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EstandarizacionTest {

    @Test
    void testEstandarizacionCuantitativo() {
        ArrayList<Double> lista = new ArrayList<>();
        lista.add(10.0);
        lista.add(20.0);
        lista.add(30.0);
        Cuantitativo cuant = new Cuantitativo("Edad", new Vector(lista));
        Dataset dataset = new Dataset();
        ArrayList<Atributo> atributos = new ArrayList<>();
        atributos.add(cuant);
        dataset.setAtributos(atributos);

        Preprocesado estandar = new Estandarizacion();
        List<Atributo> resultado = estandar.procesar(dataset);

        assertTrue(resultado.get(0) instanceof Cuantitativo);
        Cuantitativo procesado = (Cuantitativo) resultado.get(0);

        double media = procesado.media();
        assertEquals(0.0, media, 1e-6); // media tras estandarización debe ser 0
    }

    @Test
    void testEstandarizacionConCualitativo() {
        Dataset dataset = new Dataset();
        dataset.setAtributos(List.of(
                new Cualitativo("Color", List.of("Rojo", "Azul", "Verde"))
        ));

        Preprocesado estandar = new Estandarizacion();
        List<Atributo> resultado = estandar.procesar(dataset);

        assertTrue(resultado.get(0) instanceof Cualitativo);
    }
}

