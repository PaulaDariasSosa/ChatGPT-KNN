package datos;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Clase que representa un atributo cualitativo.
 *
 * Esta clase almacena una lista de valores de tipo String que representan atributos no numéricos.
 * Hereda de la clase abstracta Atributo.
 */
public class Cualitativo extends Atributo {

	/**
	 * @brief Lista de valores cualitativos (cadenas de texto).
	 */
	private List<String> valores;

	/**
	 * @brief Constructor por defecto. Inicializa con nombre vacío y lista vacía de valores.
	 */
	public Cualitativo() {
		super();
		this.nombre = "";
		this.valores = new ArrayList<String>();
	}

	/**
	 * @brief Constructor que inicializa el atributo con un nombre.
	 * @param name Nombre del atributo.
	 */
	public Cualitativo(String name) {
		this();
		this.nombre = name;
	}

	/**
	 * @brief Constructor que inicializa el atributo con un nombre y un valor.
	 * @param name Nombre del atributo.
	 * @param valor Valor inicial del atributo.
	 */
	public Cualitativo(String name, String valor) {
		this();
		this.nombre = name;
		valores.add(valor);
	}

	/**
	 * @brief Constructor que inicializa el atributo con un nombre y una lista de valores.
	 * @param name Nombre del atributo.
	 * @param valor Lista de valores del atributo.
	 */
	public Cualitativo(String name, List<String> valor) {
		this();
		this.nombre = name;
		this.valores = valor;
	}

	/**
	 * @brief Constructor de copia.
	 * @param otro Objeto Cualitativo a copiar.
	 */
	public Cualitativo(Cualitativo otro) {
		this.nombre = otro.nombre;
		this.valores = otro.valores;
	}

	/**
	 * @brief Obtiene la lista de valores del atributo.
	 * @return Lista de valores tipo String.
	 */
	public List<String> getValores() {
		return this.valores;
	}

	/**
	 * @brief Asigna una nueva lista de valores al atributo.
	 * @param nuevos Lista de nuevos valores.
	 */
	public void setValores(List<String> nuevos) {
		this.valores = nuevos;
	}

	/**
	 * @brief Devuelve una lista con las clases únicas del atributo.
	 * @return Lista de clases distintas.
	 */
	public List<String> clases() {
		ArrayList<String> clases = new ArrayList<>();
		for(int i = 0; i < this.valores.size(); ++i) {
			if(!clases.contains(this.valores.get(i))) clases.add(this.valores.get(i));
		}
		return clases;
	}

	/**
	 * @brief Devuelve el número de clases distintas del atributo.
	 * @return Número de clases únicas.
	 */
	public int nClases() {
		return this.clases().size();
	}

	/**
	 * @brief Calcula la frecuencia de aparición de cada clase.
	 * @return Lista de frecuencias relativas de cada clase.
	 */
	public List<Double> frecuencia() {
		List<String> clases = this.clases();
		ArrayList<Double> frecuencias = new ArrayList<>();
		for (int j = 0; j < this.nClases(); ++j) {
			double auxiliar = 0;
			for(int i = 0; i < this.valores.size(); ++i) {
				if(clases.get(j).equals(this.valores.get(i))) auxiliar++;
			}
			frecuencias.add(auxiliar / this.valores.size());
		}
		return frecuencias;
	}

	/**
	 * @brief Devuelve el número de elementos almacenados.
	 * @return Número de elementos en la lista de valores.
	 */
	public int size() {
		return this.valores.size();
	}

	/**
	 * @brief Agrega un nuevo valor a la lista.
	 * @param valor Valor que se añadirá (debe ser String).
	 */
	@Override
	public void add(Object valor) {
		valores.add((String) valor);
	}

	/**
	 * @brief Obtiene el valor en la posición indicada.
	 * @param i Índice del valor a obtener.
	 * @return Valor correspondiente al índice.
	 */
	@Override
	public Object getValor(int i) {
		return valores.get(i);
	}

	/**
	 * @brief Elimina el valor en la posición especificada.
	 * @param index Índice del valor a eliminar.
	 */
	@Override
	public void delete(int index) {
		valores.remove(index);
	}

	/**
	 * @brief Devuelve una representación en forma de cadena del atributo.
	 * @return Cadena con los valores.
	 */
	@Override
	public String toString() {
		return valores.toString();
	}

	/**
	 * @brief Elimina todos los valores del atributo.
	 */
	@Override
	public void clear() {
		valores.clear();
	}

}
