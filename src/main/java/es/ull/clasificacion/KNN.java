package clasificacion;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import datos.*;
import vectores.Vector;

/**
 * @brief Implementación del algoritmo K-Nearest Neighbors (KNN).
 *
 * Esta clase permite clasificar instancias basándose en los k vecinos más cercanos
 * usando diferentes métricas de distancia.
 */
public class KNN {
	/** Número de vecinos a considerar */
	private int vecinos;

	/** Tipo de distancia a utilizar ("manhattan", "minkowski", "euclidiana") */
	private String tipoDistancia;

	/**
	 * @brief Constructor que establece el número de vecinos y usa distancia euclidiana por defecto.
	 * @param k Número de vecinos a considerar.
	 */
	public KNN(int k) {
		this.vecinos = k;
	}

	/**
	 * @brief Constructor que establece el número de vecinos y el tipo de distancia.
	 * @param k Número de vecinos a considerar.
	 * @param tipoDistancia Tipo de distancia a utilizar (case insensitive).
	 */
	public KNN(int k, String tipoDistancia) {
		this.vecinos = k;
		this.tipoDistancia = tipoDistancia.toLowerCase();
	}

	/**
	 * @brief Calcula la distancia entre dos vectores según el tipo especificado.
	 * @param vieja Vector de referencia.
	 * @param nueva Vector a comparar.
	 * @param pesos Pesos para cada dimensión.
	 * @return Distancia calculada.
	 */
	public double calcularDistancia(Vector vieja, Vector nueva, List<Double> pesos) {
		switch (tipoDistancia) {
			case "manhattan":
				return getDistanciaManhattan(vieja, nueva, pesos);
			case "minkowski":
				return getDistanciaMinkowski(vieja, nueva, pesos, 3); // p = 3 por defecto
			case "euclidiana":
			default:
				return getDistanciaEuclidea(vieja, nueva, pesos);
		}
	}

	/**
	 * @brief Calcula la distancia Manhattan entre dos vectores con pesos.
	 * @param vieja Vector de referencia.
	 * @param nueva Vector a comparar.
	 * @param pesos Pesos para cada dimensión.
	 * @return Distancia Manhattan ponderada.
	 */
	public double getDistanciaManhattan(Vector vieja, Vector nueva, List<Double> pesos) {
		double dist = 0.0;
		for (int i = 0; i < nueva.size(); i++) {
			dist += Math.abs((vieja.get(i) - nueva.get(i)) * pesos.get(i));
		}
		return dist;
	}

	/**
	 * @brief Calcula la distancia Minkowski entre dos vectores con pesos.
	 * @param vieja Vector de referencia.
	 * @param nueva Vector a comparar.
	 * @param pesos Pesos para cada dimensión.
	 * @param p Orden de la distancia Minkowski.
	 * @return Distancia Minkowski ponderada.
	 */
	public double getDistanciaMinkowski(Vector vieja, Vector nueva, List<Double> pesos, int p) {
		double dist = 0.0;
		for (int i = 0; i < nueva.size(); i++) {
			dist += Math.pow(Math.abs((vieja.get(i) - nueva.get(i)) * pesos.get(i)), p);
		}
		return Math.pow(dist, 1.0 / p);
	}

	/**
	 * @brief Calcula el vector de distancias entre un conjunto de instancias y una instancia nueva.
	 * @param datos Dataset con las instancias existentes.
	 * @param nueva Instancia nueva a clasificar.
	 * @return Vector con las distancias calculadas.
	 */
	public Vector getDistancias(Dataset datos, Instancia nueva) {
		Vector aux = new Vector();
		ArrayList<Atributo> pesosString = new ArrayList<>(datos.getAtributos());
		ArrayList<Double> pesosDouble = new ArrayList<>();
		for (Atributo str : pesosString) {
			pesosDouble.add(str.getPeso());
		}
		for (int i = 0; i < datos.numeroCasos(); ++i) {
			aux.add(calcularDistancia(datos.getInstance(i).getVector(), nueva.getVector(), pesosDouble));
		}
		return aux;
	}

	/**
	 * @brief Determina la clase predominante entre un conjunto de candidatos,
	 *        basándose en la suma acumulada de sus distancias.
	 *
	 * En caso de empate, se resuelve aleatoriamente entre las clases con mínima distancia acumulada.
	 *
	 * @param candidatos Lista de instancias candidatas.
	 * @param distancias Vector con las distancias correspondientes a los candidatos.
	 * @return Clase ganadora.
	 */
	public String getClase(List<Instancia> candidatos, Vector distancias) {
		ArrayList<String> nombresClases = new ArrayList<>();
		for (int i = 0; i < candidatos.size(); i++) {
			if (!nombresClases.contains(candidatos.get(i).getClase()))
				nombresClases.add(candidatos.get(i).getClase());
		}

		ArrayList<Double> distanciasAcumuladas = new ArrayList<>(Collections.nCopies(nombresClases.size(), 0.0));

		for (int i = 0; i < candidatos.size(); ++i) {
			int indexClase = nombresClases.indexOf(candidatos.get(i).getClase());
			distanciasAcumuladas.set(indexClase, distanciasAcumuladas.get(indexClase) + distancias.get(i));
		}

		int indiceGanador = distanciasAcumuladas.indexOf(Collections.min(distanciasAcumuladas));

		double minDistancia = distanciasAcumuladas.get(indiceGanador);
		ArrayList<String> posiblesGanadores = new ArrayList<>();
		for (int i = 0; i < distanciasAcumuladas.size(); i++) {
			if (distanciasAcumuladas.get(i) == minDistancia) {
				posiblesGanadores.add(nombresClases.get(i));
			}
		}

		if (posiblesGanadores.size() > 1) {
			SecureRandom secureRand = new SecureRandom();
			int index = secureRand.nextInt(posiblesGanadores.size());
			return posiblesGanadores.get(index);
		}

		return nombresClases.get(indiceGanador);
	}

	/**
	 * @brief Calcula la distancia Euclidea sin pesos entre dos vectores.
	 *
	 * @param vieja Vector de referencia.
	 * @param nueva Vector a comparar.
	 * @return Distancia Euclidea.
	 * @throws IllegalArgumentException si los tamaños de los vectores no coinciden.
	 */
	public double getDistanciaEuclidea(Vector vieja, Vector nueva) {
		if (vieja.size() - 1 != nueva.size()) {
			throw new IllegalArgumentException("Los vectores deben tener el mismo tamaño: vieja.size() = " + vieja.size() + ", nueva.size() = " + nueva.size());
		}
		double dist = 0.0;
		for (int i = 0; i < nueva.size(); i++) {
			dist += Math.pow((vieja.get(i) - nueva.get(i)), 2);
		}
		return Math.sqrt(dist);
	}

	/**
	 * @brief Calcula la distancia Euclidea ponderada entre dos vectores.
	 *
	 * @param vieja Vector de referencia.
	 * @param nueva Vector a comparar.
	 * @param pesos Pesos para cada dimensión.
	 * @return Distancia Euclidea ponderada.
	 * @throws IllegalArgumentException si los tamaños de los vectores no coinciden.
	 */
	public double getDistanciaEuclidea(Vector vieja, Vector nueva, List<Double> pesos) {
		if (vieja.size() - 1 != nueva.size()) {
			throw new IllegalArgumentException("Los vectores deben tener el mismo tamaño: vieja.size() = " + vieja.size() + ", nueva.size() = " + nueva.size());
		}
		double dist = 0.0;
		for (int i = 0; i < nueva.size(); i++) {
			dist += Math.pow((vieja.get(i) - nueva.get(i)) * pesos.get(i), 2);
		}
		return Math.sqrt(dist);
	}

	/**
	 * @brief Obtiene la clase del vecino más cercano entre los k vecinos más próximos.
	 *
	 * Selecciona los k vecinos con menor distancia y luego determina la clase ganadora.
	 *
	 * @param candidatos Lista de instancias candidatas.
	 * @param distancias Vector con las distancias correspondientes a los candidatos.
	 * @return Clase asignada por el algoritmo KNN.
	 */
	public String getVecino(List<Instancia> candidatos, Vector distancias) {
		Vector aux = new Vector();
		ArrayList<Integer> indices = new ArrayList<>();

		// Agregar los primeros k vecinos
		for (int i = 0; i < vecinos; i++) {
			aux.add(distancias.get(i));
			indices.add(i);
		}

		// Mantener los k vecinos más cercanos
		for (int i = vecinos; i < distancias.size(); ++i) {
			if (aux.getMax() > distancias.get(i)) {
				aux.set(aux.getMaxInt(), distancias.get(i));
				indices.set(aux.getMaxInt(), i);
			}
		}

		ArrayList<Instancia> elegidos = new ArrayList<>();
		for (int i = 0; i < indices.size(); i++) {
			elegidos.add(candidatos.get(indices.get(i)));
		}

		return this.getClase(elegidos, distancias);
	}

	/**
	 * @brief Clasifica una instancia nueva según los k vecinos más cercanos.
	 *
	 * @param datos Dataset con las instancias de entrenamiento.
	 * @param nueva Instancia nueva a clasificar.
	 * @return Clase asignada a la instancia nueva.
	 */
	public String clasificar(Dataset datos, Instancia nueva) {
		Vector aux = this.getDistancias(datos, nueva);
		ArrayList<Instancia> elegidos = new ArrayList<>();
		for (int i = 0; i < datos.numeroCasos(); ++i) {
			elegidos.add(datos.getInstance(i));
		}
		return this.getVecino(elegidos, aux);
	}
}
