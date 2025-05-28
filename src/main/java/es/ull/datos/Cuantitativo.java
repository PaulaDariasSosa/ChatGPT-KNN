package datos;

import vectores.Vector;

/**
 * @brief Clase que representa un atributo cuantitativo (numérico).
 *
 * Hereda de la clase abstracta Atributo y utiliza un objeto de tipo Vector
 * para almacenar los valores numéricos.
 */
public class Cuantitativo extends Atributo {

	/**
	 * @brief Vector que contiene los valores del atributo cuantitativo.
	 */
	private Vector valores;

	/**
	 * @brief Constructor por defecto. Inicializa el nombre vacío y un vector vacío.
	 */
	public Cuantitativo() {
		this.nombre = "";
		this.valores = new Vector();
	}

	/**
	 * @brief Constructor que inicializa el atributo con un nombre.
	 * @param name Nombre del atributo.
	 */
	public Cuantitativo(String name) {
		this();
		this.nombre = name;
	}

	/**
	 * @brief Constructor que inicializa el atributo con un nombre y un valor.
	 * @param name Nombre del atributo.
	 * @param valor Valor numérico inicial.
	 */
	public Cuantitativo(String name, Double valor) {
		this();
		this.nombre = name;
		valores.add(valor);
	}

	/**
	 * @brief Constructor que inicializa el atributo con un nombre y un vector de valores.
	 * @param name Nombre del atributo.
	 * @param valor Vector de valores numéricos.
	 */
	public Cuantitativo(String name, Vector valor) {
		this();
		this.nombre = name;
		this.valores = valor;
	}

	/**
	 * @brief Constructor de copia.
	 * @param otro Objeto Cuantitativo a copiar.
	 */
	public Cuantitativo(Cuantitativo otro) {
		this.nombre = otro.nombre;
		this.valores = otro.valores;
	}

	/**
	 * @brief Devuelve el vector de valores.
	 * @return Vector de valores.
	 */
	public Vector getValores() {
		return this.valores;
	}

	/**
	 * @brief Establece un nuevo vector de valores.
	 * @param nuevos Nuevo vector de valores.
	 */
	public void setValores(Vector nuevos) {
		this.valores = nuevos;
	}

	/**
	 * @brief Calcula el valor mínimo del vector.
	 * @return Valor mínimo.
	 */
	public double minimo() {
		double minimo = this.valores.get(0);
		for (int i = 0; i < this.valores.size(); ++i) {
			if (minimo > this.valores.get(i)) minimo = this.valores.get(i);
		}
		return minimo;
	}

	/**
	 * @brief Calcula el valor máximo del vector.
	 * @return Valor máximo.
	 */
	public double maximo() {
		double maximo = this.valores.get(0);
		for (int i = 0; i < this.valores.size(); ++i) {
			if (maximo < this.valores.get(i)) maximo = this.valores.get(i);
		}
		return maximo;
	}

	/**
	 * @brief Calcula la media de los valores del vector.
	 * @return Valor medio.
	 */
	public double media() {
		double media = 0.0;
		for (int i = 0; i < this.valores.size(); ++i) {
			media += this.valores.get(i);
		}
		return media / this.valores.size();
	}

	/**
	 * @brief Calcula la desviación estándar de los valores.
	 * @return Desviación estándar.
	 */
	public double desviacion() {
		if (valores.size() == 0) return 0.0;
		double media = this.media();
		double suma = 0.0;
		for (int i = 0; i < valores.size(); ++i) {
			suma += Math.pow(valores.get(i) - media, 2);
		}
		return Math.sqrt(suma / valores.size());
	}

	/**
	 * @brief Devuelve el número de elementos del vector.
	 * @return Tamaño del vector.
	 */
	public int size() {
		return this.valores.size();
	}

	/**
	 * @brief Aplica estandarización a los valores del vector.
	 *
	 * Convierte los valores al rango de una distribución con media 0 y desviación estándar 1.
	 * Evita división por cero si la desviación es cero.
	 */
	public void estandarizacion() {
		double media = this.media();
		double desviacion = this.desviacion();
		if (desviacion == 0.0) return;
		for (int i = 0; i < valores.size(); ++i) {
			valores.set(i, (valores.get(i) - media) / desviacion);
		}
	}

	/**
	 * @brief Agrega un nuevo valor al vector.
	 * @param valor Valor numérico a añadir (debe ser Double).
	 */
	@Override
	public void add(Object valor) {
		valores.add((double) valor);
	}

	/**
	 * @brief Obtiene el valor en la posición indicada.
	 * @param i Índice del valor a obtener.
	 * @return Valor en la posición i.
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
	 * @brief Devuelve una representación en forma de cadena del vector.
	 * @return Cadena con los valores numéricos.
	 */
	@Override
	public String toString() {
		return valores.toString();
	}

	/**
	 * @brief Elimina todos los valores del vector.
	 */
	@Override
	public void clear() {
		valores.clear();
	}

}
