package codekeepers.model;

import jakarta.persistence.*;

@Entity
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_articulo")
    private int id;

    private String nombre;

    private String descripcion;

    private float precio;
    @Column(name= "gasto_envio")
    private float gastoEnvio;
    @Column(name= "tiempo_preparacion")
    private int tiempoPreparacion;

    private int stock;

    public Articulo(){}

    public Articulo(int id, String nombre, String descripcion, float precio, float gastoEnvio, int tiempoPreparacion, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.gastoEnvio = gastoEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getGastoEnvio() {
        return gastoEnvio;
    }

    public void setGastoEnvio(float gasto_envio) {
        this.gastoEnvio = gasto_envio;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempo_preparacion) {
        this.tiempoPreparacion = tiempo_preparacion;
    }

    public int getStock() {
        return stock;
    }

    public Articulo setStock(int stock) {
        this.stock = stock;
        return this;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio + "€" +
                ", gasto_envio=" + gastoEnvio + "€" +
                ", tiempo_preparacion -> " + tiempoPreparacion + " minutos" +
                '}';
    }
}

