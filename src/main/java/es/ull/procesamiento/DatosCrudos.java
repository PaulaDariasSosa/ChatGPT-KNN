package procesamiento;

import java.util.List;

import datos.*;

/**
 * @brief Implementación de la interfaz Preprocesado que retorna los datos sin modificaciones.
 */
public class DatosCrudos implements Preprocesado {

	/**
	 * @brief Devuelve los atributos originales del dataset sin ningún tipo de procesamiento.
	 * @param datos Dataset a procesar.
	 * @return Lista de atributos tal cual están en el dataset.
	 */
	public List<Atributo> procesar(Dataset datos) {
		return datos.getAtributos();
	}
}
