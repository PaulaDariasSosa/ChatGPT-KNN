package datos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief Clase que representa un conjunto de datos (dataset), compuesto por múltiples atributos.
 */
public class Dataset {

	/** Lista de atributos que componen el dataset. */
	private List<Atributo> atributos;

	/** Estado de preprocesamiento del dataset. */
	int preprocesado;

	/** Copia del dataset original para restauración. */
	private Dataset datasetOriginal;

	/** Logger para imprimir información de depuración. */
	private static final Logger logger = Logger.getLogger(Dataset.class.getName());

	/**
	 * @brief Constructor por defecto. Inicializa la lista de atributos vacía.
	 */
	public Dataset() {
		this.atributos = new ArrayList<Atributo>();
	}

	/**
	 * @brief Constructor que recibe una lista de atributos.
	 * @param nuevos Lista de atributos iniciales.
	 */
	public Dataset(List<Atributo> nuevos) {
		this();
		this.atributos = nuevos;
	}

	/**
	 * @brief Constructor que carga un dataset desde un archivo CSV.
	 * @param filename Ruta del archivo.
	 * @throws IOException Si ocurre un error durante la lectura.
	 */
	public Dataset(String filename) throws IOException {
		this();
		this.read(filename);
		this.datasetOriginal = new Dataset(this);
	}

	/**
	 * @brief Constructor de copia.
	 * @param datos Dataset a copiar.
	 */
	public Dataset(Dataset datos) {
		this();
		this.atributos = new ArrayList<>(datos.atributos);
	}

	/**
	 * @brief Cambia los pesos de todos los atributos usando una lista de valores en String.
	 * @param arrayList Lista de pesos en formato texto.
	 */
	public void cambiarPeso(List<String> arrayList) {
		if (arrayList.size() != atributos.size()) {
			throw new IllegalArgumentException("El número de pesos para asignar debe ser igual al número de atributos");
		} else {
			for (int i = 0; i < arrayList.size(); i++) {
				Atributo aux = atributos.get(i);
				aux.setPeso(Double.parseDouble(arrayList.get(i)));
				this.atributos.set(i, aux);
			}
		}
	}

	/**
	 * @brief Cambia el peso de un atributo específico.
	 * @param index Índice del atributo.
	 * @param peso Nuevo peso a asignar.
	 */
	public void cambiarPeso(int index, double peso) {
		Atributo aux = this.atributos.get(index);
		aux.setPeso(peso);
		this.atributos.set(index, aux);
	}

	/**
	 * @brief Cambia el peso de todos los atributos.
	 * @param peso Peso a asignar a todos los atributos.
	 */
	public void cambiarPeso(double peso) {
		for (int i = 0; i < atributos.size(); i++) {
			Atributo aux = atributos.get(i);
			aux.setPeso(peso);
			this.atributos.set(i, aux);
		}
	}

	/**
	 * @brief Imprime el dataset usando el logger.
	 */
	public void print() {
		if (logger.isLoggable(Level.INFO)) {
			logger.info(this.toString());
		}
	}

	/**
	 * @brief Devuelve una representación textual del dataset.
	 * @return Cadena con los nombres y valores de los atributos.
	 */
	public String toString() {
		String data = "";
		List<String> valores = this.nombreAtributos();
		valores.addAll(this.getValores());
		int contador = 1;
		StringBuilder dataBuilder = new StringBuilder(data);
		for (int i = 0; i < valores.size(); ++i) {
			dataBuilder.append(valores.get(i));
			if (contador == this.numeroAtributos()) {
				dataBuilder.append("\n");
				contador = 1;
			} else {
				dataBuilder.append(",");
				++contador;
			}
		}
		data = dataBuilder.toString();
		return data;
	}

	/**
	 * @brief Agrega una instancia al dataset.
	 * @param nueva Instancia a agregar.
	 */
	public void add(Instancia nueva) {
		for (int i = 0; i < atributos.size(); ++i) {
			Atributo aux = atributos.get(i);
			aux.add(nueva.getValores().get(i));
			atributos.set(i, aux);
		}
	}

	/**
	 * @brief Agrega una nueva fila de datos al dataset desde una lista de Strings.
	 * @param nueva Lista de valores como Strings.
	 */
	public void add(List<String> nueva) {
		for (int i = 0; i < atributos.size(); ++i) {
			Atributo aux = atributos.get(i);
			try {
				aux.add(Double.parseDouble(nueva.get(i)));
			} catch (NumberFormatException e) {
				aux.add(nueva.get(i));
			}
			atributos.set(i, aux);
		}
	}

	/**
	 * @brief Elimina una instancia del dataset por su índice.
	 * @param nueva Índice de la instancia a eliminar.
	 */
	public void delete(int nueva) {
		for (int i = 0; i < atributos.size(); ++i) {
			Atributo aux = atributos.get(i);
			aux.delete(nueva);
			atributos.set(i, aux);
		}
	}

	/**
	 * @brief Escribe el dataset actual en un archivo CSV.
	 * @param filename Ruta del archivo a escribir.
	 * @throws IOException Si ocurre un error de escritura.
	 */
	public void write(String filename) throws IOException {
		File archivo = new File(filename);
		if (archivo.exists() && !archivo.canWrite()) {
			throw new IOException("❌ No se puede escribir en el archivo: " + filename);
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			writer.write(this.toString());
		} catch (SecurityException e) {
			throw new IOException("❌ No tienes permisos para escribir en el archivo: " + filename, e);
		}

		if (archivo.exists() && archivo.length() == 0) {
			throw new IOException("❌ Error: El archivo se escribió, pero está vacío.");
		}
	}

	/**
	 * @brief Lee un archivo CSV y carga los atributos al dataset.
	 * @param filename Ruta del archivo CSV.
	 * @throws IOException Si ocurre un error de lectura.
	 */
	public void read(String filename) throws IOException {
		File archivo = new File(filename);

		if (!archivo.exists() || !archivo.isFile()) {
			throw new FileNotFoundException("❌ Error: El archivo no existe o no es válido -> " + filename);
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String primeraLinea = reader.readLine();
			if (primeraLinea == null) {
				throw new IOException("❌ Error: El archivo está vacío -> " + filename);
			}

			String[] attributeNamesArray = primeraLinea.split(",");
			String line;
			if ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				for (int i = 0; i < attributeNamesArray.length; ++i) {
					try {
						this.atributos.add(new Cuantitativo(attributeNamesArray[i], Double.parseDouble(values[i])));
					} catch (NumberFormatException e) {
						this.atributos.add(new Cualitativo(attributeNamesArray[i], values[i]));
					}
				}
			}

			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				for (int i = 0; i < attributeNamesArray.length; ++i) {
					Atributo nuevo = this.atributos.get(i);
					try {
						nuevo.add(Double.parseDouble(values[i]));
					} catch (NumberFormatException e) {
						nuevo.add(values[i]);
					}
					this.atributos.set(i, nuevo);
				}
			}
		}
	}

	/**
	 * @brief Retorna la cantidad de atributos del dataset.
	 * @return Número de atributos.
	 */
	public int numeroAtributos() {
		return atributos.size();
	}

	/**
	 * @brief Devuelve una lista con los nombres de todos los atributos.
	 * @return Lista de nombres de atributos.
	 */
	public List<String> nombreAtributos() {
		ArrayList<String> nombres = new ArrayList<>();
		for (int i = 0; i < atributos.size(); ++i)
			nombres.add(atributos.get(i).getNombre());
		return nombres;
	}

	/**
	 * @brief Devuelve la lista de atributos.
	 * @return Lista de atributos.
	 */
	public List<Atributo> getAtributos() {
		return atributos;
	}

	/**
	 * @brief Devuelve una copia vacía de los atributos actuales (sin valores, solo nombres y pesos).
	 * @return Lista de atributos vacíos.
	 */
	public List<Atributo> getAtributosEmpty() {
		ArrayList<Atributo> aux = new ArrayList<>(atributos.size());
		for (int i = 0; i < atributos.size(); ++i) {
			try {
				Cualitativo auxiliar = (Cualitativo) atributos.get(i);
				aux.add(new Cualitativo(auxiliar.getNombre()));
			} catch (ClassCastException e) {
				Cuantitativo auxiliar = (Cuantitativo) atributos.get(i);
				aux.add(new Cuantitativo(auxiliar.getNombre()));
			}
		}
		for (int i = 0; i < atributos.size(); ++i) {
			Atributo prov = aux.get(i);
			prov.setPeso(atributos.get(i).getPeso());
			aux.set(i, prov);
		}
		return aux;
	}

	/**
	 * @brief Retorna la cantidad de instancias (filas) del dataset.
	 * @return Número de casos.
	 */
	public int numeroCasos() {
		return atributos.get(0).size();
	}

	/**
	 * @brief Devuelve los valores de todas las instancias como lista de Strings.
	 * @return Lista de valores.
	 */
	public List<String> getValores() {
		ArrayList<String> valores = new ArrayList<>();
		for (int i = 0; i < atributos.get(0).size(); ++i) {
			for (int j = 0; j < atributos.size(); ++j)
				valores.add(String.valueOf(atributos.get(j).getValor(i)));
		}
		return valores;
	}

	/**
	 * @brief Devuelve el atributo en la posición dada.
	 * @param index Índice del atributo.
	 * @return Objeto Atributo.
	 */
	public Atributo get(int index) {
		return atributos.get(index);
	}

	/**
	 * @brief Devuelve una instancia específica del dataset.
	 * @param index Índice de la instancia.
	 * @return Objeto Instancia.
	 */
	public Instancia getInstance(int index) {
		ArrayList<Object> auxiliar = new ArrayList<>();
		for (int i = 0; i < atributos.size(); ++i)
			auxiliar.add(atributos.get(i).getValor(index));
		return new Instancia(auxiliar);
	}

	/**
	 * @brief Devuelve una representación textual de los pesos de los atributos.
	 * @return Cadena con los pesos.
	 */
	public String getPesos() {
		ArrayList<String> valores = new ArrayList<>();
		for (Atributo valor : this.atributos)
			valores.add(valor.get());
		return valores.toString();
	}

	/**
	 * @brief Devuelve las clases del atributo cualitativo de salida (último).
	 * @return Lista de clases posibles.
	 */
	public List<String> getClases() {
		return ((Cualitativo) this.atributos.get(atributos.size() - 1)).clases();
	}

	/**
	 * @brief Devuelve el valor del estado de preprocesado.
	 * @return Valor del preprocesamiento.
	 */
	public int getPreprocesado() {
		return preprocesado;
	}

	/**
	 * @brief Asigna un valor al estado de preprocesado.
	 * @param opcion Nuevo valor del preprocesamiento.
	 */
	public void setPreprocesado(int opcion) {
		this.preprocesado = opcion;
	}

	/**
	 * @brief Establece una nueva lista de atributos.
	 * @param nuevos Lista de atributos.
	 */
	public void setAtributos(List<Atributo> nuevos) {
		this.atributos = nuevos;
	}

	/**
	 * @brief Devuelve el dataset original almacenado.
	 * @return Dataset original.
	 */
	public Dataset getOriginal() {
		return datasetOriginal;
	}

	/**
	 * @brief Guarda el estado actual como el dataset original.
	 */
	public void setOriginal() {
		this.datasetOriginal = new Dataset(this);
	}

	/**
	 * @brief Restaura el dataset a su versión original.
	 */
	public void restaurarOriginal() {
		if (datasetOriginal != null) {
			this.atributos = new ArrayList<>(datasetOriginal.getAtributos());
			logger.info("Se han restaurado los valores originales del dataset.");
		} else {
			logger.info("No hay un dataset original guardado.");
		}
	}

}
