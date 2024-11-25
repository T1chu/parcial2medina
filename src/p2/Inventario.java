package p2;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class Inventario<T extends CSVSerializable & Serializable & Comparable<T>> {
    private List<T> elementos = new ArrayList<>();

    public void agregar(T elemento) {
        elementos.add(elemento);
    }

    public T obtener(int indice) {
        return elementos.get(indice);
    }

    public void eliminar(int indice) {
        elementos.remove(indice);
    }

    public List<T> filtrar(Predicate<T> criterio) {
        List<T> filtrados = new ArrayList<>();
        for (T elemento : elementos) {
            if (criterio.test(elemento)) {
                filtrados.add(elemento);
            }
        }
        return filtrados;
    }

    public void ordenar() {
        Collections.sort(elementos);
    }

    public void ordenar(Comparator<T> comparator) {
        elementos.sort(comparator);
    }

    public void guardarEnArchivo(String ruta) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(elementos);  // Guardar los elementos en el archivo
            System.out.println("Archivo guardado en: " + ruta);
        }
    }

    public void cargarDesdeArchivo(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            elementos = (List<T>) ois.readObject();
        }
    }

    public void guardarEnCSV(String ruta) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            for (T elemento : elementos) {
                bw.write(elemento.toCSV());
                bw.newLine();
            }
            System.out.println("Archivo CSV guardado en: " + ruta);
        }
    }

    public void cargarDesdeCSV(String ruta, T prototipo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                T nuevo = (T) prototipo.getClass().getDeclaredConstructor().newInstance();
                nuevo.fromCSV(linea);
                elementos.add(nuevo);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public void paraCadaElemento(java.util.function.Consumer<T> accion) {
        elementos.forEach(accion);
    }
}
