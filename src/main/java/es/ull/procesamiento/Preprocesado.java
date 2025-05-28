package procesamiento;

import java.util.List;

import datos.Atributo;
import datos.Dataset;

/**
 * @brief Interfaz que define el contrato para clases de preprocesamiento de datasets.
 */
public interface Preprocesado {

	/**
	 * @brief Método para procesar un dataset y devolver la lista de atributos procesados.
	 * @param datos Dataset a procesar.
	 * @return Lista de atributos resultante tras el preprocesamiento.
	 */
	public List<Atributo> procesar(Dataset datos);
}
