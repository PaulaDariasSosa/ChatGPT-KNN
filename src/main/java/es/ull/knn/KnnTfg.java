package knn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import clasificacion.KNN;
import datos.*;
import procesamiento.*;
import entrenamiento.*;

public class KnnTfg {
	private static final Logger LOGGER = Logger.getLogger(KnnTfg.class.getName());
	private static final String MSG_INTRODUCE_VALORES = "Introduce los valores: ";
	private static final String MSG_INTRODUCE_VALOR_K = "Introduce el valor de k: ";
	private static final String MSG_INTRODUCE_PORCENTAJE_TRAIN = "Introduzca el porcentaje para el conjunto de entrenamiento";
	private static final String MSG_SELECCIONAR_OPCION = "Seleccione una opción:";
	private static final String MSG_DISTANCIA_EUCLIDEA = "  [1] Distancia Euclidea ";
	private static final String MSG_DISTANCIA_MANHATTAN = "  [2] Distancia Manhattan ";
	private static final String MSG_DISTANCIA_MINKOWSKI = "  [3] Distancia Minkowski ";
	private static final String DISTANCIA_EUCLIDIANA = "euclidiana";
	private static final String DISTANCIA_MANHATTAN = "manhattan";
	private static final String DISTANCIA_MINKOWSKI = "minkowski";
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		String ruta = "";
		boolean salida = false;
		Dataset datosCrudos = new Dataset();
		Dataset datos = new Dataset();

		while (!salida) {
			mostrarMenu();
			int opcion = leerOpcion(scanner, 1,7);

			switch (opcion) {
				case 1:
					datosCrudos = cargarDataset(ruta);
					datos = preprocesar(new Dataset(datosCrudos));
					break;
				case 2:
					guardarDataset(ruta, datos);
					break;
				case 3:
					datos = modify(datos);
					break;
				case 4:
					info(datos);
					break;
				case 5:
					salida = true;
					break;
				case 6:
					experimentar(datos);
					break;
				case 7:
					ejecutarKNN(datos, datosCrudos);
					break;
				default:
					LOGGER.warning("Opción no válida.");
			}
		}
	}

	private static void mostrarMenu() {
		LOGGER.info(MSG_SELECCIONAR_OPCION);
		LOGGER.info("  [1] Cargar un dataset ");
		LOGGER.info("  [2] Guardar un dataset ");
		LOGGER.info("  [3] Modificar un dataset ");
		LOGGER.info("  [4] Mostrar información ");
		LOGGER.info("  [5] Salir del programa ");
		LOGGER.info("  [6] Realizar experimentación ");
		LOGGER.info("  [7] Algoritmo KNN para una instancia ");
	}

	private static int leerOpcion(Scanner scanner, int min, int max) {
		int opcion = -1;
		boolean entradaValida = false;

		while (!entradaValida) {
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(String.format("Seleccione una opción (%d - %d): ", min, max));
			}

			if (scanner.hasNextInt()) {
				opcion = scanner.nextInt();
				scanner.nextLine();  // Limpia el buffer después de nextInt()
				if (opcion >= min && opcion <= max) {
					entradaValida = true;
				} else {
					LOGGER.info("Opción fuera de rango. Intente de nuevo.");
				}
			} else {
				LOGGER.info("Entrada no válida. Introduzca un número.");
				scanner.next();  // Limpia la entrada incorrecta
			}
		}
		return opcion;
	}

	private static Dataset cargarDataset(String ruta) throws IOException {
		String archivo = readFile(ruta);
		return new Dataset(ruta + archivo);
	}

	private static void guardarDataset(String ruta, Dataset datos) throws IOException {
		String archivo = readFile(ruta);
		datos.write(ruta + archivo);
	}

	private static void ejecutarKNN(Dataset datos, Dataset datosCrudos) {
		LOGGER.info(MSG_INTRODUCE_VALOR_K);
		int k = leerOpcion(scanner, 1, datos.numeroCasos());
		String tipoDistancia = "";
		LOGGER.info(MSG_SELECCIONAR_OPCION);
		LOGGER.info(MSG_DISTANCIA_EUCLIDEA);
		LOGGER.info(MSG_DISTANCIA_MANHATTAN);
		LOGGER.info(MSG_DISTANCIA_MINKOWSKI);
		int opcion = leerOpcion(scanner, 1,3);
		switch(opcion) {
		case 1:
			tipoDistancia = DISTANCIA_EUCLIDIANA;
			break;
		case 2:
			tipoDistancia = DISTANCIA_MANHATTAN;
			break;
		case 3:
			tipoDistancia = DISTANCIA_MINKOWSKI;
			break;
		default:
			tipoDistancia = DISTANCIA_EUCLIDIANA;
		}
		KNN intento = new KNN(k, tipoDistancia);

		LOGGER.info(MSG_INTRODUCE_VALORES);
		String valoresString = scanner.nextLine();

		String[] subcadenas = valoresString.split(",");
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(subcadenas));
		Instancia instance = new Instancia(valoresString);

		Dataset copiaCrudos = new Dataset(datosCrudos);

		if (datos.getPreprocesado() != 1) {
			arrayList.add("clase");
			copiaCrudos.add(arrayList);
			Preprocesado intento1 = datos.getPreprocesado() == 2 ? new Normalizacion() : new Estandarizacion();
			copiaCrudos = new Dataset(intento1.procesar(copiaCrudos));
			instance = copiaCrudos.getInstance(copiaCrudos.numeroCasos() - 1);
			copiaCrudos.delete(copiaCrudos.numeroCasos() - 1);
			instance.deleteClase();
		}

		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("La clase elegida es: " + intento.clasificar(copiaCrudos, instance));
		}
	}
	
	public static String readFile(String ruta) {
		int opcion = 2;
		String archivo = "";
		while (opcion != 4) {
			LOGGER.info("Se debe especificar la ruta y nombre del archivo: ");
			LOGGER.info("      [1] Introducir nombre");
			LOGGER.info("      [2] Mostrar ruta ");
			LOGGER.info("      [3] Cambiar ruta ");
			LOGGER.info("      [4] Salir ");
			opcion = leerOpcion(scanner,1,4);
			switch(opcion) {
			case(1):
				LOGGER.info("Introduzca el nombre del archivo: ");
				archivo = scanner.nextLine();
				break;
			case(2):
				LOGGER.info(ruta);
				break;
			case(3):
				ruta = scanner.nextLine();
				break;
			default:
				LOGGER.info("Por defecto");
			}
		}
		return archivo;
	}
	
	public static Dataset modify(Dataset data) {
		int opcion = 2;
		while (opcion != 5) {
			LOGGER.info("Elija una opción de modificación ");
			LOGGER.info("      [1] Añadir instancia ");
			LOGGER.info("      [2] Eliminar instancia ");
			LOGGER.info("      [3] Modificar instancia ");
			LOGGER.info("      [4] Cambiar peso de los atributos ");
			LOGGER.info("      [5] Restaurar dataset ");
			LOGGER.info("      [6] Salir ");
			opcion = leerOpcion(scanner,1,5);
			switch(opcion) {
			case(1):
				return anadirInstancia(data);
			case(2):
				return eliminarInstancia(data);
			case(3):
				return modificarInstancia(data);
			case(4):
				data = cambiarPesos(data);
			return data;
			case(5):
					data.restaurarOriginal();
					return data;
			case(6):
				break;
			default:
				break;
			}
		}
		return data;
	}

	public static Dataset anadirInstancia(Dataset data) {
		String valores = "";
		LOGGER.info(MSG_INTRODUCE_VALORES);
		Scanner scanner1 = new Scanner(System.in);
		valores = scanner1.nextLine();
		String[] subcadenas = valores.split(",");
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(subcadenas));
		data.add(arrayList);
		return data;
	}

	public static Dataset eliminarInstancia(Dataset data) {
		if (data.numeroCasos() == 0) {
			LOGGER.warning("No hay instancias para eliminar.");
			return data;
		}
		int valor = 0;
		LOGGER.info("Introduce el indice a eliminar: ");
		valor = leerOpcion(scanner, 0, data.numeroCasos()-1);
		data.delete(valor);
		return data;
	}

	public static Dataset modificarInstancia(Dataset data) {
		if (data.numeroCasos() == 0) {
			LOGGER.warning("No hay instancias para eliminar.");
			return data;
		}
		String valores = "";
		LOGGER.info(MSG_INTRODUCE_VALORES);
		valores = scanner.nextLine();
		String[] subcadenas = valores.split(",");
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(subcadenas));
		data.add(arrayList);
		int valor = 0;
		LOGGER.info("Introduce el índice a eliminar (instancia antigua): ");
		valor = leerOpcion(scanner, 0, data.numeroCasos()-1);
		data.delete(valor);
		return data;
	}
	
	public static Dataset preprocesar(Dataset data) {
		LOGGER.info("Seleccione la opción de preprocesado: ");
		LOGGER.info("      [1] Datos crudos ");
		LOGGER.info("      [2] Rango 0-1 "); // por defecto
		LOGGER.info("      [3] Estandarización ");
		LOGGER.info("      [4] Salir ");
		int opcion = 1;
		opcion = leerOpcion(scanner,1,4);
		switch(opcion) {
		case(1):
			data.setPreprocesado(1);
			return data;
		case(2):
			Normalizacion intento1 = new Normalizacion();
			if (data.getOriginal() == null) {
				data.setOriginal();
			}
			data = new Dataset (intento1.procesar(data));
			LOGGER.info("El dataset ha sido normalizado.");
			data.setPreprocesado(2);
			break;
		case(3):
			Estandarizacion intento2 = new Estandarizacion();
			if (data.getOriginal() == null) {
				data.setOriginal();
			}
			data = new Dataset (intento2.procesar(data));
			LOGGER.info("El dataset ha sido estandarizado.");
			data.setPreprocesado(3);
			break;
		default:
			intento1 = new Normalizacion();
			if (data.getOriginal() == null) {
				data.setOriginal();
			}
			data = new Dataset (intento1.procesar(data));
			LOGGER.info("El dataset ha sido normalizado.");
			data.setPreprocesado(2);
		}
		return data;
	}
	
	public static Dataset cambiarPesos(Dataset data) {
		LOGGER.info("          [1] Asignar pesos distintos a todos los atributos ");
		LOGGER.info("          [2] Mismo peso para todos los atributos "); // por defecto ( valor 1 )
		LOGGER.info("          [3] Cambiar peso un atributo");
		int opcion = 1;
		opcion = leerOpcion(scanner,1,3);
		scanner.nextLine();
		switch(opcion) {
		case(1):
			String valores = "";
			valores = scanner.nextLine();
			String[] subcadenas = valores.split(",");
			ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(subcadenas));
			data.cambiarPeso(arrayList);
			return data;
		case(2):
			double valoresD = 1.0;
			valoresD = scanner.nextDouble();
			data.cambiarPeso(valoresD);
			return data;
		case(3):
			int valorI = 0;
			LOGGER.info("Introduce el indice del atributo a modificar: ");
			valorI = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			LOGGER.info("Peso para asignar(Debe estar entre 0 y 1): ");
			valoresD = 1.0;
			valoresD = scanner.nextDouble();
			data.cambiarPeso(valorI, valoresD);
			return data;
		default:
			break;
		}
		return data;
	}
	
	public static void info(Dataset data) {
		LOGGER.info("          [1] Mostrar dataset ");
		LOGGER.info("          [2] Mostrar instancia ");
		LOGGER.info("          [3] Mostrar información atributos cuantitativos");
		LOGGER.info("          [4] Mostrar información atributos cualitativos");
		LOGGER.info("          [5] Mostrar pesos de los atributos");
		int opcion = 1;
		opcion = leerOpcion(scanner,1,5);
		switch(opcion) {
		case(1):
			data.print();
			break;
		case(2):
			int valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroCasos()-1);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(data.getInstance(valor).toString());
			}
			break;
		case(3):
			infoCuantitativo(data);
			break;
		case(4):
			infoCualitativo(data);
			break;
		case(5):
			LOGGER.info(data.getPesos());
			break;
		default:
			break;
		}
	}
	
	public static void infoCuantitativo(Dataset data) {
		LOGGER.info("              [1] Mostrar nombre ");
		LOGGER.info("              [2] Mostrar media ");
		LOGGER.info("              [3] Mostrar maximo");
		LOGGER.info("              [4] Mostrar minimo");
		LOGGER.info("              [5] Mostrar desviación tipica");
		int opcion = 1;
		opcion = leerOpcion(scanner,1,5);
		switch(opcion) {
		case(1):
			int valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			Cuantitativo auxiliar = (Cuantitativo) data.get(valor);
			LOGGER.info(auxiliar.getNombre());
			break;
		case(2):
			valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			auxiliar = (Cuantitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Double.toString(auxiliar.media()));
			}
			break;
		case(3):
				valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			auxiliar = (Cuantitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Double.toString(auxiliar.maximo()));
			}
			break;
		case(4):
			valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			auxiliar = (Cuantitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Double.toString(auxiliar.minimo()));
			}
			break;
		case(5):
			valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			auxiliar = (Cuantitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Double.toString(auxiliar.desviacion()));
			}
			break;
		default:
			break;
		}
	}
	
	public static void infoCualitativo(Dataset data) {
		LOGGER.info("              [1] Mostrar nombre ");
		LOGGER.info("              [2] Mostrar número de clases ");
		LOGGER.info("              [3] Mostrar clases");
		LOGGER.info("              [4] Mostrar frecuencia");
		int opcion = 1;
		opcion = leerOpcion(scanner,1,4);
		switch(opcion) {
		case(1):
			int valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			try {
				Cualitativo auxiliar = (Cualitativo) data.get(valor);
				LOGGER.info(auxiliar.getNombre());
			} catch (ClassCastException e) {
				LOGGER.info("Ese atributo no es cualitativo");
			}
			
			break;
		case(2):
			valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			Cualitativo auxiliar = (Cualitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Integer.toString(auxiliar.nClases()));
			}
			break;
		case(3):
			valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			auxiliar = (Cualitativo) data.get(valor);
			StringBuilder dataBuilder = new StringBuilder();
			for (String clase : auxiliar.clases()) {
				dataBuilder.append(clase);
				dataBuilder.append(" ");
			}
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(dataBuilder.toString());
			}
			break;
		case(4):
			valor = 0;
			valor = leerOpcion(scanner, 0, data.numeroAtributos()-1);
			auxiliar = (Cualitativo) data.get(valor);
			StringBuilder dataBuilder1 = new StringBuilder();
			for (Double clase : auxiliar.frecuencia()) {
				dataBuilder1.append(clase);
				dataBuilder1.append(" ");
			}
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(dataBuilder1.toString());
			}
			break;
		default:
			break;
		}
	}
	
	public static void experimentar(Dataset datos) throws IOException {
		int opcion = 1;
		Entrenamiento nuevo = new Entrenamiento();
		while (opcion != 5) {
			LOGGER.info("              [1] Generacion experimentación normal");
			LOGGER.info("              [2] Generacion experimentación aleatoria");
			LOGGER.info("              [3] Guardar Dataset ");
			LOGGER.info("              [4] Cargar Dataset ");
			LOGGER.info("              [5] Salir");
			opcion = leerOpcion(scanner,1,5);
			switch(opcion) {
			case(1):
				int valor = 0;
				LOGGER.info(MSG_INTRODUCE_PORCENTAJE_TRAIN);
				valor = leerOpcion(scanner, 1, 100);
				String tipoDistancia = "";
				LOGGER.info(MSG_SELECCIONAR_OPCION);
				LOGGER.info(MSG_DISTANCIA_EUCLIDEA);
				LOGGER.info(MSG_DISTANCIA_MANHATTAN);
				LOGGER.info(MSG_DISTANCIA_MINKOWSKI);
				opcion = leerOpcion(scanner, 1,3);
				switch(opcion) {
					case 1:
						tipoDistancia = DISTANCIA_EUCLIDIANA;
						break;
					case 2:
						tipoDistancia = DISTANCIA_MANHATTAN;
						break;
					case 3:
						tipoDistancia =DISTANCIA_MINKOWSKI;
						break;
					default:
						tipoDistancia = DISTANCIA_EUCLIDIANA;
				}
				nuevo = new Entrenamiento(datos, (double)valor/100, tipoDistancia);
				LOGGER.info(MSG_INTRODUCE_VALOR_K);
				int k = leerOpcion(scanner, 1, datos.numeroCasos());
				nuevo.generarPrediccion(k);
				nuevo.generarMatriz(k);
				break;
			case(2):
				nuevo = experimentacionAleatoria(datos);
				break;
			case(3):
				LOGGER.info("Introduzca el nombre para el archivo de entrenamiento: ");
				String archivo1 = scanner.nextLine();
				LOGGER.info("Introduzca el nombre para el archivo de pruebas: ");
				String archivo2 = scanner.nextLine();
				nuevo.write(archivo1, archivo2);
				break;
			case(4):
				LOGGER.info("Introduzca el nombre del archivo de entrenamiento: ");
				archivo1 = scanner.nextLine();
				LOGGER.info("Introduzca el nombre del archivo de pruebas: ");
				archivo2 = scanner.nextLine();
				nuevo.read(archivo1, archivo2);
				LOGGER.info(MSG_INTRODUCE_VALOR_K);
				k = leerOpcion(scanner, 1, datos.numeroCasos());
				nuevo.generarPrediccion(k);
				nuevo.generarMatriz(k);
				break;
			default:
				break;
			}
		}
	}
	
	public static Entrenamiento experimentacionAleatoria(Dataset datos) {
		LOGGER.info(MSG_SELECCIONAR_OPCION);
		LOGGER.info(MSG_DISTANCIA_EUCLIDEA);
		LOGGER.info(MSG_DISTANCIA_MANHATTAN);
		LOGGER.info(MSG_DISTANCIA_MINKOWSKI);
		int opcionDistancia = leerOpcion(scanner, 1,3);
		String tipoDistancia = "";
		switch(opcionDistancia) {
			case 1:
				tipoDistancia = DISTANCIA_EUCLIDIANA;
				break;
			case 2:
				tipoDistancia = DISTANCIA_MANHATTAN;
				break;
			case 3:
				tipoDistancia = DISTANCIA_MINKOWSKI;
				break;
			default:
				tipoDistancia = DISTANCIA_EUCLIDIANA;
		}
		LOGGER.info("              [1] Semilla(Seed) por defecto");
		LOGGER.info("              [2] Semilla(Seed) manual");
		int opcion = 1;
		opcion = leerOpcion(scanner,1,2);
		Entrenamiento nuevo = new Entrenamiento();
		switch(opcion) {
		case(1):
			int valor = 0;
			LOGGER.info(MSG_INTRODUCE_PORCENTAJE_TRAIN);
			valor = leerOpcion(scanner, 1, 100);
			nuevo = new Entrenamiento(datos, (double)valor/100, 1234, tipoDistancia);
			LOGGER.info(MSG_INTRODUCE_VALOR_K);
			int k = leerOpcion(scanner, 1, datos.numeroCasos());
			nuevo.generarPrediccion(k);
			nuevo.generarMatriz(k);
			return nuevo;
		case(2):
			valor = 0;
			LOGGER.info(MSG_INTRODUCE_PORCENTAJE_TRAIN);
			valor = leerOpcion(scanner, 1, 100);
			LOGGER.info("Introduzca la semilla para la generacion aleatoria");
			int valor2 = leerOpcion(scanner, 0, Integer.MAX_VALUE);
			nuevo = new Entrenamiento(datos, (double)valor/100, valor2, tipoDistancia);
			LOGGER.info(MSG_INTRODUCE_VALOR_K);
			k = leerOpcion(scanner, 1, datos.numeroCasos());
			nuevo.generarPrediccion(k);
			nuevo.generarMatriz(k);
			return nuevo;
		default:
			break;
		}
		return nuevo;
	}
}

