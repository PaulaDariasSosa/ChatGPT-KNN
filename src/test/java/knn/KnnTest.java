package knn;

import static org.junit.jupiter.api.Assertions.*;

import datos.Dataset;
import datos.Instancia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;
/**
class KnnTfgTest {

    Dataset dataset;

    @BeforeEach
    void setUp() throws Exception {
        dataset = new Dataset();
        File irisFile = new File("iris.csv"); // Ajusta si tu path es diferente
        Scanner sc = new Scanner(irisFile);
        while (sc.hasNextLine()) {
            String linea = sc.nextLine();
            dataset.add(new Instancia(linea));
        }
        sc.close();
    }

    @Test
    void testAnadirInstancia() {
        String entrada = "5.1,3.5,1.4,0.2,Iris-setosa\n";
        System.setIn(new ByteArrayInputStream(entrada.getBytes()));

        KnnTfg.anadirInstancia(dataset);

        assertEquals(151, dataset.numeroCasos()); // Iris tiene 150 filas, ahora 151
    }

    @Test
    void testEliminarInstancia() {
        String entrada = "0\n"; // Eliminar primera instancia
        System.setIn(new ByteArrayInputStream(entrada.getBytes()));

        KnnTfg.eliminarInstancia(dataset);

        assertEquals(149, dataset.numeroCasos());
    }

    @Test
    void testModificarInstancia() {
        String entrada = "0\n6.1,3.0,4.6,1.4,Iris-versicolor\n";
        System.setIn(new ByteArrayInputStream(entrada.getBytes()));

        KnnTfg.modificarInstancia(dataset);

        Instancia primeraInstancia = null;
        for (Object obj : dataset.getValores()) {
            if (obj instanceof Instancia) {
                primeraInstancia = (Instancia) obj;
                break;
            }
        }
        assertNotNull(primeraInstancia);
        assertEquals("6.1", primeraInstancia.getValores().get(0).toString());
    }



    @Test
    void testPreprocesarNormalizacion() {
        String entrada = "1\n"; // opción para normalización
        System.setIn(new ByteArrayInputStream(entrada.getBytes()));

        KnnTfg.preprocesar(dataset);

        assertTrue(true); // No excepción = éxito. Puedes agregar más validaciones si el método retorna algo
    }

    @Test
    void testCambiarPesosDistintos() {
        StringBuilder entrada = new StringBuilder("2\n"); // opción para pesos distintos
        for (int i = 0; i < 4; i++) {
            entrada.append("1.0\n"); // peso para cada atributo (suponiendo 4 atributos)
        }
        System.setIn(new ByteArrayInputStream(entrada.toString().getBytes()));

        KnnTfg.cambiarPesos(dataset);

        assertTrue(true); // Agrega aserciones si el método guarda los pesos en algún lado
    }

    @Test
    void testCambiarPesosIguales() {
        String entrada = "1\n"; // opción para pesos iguales
        System.setIn(new ByteArrayInputStream(entrada.getBytes()));

        KnnTfg.cambiarPesos(dataset);

        assertTrue(true);
    }

    @Test
    void testCambiarPesoUnAtributo() {
        String entrada = "0\n2\n2.5\n"; // opción para un solo atributo, índice 2 a peso 2.5
        System.setIn(new ByteArrayInputStream(entrada.getBytes()));

        KnnTfg.cambiarPesos(dataset);

        assertTrue(true);
    }
}
*/
