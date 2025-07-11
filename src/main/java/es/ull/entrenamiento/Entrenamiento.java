package entrenamiento;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import clasificacion.KNN;
import datos.*;
import vectores.Matriz;

/**
 * @class Entrenamiento
 * @brief Clase que representa el proceso de entrenamiento y evaluación de un modelo KNN.
 *
 * Esta clase permite dividir un conjunto de datos en conjuntos de entrenamiento y prueba,
 * realizar predicciones con el algoritmo KNN, generar la matriz de confusión y manejar
 * la lectura y escritura de los datasets de entrenamiento y prueba.
 */
public class Entrenamiento {

	/** Dataset de entrenamiento */
	private Dataset train;

	/** Dataset de prueba */
	private Dataset test;

	/** Lista de clases presentes en los datos */
	private List<String> clases;

	/** Tipo de distancia a utilizar en KNN */
	private String distancia;

	/**
	 * Constructor vacío.
	 */
	public Entrenamiento() {
	}

	/**
	 * Constructor que divide el dataset en entrenamiento y prueba según un porcentaje dado.
	 *
	 * @param datos Dataset completo con las instancias.
	 * @param porcentaje Porcentaje de datos que se usarán para entrenamiento (el resto para prueba).
	 * @param distancia Tipo de distancia a utilizar (por ejemplo "euclidiana", "manhattan").
	 */
	public Entrenamiento(Dataset datos, double porcentaje, String distancia) {
		this.distancia = distancia;
		Dataset trainset = new Dataset(datos.getAtributosEmpty());
		Dataset testset = new Dataset(datos.getAtributosEmpty());
		clases = datos.getClases();
		int indice = 0;
		while(indice < datos.numeroCasos()*porcentaje) {
			trainset.add(datos.getInstance(indice));
			indice += 1;
		}
		for (int i = indice; i < datos.numeroCasos(); ++i) {
			testset.add(datos.getInstance(i));
		}
		this.test = testset;
		this.train = trainset;
		this.test.setPreprocesado(datos.getPreprocesado());
		this.train.setPreprocesado(datos.getPreprocesado());
	}

	/**
	 * Constructor que divide el dataset en entrenamiento y prueba usando una semilla para
	 * selección aleatoria reproducible.
	 *
	 * @param datos Dataset completo con las instancias.
	 * @param porcentaje Porcentaje de datos que se usarán para entrenamiento.
	 * @param semilla Semilla para la generación aleatoria.
	 * @param distancia Tipo de distancia a utilizar en KNN.
	 */
	public Entrenamiento(Dataset datos, double porcentaje, int semilla, String distancia) {
		this.distancia = distancia;
		Dataset trainset = new Dataset(datos.getAtributosEmpty());
		Dataset testset = new Dataset(datos.getAtributosEmpty());
		clases = datos.getClases();
		ArrayList<Integer> indices = new ArrayList<>();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(semilla);
		while(indices.size() < datos.numeroCasos()*porcentaje) {
			int randomNumber = secureRandom.nextInt(datos.numeroCasos());
			if (!indices.contains(randomNumber)) {
				trainset.add(datos.getInstance(randomNumber));
				indices.add(randomNumber);
			}
		}
		for (int i = 0; i < datos.numeroCasos(); ++i) {
			if (!indices.contains(i)) {
				testset.add(datos.getInstance(i));
			}
		}
		this.test = testset;
		this.train =  trainset;
		this.test.setPreprocesado(datos.getPreprocesado());
		this.train.setPreprocesado(datos.getPreprocesado());
	}

	/**
	 * Genera predicciones para el conjunto de prueba usando KNN con el valor de K dado,
	 * y calcula la precisión del modelo.
	 *
	 * @param valorK Número de vecinos a considerar en KNN.
	 */
	public void generarPrediccion(int valorK) {
		Dataset pruebas = new Dataset(test);
		Double aciertos = 0.0;
		for (int i = 0; i < pruebas.numeroCasos(); ++i) {
			ArrayList<Object> instance = new ArrayList<>();
			for (int j = 0; j < pruebas.numeroAtributos()-1; ++j) {
				instance.add(pruebas.getInstance(i).getValores().get(j));
			}
			Instancia nueva = new Instancia(instance);
			String clase = (new KNN(valorK, distancia).clasificar(train, nueva));
			if (clase.equals(test.getInstance(i).getClase())) aciertos += 1;
		}
		Logger logger = Logger.getLogger(Entrenamiento.class.getName());
		if (logger.isLoggable(Level.INFO)) {
			logger.info(String.format("La precisión predictiva: %.2f / %d = %.2f%%", aciertos, test.numeroCasos(), (aciertos / test.numeroCasos()) * 100));
		}
	}

	/**
	 * Genera y muestra la matriz de confusión para el conjunto de prueba usando KNN.
	 *
	 * @param valorK Número de vecinos a considerar en KNN.
	 */
	public void generarMatriz(int valorK) {
		Dataset pruebas = new Dataset(test);
		Matriz confusion = new Matriz (clases.size(), clases.size());
		for (int i = 0; i < pruebas.numeroCasos(); ++i) {
			ArrayList<Object> instance = new ArrayList<>();
			for (int j = 0; j < pruebas.numeroAtributos()-1; ++j) {
				instance.add(pruebas.getInstance(i).getValores().get(j));
			}
			Instancia nueva = new Instancia(instance);
			String clase = (new KNN(valorK, distancia).clasificar(train, nueva));
			confusion.set( clases.indexOf(test.getInstance(i).getClase()),clases.indexOf(clase),confusion.get(clases.indexOf(test.getInstance(i).getClase()),clases.indexOf(clase))+1);
		}
		Logger logger = Logger.getLogger(Entrenamiento.class.getName());
		if (logger.isLoggable(Level.INFO)) {
			logger.info(clases.toString());
		}
		confusion.print();
	}

	/**
	 * Escribe los datasets de entrenamiento y prueba en los archivos indicados.
	 *
	 * @param filename1 Nombre del archivo donde se escribirá el dataset de entrenamiento.
	 * @param filename2 Nombre del archivo donde se escribirá el dataset de prueba.
	 * @throws IOException En caso de error durante la escritura de archivos.
	 */
	public void write(String filename1, String filename2) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename1))) {
			train.write(filename1);
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename2))) {
			test.write(filename2);
		}
	}

	/**
	 * Lee los datasets de entrenamiento y prueba desde los archivos indicados y actualiza
	 * la lista de clases.
	 *
	 * @param filename1 Archivo que contiene el dataset de entrenamiento.
	 * @param filename2 Archivo que contiene el dataset de prueba.
	 * @throws IOException En caso de error durante la lectura de archivos.
	 */
	public void read(String filename1, String filename2) throws IOException {
		train = new Dataset(filename1);
		test = new Dataset(filename2);
		List<String> clasesA = train.getClases();
		List<String> clasesB = test.getClases();
		for (int i = 0; i < clasesB.size(); i++) {
			if (!clasesA.contains(clasesB.get(i))) clasesA.add(clasesB.get(i));
		}
		clases = clasesA;
	}

}
