package datos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vectores.Vector;

/**
 * @brief Clase que representa una instancia de datos con atributos y una clase (opcional).
 *
 * Cada instancia almacena una lista de objetos que pueden representar valores numéricos (Double o Integer)
 * y una clase asociada al final de la lista. Incluye métodos para normalización, estandarización,
 * y extracción del vector de características.
 */
public class Instancia {

	/**
	 * @brief Lista de valores que representa los atributos de la instancia.
	 */
	private List<Object> valores;

	/**
	 * @brief Constructor por defecto. Inicializa la instancia con una lista vacía.
	 */
	public Instancia(){
		this.valores = new ArrayList<Object>();
	}

	/**
	 * @brief Constructor que recibe una lista de objetos como valores.
	 *
	 * @param nuevos Lista de valores para inicializar la instancia.
	 */
	public Instancia(List<Object> nuevos){
		this.valores = nuevos;
	}

	/**
	 * @brief Constructor que recibe una cadena separada por comas para crear la lista de valores.
	 *
	 * @param nuevos Cadena de texto con los valores separados por comas.
	 */
	public Instancia(String nuevos){
		String[] subcadenas = nuevos.split(",");
		ArrayList<Object> arrayList = new ArrayList<>(Arrays.asList(subcadenas));
		this.valores = arrayList;
	}

	/**
	 * @brief Obtiene la lista de valores de la instancia.
	 *
	 * @return Lista de objetos que representan los valores de la instancia.
	 */
	public List<Object> getValores() {
		return this.valores;
	}

	/**
	 * @brief Devuelve una representación textual de la instancia.
	 *
	 * @return Cadena con los valores de la instancia.
	 */
	public String toString() {
		return valores.toString();
	}

	/**
	 * @brief Devuelve un vector con los valores numéricos de la instancia (sin incluir la clase).
	 *
	 * @return Objeto de tipo Vector con los atributos numéricos.
	 */
	public Vector getVector() {
		Vector aux = new Vector();
		for (int i = 0; i < valores.size()-1; ++i) {
			if (valores.get(i) instanceof Double) {
				aux.add((Double) valores.get(i));
			} else if (valores.get(i) instanceof Integer) {
				aux.add((int) valores.get(i));
			}
		}
		return aux;
	}

	/**
	 * @brief Obtiene el valor que representa la clase de la instancia.
	 *
	 * @return Cadena que representa la clase (último elemento de la lista).
	 */
	public String getClase() {
		return (String) this.valores.get(valores.size()-1);
	}

	/**
	 * @brief Normaliza los valores numéricos del vector de atributos en la instancia.
	 *
	 * Convierte todos los valores numéricos a un rango [0, 1].
	 */
	public void normalizar() {
		Vector aux = this.getVector();
		aux.normalize();
		ArrayList<Object> arrayListObject = new ArrayList<>();
		for (Double d : aux.getValores()) {
			arrayListObject.add(d);
		}
		this.valores = arrayListObject;
	}

	/**
	 * @brief Estandariza los valores numéricos del vector de atributos en la instancia.
	 *
	 * Ajusta los valores para que tengan media 0 y desviación estándar 1.
	 */
	public void estandarizar() {
		Vector aux = this.getVector();
		double media = 0.0;
		for(int i = 0; i < aux.size(); ++i) {
			media += aux.get(i);
		}
		media = media / aux.size();
		double auxiliar = 0;
		for(int i = 0; i < aux.size(); ++i) {
			auxiliar += (aux.get(i) - media) * (aux.get(i) - media);
		}
		auxiliar /= this.valores.size();
		double desviacion = Math.sqrt(auxiliar);
		for (int i = 0; i < aux.size(); ++i) {
			aux.set(i, (aux.get(i) - media) / desviacion);
		}
		ArrayList<Object> arrayListObject = new ArrayList<>();
		for (Double d : aux.getValores()) {
			arrayListObject.add(d);
		}
		this.valores = arrayListObject;
	}

	/**
	 * @brief Elimina la clase de la instancia (último valor de la lista).
	 */
	public void deleteClase() {
		valores.remove(valores.size() - 1);
	}

}
