package datos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dataset {
	private List<Atributo> atributos;
	int preprocesado;
	private Dataset datasetOriginal;
	private static final Logger logger = Logger.getLogger(Dataset.class.getName());

	public Dataset() {
		this.atributos = new ArrayList<Atributo>();
	}
	
	public Dataset(List<Atributo> nuevos) {
		this();
		this.atributos = nuevos;
	}
	
	public Dataset(String filename) throws IOException {
		this();
		this.read(filename);
		this.datasetOriginal = new Dataset(this);
	}
	
	public Dataset(Dataset datos) {
		this();
		this.atributos = new ArrayList<>(datos.atributos);
	}

	public void cambiarPeso(List<String> arrayList) {
		if ( arrayList.size() != atributos.size()) {
			throw new IllegalArgumentException("El número de pesos para asignar debe ser igual al número de atributos");
		} else {
			for (int i = 0; i <  arrayList.size(); i++) {
				Atributo aux = atributos.get(i);
				aux.setPeso(Double.parseDouble(arrayList.get(i)));
				this.atributos.set(i, aux);
			}
		}
	}

	public void cambiarPeso(int index, double peso) {
		Atributo aux = this.atributos.get(index);
		aux.setPeso(peso);
		this.atributos.set(index, aux);
	}

	public void cambiarPeso(double peso) {
	       for (int i = 0; i <  atributos.size(); i++) {
	        Atributo aux = atributos.get(i);
	        aux.setPeso(peso);
	        this.atributos.set(i, aux);
	       }
	}
	
	// Print
	public void print() {
		if (logger.isLoggable(Level.INFO)) {
			logger.info(this.toString());
		}
	}
	
	// toString
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
	
	// Modify (mezcla de add y delete)
	// Add instancia 
	public void add(Instancia nueva) {
		for (int i = 0; i < atributos.size(); ++i) {
			Atributo aux =  atributos.get(i);
			aux.add(nueva.getValores().get(i));
			atributos.set(i, aux);
		}	
	}
	
	public void add(List<String> nueva) {
		for (int i = 0; i < atributos.size(); ++i) {
			Atributo aux =  atributos.get(i);
			try {
				aux.add(Double.parseDouble(nueva.get(i)));
    		} catch (NumberFormatException e) {
    			aux.add(nueva.get(i));
    		}
			atributos.set(i, aux);
		}	
	}
	// Delete
	public void delete(int nueva) {
		for (int i = 0; i < atributos.size(); ++i) {
			Atributo aux = atributos.get(i);
			aux.delete(nueva);
			atributos.set(i, aux);
		}
	}
	
	// Método para escribir el dataset en un archivo CSV
	public void write(String filename) throws IOException {
		File archivo = new File(filename);

		// Verificar si el archivo existe y se puede escribir en él
		if (archivo.exists() && !archivo.canWrite()) {
			throw new IOException("❌ No se puede escribir en el archivo: " + filename);
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			writer.write(this.toString());
		} catch (SecurityException e) {
			throw new IOException("❌ No tienes permisos para escribir en el archivo: " + filename, e);
		}

		// Verificar si el archivo se escribió correctamente
		if (archivo.exists() && archivo.length() == 0) {
			throw new IOException("❌ Error: El archivo se escribió, pero está vacío.");
		}
	}


	public void read(String filename) throws IOException {
		File archivo = new File(filename);

		if (!archivo.exists() || !archivo.isFile()) {
			throw new FileNotFoundException("❌ Error: El archivo no existe o no es válido -> " + filename);
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Leer la primera línea para obtener los nombres de los atributos
			String primeraLinea = reader.readLine();
			if (primeraLinea == null) {
				throw new IOException("❌ Error: El archivo está vacío -> " + filename);
			}
			// llamar al constructor vacio
            String[] attributeNamesArray = primeraLinea.split(",");
            String line;
            if ((line = reader.readLine()) != null) {
            	String[] values = line.split(",");
            	for (int i = 0; i < attributeNamesArray.length ; ++i) {
            		try {
            			this.atributos.add(new Cuantitativo(attributeNamesArray[i], Double.parseDouble(values[i]))); // sino poner encima Double.parseDouble(values[i])
            		} catch (NumberFormatException e) {
            			this.atributos.add(new Cualitativo(attributeNamesArray[i], values[i]));
            		}
            	}
            }
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < attributeNamesArray.length ; ++i) {
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
	
	// numero atributos
	public int numeroAtributos() {
		return atributos.size();
	}
	
	// nombre atributos
	public List<String> nombreAtributos(){
		ArrayList<String> nombres = new ArrayList<>();
		for(int i = 0; i < atributos.size(); ++i) nombres.add(atributos.get(i).getNombre());
		return nombres;
	}
	
	public List<Atributo> getAtributos(){
		return atributos;
	}
	
	public List<Atributo> getAtributosEmpty() {
		ArrayList<Atributo> aux = new ArrayList<Atributo> (atributos.size());
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
	
	// numero casos
	public int numeroCasos() {
		return atributos.get(0).size();
	}

	public List<String> getValores(){
		ArrayList<String> valores = new ArrayList<String>();
		 for (int i = 0; i < atributos.get(0).size(); ++i) {
	        	for (int j = 0; j < atributos.size(); ++j) valores.add(String.valueOf(atributos.get(j).getValor(i)));
		}
		return valores;
	}
	
	public Atributo get(int index) {
		return atributos.get(index);
	}
	
	public Instancia getInstance(int index){
	 	ArrayList<Object> auxiliar = new ArrayList<>();
		for (int i = 0; i < atributos.size(); ++i) auxiliar.add(atributos.get(i).getValor(index));
		return new Instancia (auxiliar);
	}
	
	public String getPesos() {
		ArrayList<String> valores = new ArrayList<String>();
		for (Atributo valor : this.atributos) valores.add(valor.get());
		return valores.toString();
	}
	
	public List<String> getClases() {
		return ((Cualitativo) this.atributos.get(atributos.size()-1)).clases();
	}
	
	public int getPreprocesado() {
		return preprocesado;
	}
	
	public void setPreprocesado(int opcion) {
		this.preprocesado = opcion;
	}
	
	public void setAtributos(List<Atributo> nuevos) {
		this.atributos = nuevos;
	}

	public Dataset getOriginal() {
		return datasetOriginal;
	}

	public void setOriginal() {
		this.datasetOriginal = new Dataset(this);
	}

	public void restaurarOriginal() {
		if (datasetOriginal != null) {
			this.atributos = new ArrayList<>(datasetOriginal.getAtributos());
			logger.info("Se han restaurado los valores originales del dataset.");
		} else {
			logger.info("No hay un dataset original guardado.");
		}
	}


}