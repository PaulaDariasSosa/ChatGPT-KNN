package vectores;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @class Vector
 * @brief Clase que representa un vector de valores reales (double) y permite operaciones básicas entre vectores.
 *
 * Proporciona funcionalidad como suma, producto escalar, normalización,
 * escritura/lectura desde archivos y manipulación directa de los elementos del vector.
 *
 * @author
 * @version 1.0
 */
public class Vector {
    private List<Double> coef;
    private static final String MSG_MISMO_TAMANO = "Los vectores deben tener el mismo tamaño";

    /** @brief Constructor vacío. Inicializa el vector como una lista vacía. */
    public Vector() {
        coef = new ArrayList<>();
    }

    /**
     * @brief Constructor que recibe un array de tipo double.
     * @param array Arreglo de valores que se añaden al vector.
     */
    public Vector(double[] array) {
        this();
        for (double value : array) {
            coef.add(value);
        }
    }

    /**
     * @brief Constructor que recibe una lista de valores double.
     * @param coef Lista de coeficientes que se copian al vector.
     */
    public Vector(List<Double> coef) {
        this.coef = new ArrayList<>(coef);
    }

    /**
     * @brief Constructor que inicializa un vector con un número fijo de ceros.
     * @param size Tamaño del vector.
     */
    public Vector(int size) {
        this();
        for (int i = 0; i < size; ++i) {
            coef.add(0.0);
        }
    }

    /**
     * @brief Constructor que lee valores desde un archivo usando Scanner.
     * @param file Archivo de entrada.
     * @throws FileNotFoundException Si el archivo no se encuentra.
     */
    public Vector(File file) throws FileNotFoundException {
        coef = new ArrayList<>();
        readFileWithScanner(file);
    }

    /**
     * @brief Constructor que construye un vector a partir de una cadena separada por comas.
     * @param str Cadena con números separados por comas.
     */
    public Vector(String str) {
        coef = new ArrayList<>();
        String[] values = str.split(",");
        for (String value : values) {
            coef.add(Double.parseDouble(value.trim()));
        }
    }

    /**
     * @brief Constructor de copia.
     * @param otro Otro objeto Vector del cual se copia el contenido.
     */
    public Vector(Vector otro) {
        coef = new ArrayList<>(otro.coef);
    }

    /** @brief Devuelve el tamaño (dimensión) del vector. */
    public int size() {
        return coef.size();
    }

    /** @brief Vacía el contenido del vector. */
    public void clear() {
        coef.clear();
    }

    /** @brief Representación en forma de cadena del vector. */
    public String toString() {
        return coef.toString();
    }

    /** @brief Imprime el vector usando un logger a nivel INFO. */
    public void print() {
        Logger logger = Logger.getLogger(Vector.class.getName());
        if (logger.isLoggable(Level.INFO)) {
            logger.info(this.toString());
        }
    }

    /**
     * @brief Obtiene el valor en la posición especificada.
     * @param index Índice del valor a obtener.
     * @return Valor en la posición indicada.
     */
    public double get(int index) {
        return coef.get(index);
    }

    /**
     * @brief Establece un nuevo valor en una posición específica.
     * @param index Índice donde establecer el valor.
     * @param value Valor a establecer.
     */
    public void set(int index, double value) {
        coef.set(index, value);
    }

    /**
     * @brief Añade un valor al final del vector.
     * @param value Valor a añadir.
     */
    public void add(double value) {
        coef.add(value);
    }

    /**
     * @brief Suma otro vector a este vector.
     * @param other Vector a sumar.
     * @throws IllegalArgumentException Si los vectores no tienen el mismo tamaño.
     */
    public void add(Vector other) {
        if (this.size() != other.size()) throw new IllegalArgumentException(MSG_MISMO_TAMANO);
        for (int i = 0; i < this.size(); i++) {
            coef.set(i, coef.get(i) + other.get(i));
        }
    }

    /**
     * @brief Elimina el valor en la posición indicada.
     * @param index Índice del valor a eliminar.
     */
    public void remove(int index) {
        coef.remove(index);
    }

    /** @brief Devuelve el valor máximo del vector. */
    public double getMax() {
        double max = Double.NEGATIVE_INFINITY;
        for (double value : coef) {
            if (value > max) max = value;
        }
        return max;
    }

    /** @brief Devuelve el índice del valor máximo del vector. */
    public int getMaxInt() {
        double max = Double.NEGATIVE_INFINITY;
        int maxint = -1;
        for (int i = 0; i < coef.size(); ++i) {
            if (coef.get(i) > max) {
                max = coef.get(i);
                maxint = i;
            }
        }
        return maxint;
    }

    /** @brief Devuelve el valor mínimo del vector. */
    public double getMin() {
        double min = Double.POSITIVE_INFINITY;
        for (double value : coef) {
            if (value < min) min = value;
        }
        return min;
    }

    /**
     * @brief Calcula el producto escalar con otro vector.
     * @param other Vector con el cual se hace el producto escalar.
     * @return Resultado del producto escalar.
     * @throws IllegalArgumentException Si los vectores no tienen el mismo tamaño.
     */
    public double productoEscalar(Vector other) {
        if (this.size() != other.size()) throw new IllegalArgumentException(MSG_MISMO_TAMANO);
        double result = 0;
        for (int i = 0; i < this.size(); i++) result += this.get(i) * other.get(i);
        return result;
    }

    /**
     * @brief Suma un valor escalar a cada componente del vector.
     * @param value Valor escalar.
     * @return Nuevo vector con el resultado.
     */
    public Vector sum(double value) {
        Vector suma = new Vector();
        for (int i = 0; i < coef.size(); i++) {
            suma.add(coef.get(i) + value);
        }
        return suma;
    }

    /**
     * @brief Suma otro vector a este vector y devuelve un nuevo vector.
     * @param other Vector a sumar.
     * @return Nuevo vector resultado de la suma.
     * @throws IllegalArgumentException Si los vectores no tienen el mismo tamaño.
     */
    public Vector sum(Vector other) {
        if (this.size() != other.size()) throw new IllegalArgumentException(MSG_MISMO_TAMANO);
        Vector suma = new Vector();
        for (int i = 0; i < this.size(); i++) {
            suma.add(coef.get(i) + other.get(i));
        }
        return suma;
    }

    /**
     * @brief Compara si dos vectores son iguales en contenido.
     * @param other Otro vector.
     * @return true si son iguales.
     */
    public boolean equals(Vector other) {
        return this.coef.equals(other.coef);
    }

    /**
     * @brief Verifica si otro vector tiene la misma dimensión.
     * @param other Otro vector.
     * @return true si tienen el mismo tamaño.
     */
    public boolean equalDimension(Vector other) {
        return this.size() == other.size();
    }

    /**
     * @brief Comprueba si el vector contiene un valor específico.
     * @param value Valor a comprobar.
     * @return true si existe en el vector.
     */
    public boolean isContent(double value) {
        return coef.contains(value);
    }

    /**
     * @brief Concatena otro vector a este vector.
     * @param other Vector a concatenar.
     */
    public void concat(Vector other) {
        coef.addAll(other.coef);
    }

    /**
     * @brief Escribe el vector en un archivo.
     * @param filename Nombre del archivo.
     * @throws IOException Si hay un error al escribir.
     */
    public void write(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(this.toString());
        }
    }

    /**
     * @brief Escribe el vector en un archivo.
     * @param file Archivo destino.
     * @throws IOException Si hay un error al escribir.
     */
    public void write(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(this.toString());
        }
    }

    /**
     * @brief Lee datos desde un archivo de texto plano.
     * @param filename Nombre del archivo.
     * @throws IOException Si hay un error al leer.
     */
    public void read(String filename) throws IOException {
        coef.clear();
        readFile(filename);
    }

    /**
     * @brief Lee datos desde un archivo con Scanner.
     * @param file Archivo fuente.
     * @throws FileNotFoundException Si el archivo no existe.
     */
    public void read(File file) throws FileNotFoundException {
        coef.clear();
        readFileWithScanner(file);
    }

    /**
     * @brief Lee datos desde un Scanner.
     * @param scanner Scanner con entrada de datos.
     */
    public void read(Scanner scanner) {
        coef.clear();
        while (scanner.hasNextDouble()) {
            coef.add(scanner.nextDouble());
        }
    }

    /** @brief Calcula el módulo (norma) del vector. */
    public double module() {
        double sum = 0;
        for (double value : coef) {
            sum += Math.pow(value, 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * @brief Multiplica el vector por un escalar.
     * @param scalar Valor escalar.
     */
    public void multiply(double scalar) {
        for (int i = 0; i < coef.size(); i++) {
            coef.set(i, coef.get(i) * scalar);
        }
    }

    /** @brief Normaliza el vector al rango [0, 1]. */
    public void normalize() {
        double min  = this.getMin();
        double max = this.getMax();
        for (int i = 0; i < coef.size(); ++i)
            coef.set(i, (coef.get(i) - min) / (max - min));
    }

    /** @brief Calcula la media (promedio) del vector. */
    public double avg() {
        double sum = 0;
        for (double value : coef) {
            sum += value;
        }
        return sum / coef.size();
    }

    /**
     * @brief Método auxiliar que lee valores línea por línea.
     * @param filename Nombre del archivo.
     * @throws IOException Si hay un error de lectura.
     */
    private void readFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                coef.add(Double.parseDouble(line));
            }
        }
    }

    /**
     * @brief Método auxiliar que lee valores con Scanner, usando coma o espacio como separador.
     * @param file Archivo fuente.
     * @throws FileNotFoundException Si el archivo no existe.
     */
    private void readFileWithScanner(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] tokens = line.split("[,\\s]+");
                    for (String token : tokens) {
                        try {
                            coef.add(Double.parseDouble(token.trim()));
                        } catch (NumberFormatException e) {
                            Logger.getLogger(Vector.class.getName()).log(Level.WARNING, "Valor no válido: " + token, e);
                        }
                    }
                }
            }
        }
    }

    /**
     * @brief Devuelve la lista interna de coeficientes del vector.
     * @return Lista de valores double.
     */
    public List<Double> getValores() {
        return this.coef;
    }
}
