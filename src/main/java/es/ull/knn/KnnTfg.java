package knn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Supplier;
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

	public static void main(String[] args) throws IOException {
		String ruta = "";
		boolean salida = false;
		Dataset datosCrudos = new Dataset();
		Dataset datos = new Dataset();
		String archivo;
		while(!salida) {
			LOGGER.info("Seleccione una opción:");
			LOGGER.info("  [1] Cargar un dataset ");
			LOGGER.info("  [2] Guargar un dataset ");
			LOGGER.info("  [3] Modificar un dataset ");
			LOGGER.info("  [4] Mostrar información ");
			LOGGER.info("  [5] Salir del programa ");
			LOGGER.info("  [6] Realizar experimentación ");
			LOGGER.info("  [7] Algoritmo KNN para una instancia ");
			int opcion = 1;
			Scanner scanner = new Scanner(System.in);
			opcion = scanner.nextInt();
			switch(opcion) {
			case(1):
				archivo = readFile(ruta);
				datosCrudos = new Dataset(ruta+archivo);
				datos = new Dataset(ruta+archivo);
				datos = preprocesar(datos);
				break;
			case(2):
				archivo = readFile(ruta);
				datos.write(ruta+archivo);
				break;
			case(3):
				datos = modify(datos);
				break;
			case(4):
				info(datos);
				break;
			case(5):
				salida = true;
				break;
			case(6):
				experimentar(datos);
				break;
			case(7):
				LOGGER.info(MSG_INTRODUCE_VALOR_K);
				int k = scanner.nextInt();
				KNN intento = new KNN(k);
				String valoresString = "";
				LOGGER.info(MSG_INTRODUCE_VALORES);
				Scanner scanner1 = new Scanner(System.in);
				valoresString = scanner1.nextLine();
				String[] subcadenas = valoresString.split(",");
				ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(subcadenas));
				Instancia instance = new Instancia (valoresString);
				Dataset copiaCrudos = new Dataset(datosCrudos);
				if (datos.getPreprocesado() != 1) {
					arrayList.add("clase");
					copiaCrudos.add(arrayList);
					Preprocesado intento1 = new Normalizacion();
					if (datos.getPreprocesado() == 2) intento1 = new Normalizacion();
					if (datos.getPreprocesado() == 3) intento1 = new Estandarizacion();
					copiaCrudos = new Dataset (intento1.procesar(copiaCrudos));
					instance = copiaCrudos.getInstance(copiaCrudos.numeroCasos()-1);
					copiaCrudos.delete(copiaCrudos.numeroCasos()-1);
					instance.deleteClase();
				}
				if (LOGGER.isLoggable(Level.INFO)) {
					LOGGER.info("La clase elegida es: " + intento.clasificar(copiaCrudos, instance));
				}
				break;
			default:
			}
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
			Scanner scanner = new Scanner(System.in);
			opcion = scanner.nextInt();
			switch(opcion) {
			case(1):
				LOGGER.info("Introduzca el nombre del archivo: ");
				Scanner scanner1 = new Scanner(System.in);
				archivo = scanner1.nextLine();
				break;
			case(2):
				LOGGER.info(ruta);
				break;
			case(3):
				Scanner scanner2 = new Scanner(System.in);
				ruta = scanner2.nextLine();
				break;
			default:
				LOGGER.info("Por defecto");
			}
		}
		return archivo;
	}
	
	public static Dataset modify(Dataset data) {
		int opcion = 2;
		String valores = "";
		while (opcion != 5) {
			LOGGER.info("Elija una opción de modificación ");
			LOGGER.info("      [1] Añadir instancia ");
			LOGGER.info("      [2] Eliminar instancia ");
			LOGGER.info("      [3] Modificar instancia ");
			LOGGER.info("      [4] Cambiar peso de los atributos ");
			LOGGER.info("      [5] Salir ");
			Scanner scanner = new Scanner(System.in);
			opcion = scanner.nextInt();
			switch(opcion) {
			case(1):
				valores = "";
				LOGGER.info(MSG_INTRODUCE_VALORES);
				Scanner scanner1 = new Scanner(System.in);
				valores = scanner1.nextLine();
				String[] subcadenas = valores.split(",");
				ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(subcadenas));
				LOGGER.info((Supplier<String>) arrayList);
				data.add(arrayList);
				return data;
			case(2):
				int valor = 0;
				LOGGER.info("Introduce el indice a eliminar: ");
				scanner1 = new Scanner(System.in);
				valor = scanner1.nextInt();
				data.delete(valor);
				return data;
			case(3):
				valores = "";
				LOGGER.info(MSG_INTRODUCE_VALORES);
				scanner1 = new Scanner(System.in);
				valores = scanner1.nextLine();
				subcadenas = valores.split(",");
				arrayList = new ArrayList<>(Arrays.asList(subcadenas));
				data.add(arrayList);
				valor = 0;
				LOGGER.info("Introduce el indice a eliminar: ");
				scanner1 = new Scanner(System.in);
				valor = scanner1.nextInt();
				data.delete(valor);
				return data;
			case(4):
				data = cambiarPesos(data);
			return data;
			case(5):
				break;
			default:
				break;
			}
		}
		return data;
	}
	
	public static Dataset preprocesar(Dataset data) {
		LOGGER.info("Seleccione la opción de preprocesado: ");
		LOGGER.info("      [1] Datos crudos ");
		LOGGER.info("      [2] Rango 0-1 "); // por defecto
		LOGGER.info("      [3] Estandarización ");
		LOGGER.info("      [4] Salir ");
		int opcion = 1;
		Scanner scanner = new Scanner(System.in);
		opcion = scanner.nextInt();
		switch(opcion) {
		case(1):
			data.setPreprocesado(1);
			return data;
		case(2):
			Normalizacion intento1 = new Normalizacion();
			data = new Dataset (intento1.procesar(data));
			data.setPreprocesado(2);
			break;
		case(3):
			Estandarizacion intento2 = new Estandarizacion();
			data = new Dataset (intento2.procesar(data));
			data.setPreprocesado(3);
			break;
		default:
			intento1 = new Normalizacion();
			data = new Dataset (intento1.procesar(data));
			data.setPreprocesado(2);
		}
		return data;
	}
	
	public static Dataset cambiarPesos(Dataset data) {
		LOGGER.info("          [1] Asignar pesos distintos a todos los atributos ");
		LOGGER.info("          [2] Mismo peso para todos los atributos "); // por defecto ( valor 1 )
		LOGGER.info("          [3] Cambiar peso un atributo");
		int opcion = 1;
		Scanner scanner = new Scanner(System.in);
		opcion = scanner.nextInt();
		scanner.nextLine();
		switch(opcion) {
		case(1):
			String valores = "";
			Scanner scanner1 = new Scanner(System.in);
			valores = scanner1.nextLine();
			String[] subcadenas = valores.split(",");
			ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(subcadenas));
			data.cambiarPeso(arrayList);
			return data;
		case(2):
			double valoresD = 1.0;
			scanner1 = new Scanner(System.in);
			valoresD = scanner1.nextDouble();
			data.cambiarPeso(valoresD);
			return data;
		case(3):
			int valorI = 0;
			LOGGER.info("Introduce el indice del atributo a modificar: ");
			scanner1 = new Scanner(System.in);
			valorI = scanner1.nextInt();
			LOGGER.info("Peso para asignar(Debe estar entre 0 y 1): ");
			valoresD = 1.0;
			valoresD = scanner1.nextDouble();
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
		Scanner scanner = new Scanner(System.in);
		opcion = scanner.nextInt();
		switch(opcion) {
		case(1):
			data.print();
			break;
		case(2):
			int valor = 0;
			Scanner scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
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
		Scanner scanner = new Scanner(System.in);
		opcion = scanner.nextInt();
		switch(opcion) {
		case(1):
			int valor = 0;
			Scanner scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
			Cuantitativo auxiliar = (Cuantitativo) data.get(valor);
			LOGGER.info(auxiliar.getNombre());
			break;
		case(2):
			valor = 0;
			scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
			auxiliar = (Cuantitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Double.toString(auxiliar.media()));
			}
			break;
		case(3):
				valor = 0;
			scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
			auxiliar = (Cuantitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Double.toString(auxiliar.maximo()));
			}
			break;
		case(4):
			valor = 0;
			scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
			auxiliar = (Cuantitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Double.toString(auxiliar.minimo()));
			}
			break;
		case(5):
			valor = 0;
			scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
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
		Scanner scanner = new Scanner(System.in);
		opcion = scanner.nextInt();
		switch(opcion) {
		case(1):
			int valor = 0;
			Scanner scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
			try {
				Cualitativo auxiliar = (Cualitativo) data.get(valor);
				LOGGER.info(auxiliar.getNombre());
			} catch (ClassCastException e) {
				LOGGER.info("Ese atributo no es cualitativo");
			}
			
			break;
		case(2):
			valor = 0;
			scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
			Cualitativo auxiliar = (Cualitativo) data.get(valor);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(Integer.toString(auxiliar.nClases()));
			}
			break;
		case(3):
			valor = 0;
			scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
			auxiliar = (Cualitativo) data.get(valor);
			StringBuilder dataBuilder = new StringBuilder();
			for (String clase : auxiliar.clases()) {
				dataBuilder.append(clase);
				dataBuilder.append(" ");
			}
			LOGGER.info(dataBuilder.toString());
			break;
		case(4):
			valor = 0;
			scanner1 = new Scanner(System.in);
			valor = scanner1.nextInt();
			auxiliar = (Cualitativo) data.get(valor);
			StringBuilder dataBuilder1 = new StringBuilder();
			for (Double clase : auxiliar.frecuencia()) {
				dataBuilder1.append(clase);
				dataBuilder1.append(" ");
			}
			LOGGER.info(dataBuilder1.toString());
			break;
		default:
			break;
		}
	}
	
	public static void experimentar(Dataset datos) throws IOException {
		int opcion = 1;
		Scanner scanner = new Scanner(System.in);
		Entrenamiento nuevo = new Entrenamiento();
		while (opcion != 5) {
			LOGGER.info("              [1] Generacion experimentación normal");
			LOGGER.info("              [2] Generacion experimentación aleatoria");
			LOGGER.info("              [3] Guardar Dataset ");
			LOGGER.info("              [4] Cargar Dataset ");
			LOGGER.info("              [5] Salir");
			opcion = scanner.nextInt();
			switch(opcion) {
			case(1):
				int valor = 0;
				Scanner scanner1 = new Scanner(System.in);
				LOGGER.info(MSG_INTRODUCE_PORCENTAJE_TRAIN);
				valor = scanner1.nextInt();
				nuevo = new Entrenamiento(datos, (double)valor/100);
				LOGGER.info(MSG_INTRODUCE_VALOR_K);
				int k = scanner.nextInt();
				nuevo.generarPrediccion(k);
				nuevo.generarMatriz(k);
				break;
			case(2):
				nuevo = experimentacionAleatoria(datos);
				break;
			case(3):
				LOGGER.info("Introduzca el nombre para el archivo de entrenamiento: ");
				scanner1 = new Scanner(System.in);
				String archivo1 = scanner1.nextLine();
				LOGGER.info("Introduzca el nombre para el archivo de pruebas: ");
				scanner1 = new Scanner(System.in);
				String archivo2 = scanner1.nextLine();
				nuevo.write(archivo1, archivo2);
				break;
			case(4):
				LOGGER.info("Introduzca el nombre del archivo de entrenamiento: ");
				scanner1 = new Scanner(System.in);
				archivo1 = scanner1.nextLine();
				LOGGER.info("Introduzca el nombre del archivo de pruebas: ");
				scanner1 = new Scanner(System.in);
				archivo2 = scanner1.nextLine();
				nuevo.read(archivo1, archivo2);
				LOGGER.info(MSG_INTRODUCE_VALOR_K);
				k = scanner.nextInt();
				nuevo.generarPrediccion(k);
				nuevo.generarMatriz(k);
				break;
			default:
				break;
			}
		}
	}
	
	public static Entrenamiento experimentacionAleatoria(Dataset datos) {
		LOGGER.info("              [1] Semilla(Seed) por defecto");
		LOGGER.info("              [2] Semilla(Seed) manual");
		int opcion = 1;
		Scanner scanner = new Scanner(System.in);
		opcion = scanner.nextInt();
		Entrenamiento nuevo = new Entrenamiento();
		switch(opcion) {
		case(1):
			int valor = 0;
			Scanner scanner1 = new Scanner(System.in);
			LOGGER.info(MSG_INTRODUCE_PORCENTAJE_TRAIN);
			valor = scanner1.nextInt();
			nuevo = new Entrenamiento(datos, (double)valor/100, 1234);
			LOGGER.info(MSG_INTRODUCE_VALOR_K);
			int k = scanner.nextInt();
			nuevo.generarPrediccion(k);
			nuevo.generarMatriz(k);
			return nuevo;
		case(2):
			valor = 0;
			scanner1 = new Scanner(System.in);
			LOGGER.info(MSG_INTRODUCE_PORCENTAJE_TRAIN);
			valor = scanner1.nextInt();
			scanner1 = new Scanner(System.in);
			LOGGER.info("Introduzca la semilla para la generacion aleatoria");
			int valor2 = scanner1.nextInt();
			nuevo = new Entrenamiento(datos, (double)valor/100, valor2);
			LOGGER.info(MSG_INTRODUCE_VALOR_K);
			k = scanner.nextInt();
			nuevo.generarPrediccion(k);
			nuevo.generarMatriz(k);
			return nuevo;
		default:
			break;
		}
		return nuevo;
	}
}

