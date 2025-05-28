package entrenamiento;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import entrenamiento.Entrenamiento;
import datos.*;
import java.util.*;
import java.io.*;

/**
 * @class EntrenamientoTest
 * @brief Pruebas unitarias para la clase {@link Entrenamiento}.
 *
 * Se prueban funcionalidades de inicialización, particionado de datos (split) con y sin semilla,
 * generación de predicciones, generación de matriz de confusión y operaciones de lectura/escritura.
 */
class EntrenamientoTest {

    /**
     * @test Verifica la creación de un objeto Entrenamiento con split sin semilla.
     */
    @Test
    void testEntrenamientoSplitSinSemilla() {
        Dataset dataset = new Dataset();

        Cuantitativo atributo = new Cuantitativo("A");
        atributo.add(1.0);
        atributo.add(2.0);
        atributo.add(3.0);
        atributo.add(4.0);
        atributo.setPeso(1.0);

        // Atributo cualitativo para la clase
        Cualitativo clase = new Cualitativo("Clase");
        clase.add("Rojo");
        clase.add("Rojo");
        clase.add("Azul");
        clase.add("Azul");

        ArrayList<Atributo> atributos = new ArrayList<>();
        atributos.add(atributo);
        atributos.add(clase);
        dataset.setAtributos(atributos);
        ArrayList<Object> i1 = new ArrayList<>();
        i1.add(1.0);
        i1.add("Rojo");
        dataset.add(new Instancia(i1));
        ArrayList<Object> i2 = new ArrayList<>();
        i2.add(2.0);
        i2.add("Rojo");
        dataset.add(new Instancia(i2));
        ArrayList<Object> i3 = new ArrayList<>();
        i3.add(3.0);
        i3.add("Azul");
        dataset.add(new Instancia(i3));
        ArrayList<Object> i4 = new ArrayList<>();
        i4.add(4.0);
        i4.add("Azul");
        dataset.add(new Instancia(i4));

        Entrenamiento entrenamiento = new Entrenamiento(dataset, 0.5, "euclidiana");

        assertNotNull(entrenamiento);
    }

    /**
     * @test Verifica la creación de un objeto Entrenamiento con split usando semilla fija.
     */
    @Test
    void testEntrenamientoSplitConSemilla() {
        Dataset dataset = new Dataset();

        Cuantitativo atributo = new Cuantitativo("A");
        atributo.add(1.0);
        atributo.add(2.0);
        atributo.add(3.0);
        atributo.add(4.0);
        atributo.setPeso(1.0);

        // Atributo cualitativo para la clase
        Cualitativo clase = new Cualitativo("Clase");
        clase.add("Rojo");
        clase.add("Rojo");
        clase.add("Azul");
        clase.add("Azul");

        ArrayList<Atributo> atributos = new ArrayList<>();
        atributos.add(atributo);
        atributos.add(clase);
        dataset.setAtributos(atributos);
        ArrayList<Object> i1 = new ArrayList<>();
        i1.add(1.0);
        i1.add("Rojo");
        dataset.add(new Instancia(i1));
        ArrayList<Object> i2 = new ArrayList<>();
        i2.add(2.0);
        i2.add("Rojo");
        dataset.add(new Instancia(i2));
        ArrayList<Object> i3 = new ArrayList<>();
        i3.add(3.0);
        i3.add("Azul");
        dataset.add(new Instancia(i3));
        ArrayList<Object> i4 = new ArrayList<>();
        i4.add(4.0);
        i4.add("Azul");
        dataset.add(new Instancia(i4));

        Entrenamiento entrenamiento = new Entrenamiento(dataset, 0.5, 42, "euclidiana");

        assertNotNull(entrenamiento);
    }

    /**
     * @test Verifica que la generación de predicción no genera errores.
     */
    @Test
    void testGenerarPrediccionYPrecision() {
        Dataset dataset = new Dataset();

        Cuantitativo atributo = new Cuantitativo("A");
        atributo.add(1.0);
        atributo.add(2.0);
        atributo.add(3.0);
        atributo.add(4.0);
        atributo.setPeso(1.0);

        // Atributo cualitativo para la clase
        Cualitativo clase = new Cualitativo("Clase");
        clase.add("Rojo");
        clase.add("Rojo");
        clase.add("Azul");
        clase.add("Azul");

        ArrayList<Atributo> atributos = new ArrayList<>();
        atributos.add(atributo);
        atributos.add(clase);
        dataset.setAtributos(atributos);
        ArrayList<Object> i1 = new ArrayList<>();
        i1.add(1.0);
        i1.add("Rojo");
        dataset.add(new Instancia(i1));
        ArrayList<Object> i2 = new ArrayList<>();
        i2.add(2.0);
        i2.add("Rojo");
        dataset.add(new Instancia(i2));
        ArrayList<Object> i3 = new ArrayList<>();
        i3.add(3.0);
        i3.add("Azul");
        dataset.add(new Instancia(i3));
        ArrayList<Object> i4 = new ArrayList<>();
        i4.add(4.0);
        i4.add("Azul");
        dataset.add(new Instancia(i4));

        Entrenamiento entrenamiento = new Entrenamiento(dataset, 0.5, 123, "euclidiana");
        entrenamiento.generarPrediccion(1);  // Debe ejecutarse sin errores
    }

    /**
     * @test Verifica que la generación de la matriz de confusión se ejecuta sin errores.
     */
    @Test
    void testMatrizConfusion() {
        Dataset dataset = new Dataset();

        Cuantitativo atributo = new Cuantitativo("A");
        atributo.add(1.0);
        atributo.add(2.0);
        atributo.add(3.0);
        atributo.add(4.0);
        atributo.setPeso(1.0);

        // Atributo cualitativo para la clase
        Cualitativo clase = new Cualitativo("Clase");
        clase.add("Rojo");
        clase.add("Rojo");
        clase.add("Azul");
        clase.add("Azul");

        ArrayList<Atributo> atributos = new ArrayList<>();
        atributos.add(atributo);
        atributos.add(clase);
        dataset.setAtributos(atributos);
        ArrayList<Object> i1 = new ArrayList<>();
        i1.add(1.0);
        i1.add("Rojo");
        dataset.add(new Instancia(i1));
        ArrayList<Object> i2 = new ArrayList<>();
        i2.add(2.0);
        i2.add("Rojo");
        dataset.add(new Instancia(i2));
        ArrayList<Object> i3 = new ArrayList<>();
        i3.add(3.0);
        i3.add("Azul");
        dataset.add(new Instancia(i3));
        ArrayList<Object> i4 = new ArrayList<>();
        i4.add(4.0);
        i4.add("Azul");
        dataset.add(new Instancia(i4));

        Entrenamiento entrenamiento = new Entrenamiento(dataset, 0.5, 42, "euclidiana");
        entrenamiento.generarMatriz(1);  // Debe imprimir la matriz sin errores
    }

    /**
     * @test Verifica la correcta escritura y lectura de archivos para entrenamiento.
     * @throws IOException si hay errores en la lectura o escritura de archivos.
     */
    @Test
    void testGuardarYCargar() throws IOException {
        Dataset dataset = new Dataset();

        Cuantitativo atributo = new Cuantitativo("A");
        atributo.add(1.0);
        atributo.add(2.0);
        atributo.add(3.0);
        atributo.add(4.0);
        atributo.setPeso(1.0);

        // Atributo cualitativo para la clase
        Cualitativo clase = new Cualitativo("Clase");
        clase.add("Rojo");
        clase.add("Rojo");
        clase.add("Azul");
        clase.add("Azul");

        ArrayList<Atributo> atributos = new ArrayList<>();
        atributos.add(atributo);
        atributos.add(clase);
        dataset.setAtributos(atributos);
        ArrayList<Object> i1 = new ArrayList<>();
        i1.add(1.0);
        i1.add("Rojo");
        dataset.add(new Instancia(i1));
        ArrayList<Object> i2 = new ArrayList<>();
        i2.add(2.0);
        i2.add("Rojo");
        dataset.add(new Instancia(i2));
        ArrayList<Object> i3 = new ArrayList<>();
        i3.add(3.0);
        i3.add("Azul");
        dataset.add(new Instancia(i3));
        ArrayList<Object> i4 = new ArrayList<>();
        i4.add(4.0);
        i4.add("Azul");
        dataset.add(new Instancia(i4));

        Entrenamiento entrenamiento = new Entrenamiento(dataset, 0.5, 1, "euclidiana");

        entrenamiento.write("train.csv", "test.csv");
        entrenamiento.read("train.csv", "test.csv");

        File f1 = new File("train.csv");
        File f2 = new File("test.csv");
        assertTrue(f1.exists());
        assertTrue(f2.exists());

        f1.delete();
        f2.delete();
    }
}
