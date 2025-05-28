package procesamiento;

import java.util.List;

import datos.Atributo;
import datos.Cuantitativo;
import datos.Dataset;

/**
 * @brief Clase que implementa la normalización de atributos cuantitativos mediante estandarización.
 *
 * Esta clase implementa la interfaz Preprocesado y procesa un Dataset
 * aplicando la estandarización a todos los atributos de tipo Cuantitativo.
 */
public class Estandarizacion implements Preprocesado {

	/**
	 * @brief Procesa el dataset estandarizando los atributos cuantitativos.
	 *
	 * Recorre la lista de atributos del dataset y aplica la estandarización
	 * a cada atributo que sea de tipo Cuantitativo.
	 *
	 * @param datos Dataset a procesar.
	 * @return Lista de atributos estandarizados.
	 */
	public List<Atributo> procesar(Dataset datos) {
		List<Atributo> nuevos = datos.getAtributos();
		Cuantitativo ejemplo = new Cuantitativo();
		for (int i = 0; i < nuevos.size(); i++) {
			if (nuevos.get(i).getClass() == ejemplo.getClass()) {
				ejemplo = (Cuantitativo) nuevos.get(i);
				ejemplo.estandarizacion();
				nuevos.set(i, ejemplo);
			}
		}
		return nuevos;
	}
}
