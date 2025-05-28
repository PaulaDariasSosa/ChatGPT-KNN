package procesamiento;

import java.util.ArrayList;
import java.util.List;

import datos.*;
import vectores.Vector;

/**
 * @brief Clase que implementa la normalización de atributos cuantitativos.
 *
 * Esta clase implementa la interfaz Preprocesado y procesa un Dataset
 * normalizando todos los atributos de tipo Cuantitativo.
 */
public class Normalizacion implements Preprocesado {

	/**
	 * @brief Procesa el dataset normalizando los atributos cuantitativos.
	 *
	 * Para cada atributo del dataset que sea de tipo Cuantitativo, se normalizan sus valores
	 * utilizando el método normalize() de la clase Vector.
	 *
	 * @param datos Dataset a procesar.
	 * @return Lista de atributos con los valores normalizados.
	 */
	public List<Atributo> procesar(Dataset datos) {
		List<Atributo> nuevos = new ArrayList<Atributo>(datos.getAtributos());
		Cuantitativo ejemplo = new Cuantitativo();
		for (int i = 0; i < nuevos.size(); i++) {
			if (nuevos.get(i).getClass() == ejemplo.getClass()) {
				ejemplo = (Cuantitativo) nuevos.get(i);
				Vector valores = ejemplo.getValores();
				valores.normalize();
				ejemplo.setValores(valores);
				nuevos.set(i, ejemplo);
			}
		}
		return nuevos;
	}
}
