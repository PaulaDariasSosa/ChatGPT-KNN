package datos;

/**
 * @brief Clase abstracta que representa un atributo genérico con un nombre y un peso.
 *
 * Esta clase proporciona una estructura base para atributos que pueden tener diferentes tipos de valores.
 * Define métodos abstractos que deben ser implementados por sus subclases.
 */
public abstract class Atributo {

	/**
	 * @brief Peso del atributo.
	 */
	protected double peso = 1;

	/**
	 * @brief Nombre del atributo.
	 */
	protected String nombre;

	/**
	 * @brief Devuelve los valores almacenados en el atributo.
	 *
	 * @return Un objeto que representa los valores del atributo.
	 */
	public abstract Object getValores();

	/**
	 * @brief Devuelve el nombre del atributo.
	 *
	 * @return Una cadena de texto con el nombre del atributo.
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * @brief Devuelve el peso del atributo.
	 *
	 * @return Un valor double representando el peso del atributo.
	 */
	public double getPeso() {
		return this.peso;
	}

	/**
	 * @brief Establece un nuevo nombre para el atributo.
	 *
	 * @param nuevo Nuevo nombre que se desea asignar.
	 */
	public void setNombre(String nuevo) {
		this.nombre = nuevo;
	}

	/**
	 * @brief Establece un nuevo peso para el atributo.
	 *
	 * @param nuevo Nuevo valor de peso.
	 */
	public void setPeso(double nuevo) {
		this.peso = nuevo;
	}

	/**
	 * @brief Devuelve una representación textual simple del atributo.
	 *
	 * @return Una cadena con el formato "nombre: peso".
	 */
	public String get() {
		return (this.nombre + ": " + this.peso);
	}

	/**
	 * @brief Devuelve la cantidad de elementos almacenados en el atributo.
	 *
	 * @return Número de elementos.
	 */
	public abstract int size();

	/**
	 * @brief Añade un nuevo valor al atributo.
	 *
	 * @param valor El valor que se desea agregar.
	 */
	public abstract void add(Object valor);

	/**
	 * @brief Elimina el valor en la posición especificada.
	 *
	 * @param indice Índice del valor a eliminar.
	 */
	public abstract void delete(int indice);

	/**
	 * @brief Devuelve el valor almacenado en la posición i.
	 *
	 * @param i Índice del valor a obtener.
	 * @return Valor almacenado en la posición i.
	 */
	public abstract Object getValor(int i);

	/**
	 * @brief Devuelve una representación en forma de cadena del atributo.
	 *
	 * @return Cadena que representa el contenido del atributo.
	 */
	public abstract String toString();

	/**
	 * @brief Elimina todos los valores almacenados en el atributo.
	 */
	public abstract void clear();

	/**
	 * @brief Constructor protegido por defecto.
	 *
	 * Inicializa el nombre del atributo como una cadena vacía.
	 */
	protected Atributo() {
		this.nombre = "";
	}

	/**
	 * @brief Constructor de copia protegido.
	 *
	 * @param otro Otro objeto Atributo desde el cual copiar los valores.
	 */
	protected Atributo(Atributo otro) {
		this.nombre = otro.nombre;
		this.peso = otro.peso;
	}
}
