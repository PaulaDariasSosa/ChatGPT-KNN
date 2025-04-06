package clasificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import datos.*;
import vectores.Vector;

public class KNN {
  private int vecinos;
  private String tipoDistancia;  // Nuevo atributo

  public KNN (int k) {
	  this.vecinos = k;
  }

  public KNN(int k, String tipoDistancia) {
	  this.vecinos = k;
	  this.tipoDistancia = tipoDistancia.toLowerCase();
  }

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

  public double getDistanciaManhattan(Vector vieja, Vector nueva, List<Double> pesos) {
		double dist = 0.0;
		for (int i = 0; i < nueva.size(); i++) {
			dist += Math.abs((vieja.get(i) - nueva.get(i)) * pesos.get(i));
		}
		return dist;
  }

	public double getDistanciaMinkowski(Vector vieja, Vector nueva, List<Double> pesos, int p) {
		double dist = 0.0;
		for (int i = 0; i < nueva.size(); i++) {
			dist += Math.pow(Math.abs((vieja.get(i) - nueva.get(i)) * pesos.get(i)), p);
		}
		return Math.pow(dist, 1.0 / p);
	}

	// Modificamos getDistancias para usar la nueva función calcularDistancia
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

	public String getClase(List<Instancia> candidatos, Vector distancias) {
		ArrayList<String> nombresClases = new ArrayList<>();
		for (int i = 0; i < candidatos.size(); i++) {
			if (!nombresClases.contains(candidatos.get(i).getClase())) nombresClases.add(candidatos.get(i).getClase());
		}

		// Crea un ArrayList para almacenar las distancias acumuladas de cada clase
		ArrayList<Double> distanciasAcumuladas = new ArrayList<>(Collections.nCopies(nombresClases.size(), 0.0));

		// Acumula las distancias para cada clase (utilizando distancias)
		for (int i = 0; i < candidatos.size(); ++i) {
			int indexClase = nombresClases.indexOf(candidatos.get(i).getClase());
			// Aquí usamos el vector de distancias directamente para acumular
			distanciasAcumuladas.set(indexClase, distanciasAcumuladas.get(indexClase) + distancias.get(i));
		}

		// Obtiene el índice de la clase con la menor distancia acumulada
		int indiceGanador = distanciasAcumuladas.indexOf(Collections.min(distanciasAcumuladas));

		// Si hay un empate, se resuelve aleatoriamente entre las clases con las distancias mínimas acumuladas
		double minDistancia = distanciasAcumuladas.get(indiceGanador);
		ArrayList<String> posiblesGanadores = new ArrayList<>();
		for (int i = 0; i < distanciasAcumuladas.size(); i++) {
			if (distanciasAcumuladas.get(i) == minDistancia) {
				posiblesGanadores.add(nombresClases.get(i));
			}
		}

		// Si hay más de una clase con la distancia mínima acumulada, se resuelve aleatoriamente
		if (posiblesGanadores.size() > 1) {
			Random rand = new Random();
			return posiblesGanadores.get(rand.nextInt(posiblesGanadores.size()));
		}

		return nombresClases.get(indiceGanador);
	}


	public double getDistanciaEuclidea(Vector vieja, Vector nueva) {
	  if (vieja.size()-1 != nueva.size()) {
		  throw new IllegalArgumentException("Los vectores deben tener el mismo tamaño: vieja.size() = " + vieja.size() + ", nueva.size() = " + nueva.size());
	  }
	double dist = 0.0;
	for(int i = 0; i < nueva.size(); i++) {
		dist += Math.pow((vieja.get(i) - nueva.get(i)), 2);
	}
	return Math.sqrt(dist);
  }

  public double getDistanciaEuclidea(Vector vieja, Vector nueva, List<Double> pesos) {
	  if (vieja.size()-1 != nueva.size()) {
		  throw new IllegalArgumentException("Los vectores deben tener el mismo tamaño: vieja.size() = " + vieja.size() + ", nueva.size() = " + nueva.size());
	  }
		double dist = 0.0;
		for(int i = 0; i < nueva.size(); i++) {
			dist += Math.pow((vieja.get(i) - nueva.get(i))*pesos.get(i), 2);
		}
		return Math.sqrt(dist);
	  }

	public String getVecino(List<Instancia> candidatos, Vector distancias) {
		Vector aux = new Vector();
		ArrayList<Integer> indices = new ArrayList<>();

		// Asegúrate de agregar los k primeros vecinos
		for (int i = 0; i < vecinos; i++) {
			aux.add(distancias.get(i));
			indices.add(i);
		}

		// Ahora recorrer las distancias y mantener los k menores
		for (int i = vecinos; i < distancias.size(); ++i) {
			// Si la distancia actual es menor que la mayor en el vector auxiliar
			if (aux.getMax() > distancias.get(i)) {
				// Reemplazar la mayor distancia y su índice
				aux.set(aux.getMaxInt(), distancias.get(i));
				indices.set(aux.getMaxInt(), i);
			}
		}

		// Crear una lista de instancias seleccionadas
		ArrayList<Instancia> elegidos = new ArrayList<>();
		for (int i = 0; i < indices.size(); i++) {
			elegidos.add(candidatos.get(indices.get(i)));
		}

		// Aquí llamas a getClase con los dos parámetros correctamente
		return this.getClase(elegidos, distancias);  // Se pasan ambos parámetros
	}


	public String clasificar(Dataset datos, Instancia nueva) {
	  Vector aux = this.getDistancias(datos, nueva);
	  ArrayList<Instancia> elegidos = new ArrayList<>();
	  for (int i = 0; i < datos.numeroCasos(); ++i) {
	    elegidos.add(datos.getInstance(i));
	  }
	  return this.getVecino(elegidos, aux);
  }
}
