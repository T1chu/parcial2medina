package p2;

import java.io.Serializable;

public class Libro implements Comparable<Libro>, CSVSerializable, Serializable {
    private int id;
    private String titulo;
    private String autor;
    private Categoria categoria;

    public Libro(int id, String titulo, String autor, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public Categoria getCategoria() { return categoria; }

    @Override
    public int compareTo(Libro otro) {
        return Integer.compare(this.id, otro.id);
    }

    @Override
    public String toCSV() {
        return id + "," + titulo + "," + autor + "," + categoria;
    }

    @Override
    public void fromCSV(String csv) {
        String[] parts = csv.split(",");
        this.id = Integer.parseInt(parts[0]);
        this.titulo = parts[1];
        this.autor = parts[2];
        this.categoria = Categoria.valueOf(parts[3]);
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", titulo='" + titulo + '\'' + 
               ", autor='" + autor + '\'' + ", categoria=" + categoria + '}';
    }
}
