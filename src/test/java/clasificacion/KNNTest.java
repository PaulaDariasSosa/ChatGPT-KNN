package clasificacion;

import datos.*;
import vectores.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

class KNNTest {

    @Test
    void testDistanciaEuclideaSinPesos() {
        KNN knn = new KNN(3);
        Vector v1 = new Vector(List.of(1.0, 2.0, 3.0));
        Vector v2 = new Vector(List.of(4.0, 6.0));
        double dist = knn.getDistanciaEuclidea(v1, v2);
        assertEquals(5, dist, 1e-6);
    }

    @Test
    void testDistanciaEuclideaConPesos() {
        KNN knn = new KNN(3);
        Vector v1 = new Vector(List.of(1.0, 2.0, 3.0));
        Vector v2 = new Vector(List.of(4.0, 6.0));
        List<Double> pesos = List.of(1.0, 0.5, 2.0);
        double dist = knn.getDistanciaEuclidea(v1, v2, pesos);
        assertEquals(3.605551275463989, dist, 1e-6);
    }

    @Test
    void testDistanciaManhattan() {
        KNN knn = new KNN(3, "manhattan");
        Vector v1 = new Vector(List.of(1.0, 2.0));
        Vector v2 = new Vector(List.of(4.0, 6.0));
        List<Double> pesos = List.of(1.0, 1.0);
        double dist = knn.getDistanciaManhattan(v1, v2, pesos);
        assertEquals(7.0, dist, 1e-6);
    }

    @Test
    void testDistanciaMinkowski() {
        KNN knn = new KNN(3, "minkowski");
        Vector v1 = new Vector(List.of(1.0, 2.0));
        Vector v2 = new Vector(List.of(4.0, 6.0));
        List<Double> pesos = List.of(1.0, 1.0);
        double dist = knn.getDistanciaMinkowski(v1, v2, pesos, 3);
        double esperado = Math.pow(Math.pow(3.0, 3) + Math.pow(4.0, 3), 1.0/3);
        assertEquals(esperado, dist, 1e-6);
    }

    @Test
    void testClasificacionSimple() {
        KNN knn = new KNN(1, "euclidiana");

        Dataset dataset = new Dataset();
        List<Atributo> atributos = new ArrayList<>();
        ArrayList<Double> numero = new ArrayList<>();
        numero.add(1.0);
        numero.add(5.0);
        atributos.add(new Cuantitativo("Edad", new Vector(numero)));
        ArrayList<String> colores = new ArrayList<>();
        colores.add("Rojo");
        colores.add("Verde");
        atributos.add(new Cualitativo("Color", colores));
        dataset = new Dataset(atributos);


        // Instancia de prueba (sin clase)
        Instancia nueva = new Instancia(List.of(1.5));

        String clase = knn.clasificar(dataset, nueva);
        assertEquals("Rojo", clase);
    }

}

