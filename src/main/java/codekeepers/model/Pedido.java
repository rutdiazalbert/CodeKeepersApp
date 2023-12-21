package codekeepers.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_pedido")
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;

    @Column(name = "fecha_hora_pedido")
    private LocalDateTime fechaHora;

    @Column(name = "cantidad")
    private int cantidadArticulo;

    @Column(name = "precio")
    private float precioPedido;

    public Pedido(){}

    public Pedido(int id, Cliente cliente, Articulo articulo, int cantidadArticulo, float precioPedido, LocalDateTime fechaHora) {
        this.id = id;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidadArticulo = cantidadArticulo;
        this.precioPedido = precioPedido;
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(int cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    public float getPrecioPedido() {
        return precioPedido;
    }

    public void setPrecioPedido(float precioPedido) {
        this.precioPedido = precioPedido;
    }

    public boolean pedidoEnviado(){
        int tiempoPreparacion = articulo.getTiempoPreparacion();
        long diferenciaMinutos = ChronoUnit.MINUTES.between(fechaHora, LocalDateTime.now());

        return diferenciaMinutos >= tiempoPreparacion;
    }

    public double precioEnvio() {
        float costoEnvioTotal = articulo.getGastoEnvio();

        // Si el cliente no tiene descuento en envíos, simplemente retornamos el costo total.
        if (cliente instanceof ClienteEstandard) {
            return costoEnvioTotal;
        }

        // Si el cliente es premium, aplicamos el descuento del 20%.
        if (cliente instanceof ClientePremium) {
            float descuento = (cliente.descuentoEnvio() * costoEnvioTotal) / 100;

            return Math.round((costoEnvioTotal - descuento)* Math.pow(10, 2)) / Math.pow(10, 2);
        }
        return costoEnvioTotal;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fecha_hora=" + fechaHora +
                ", cliente=" + cliente.getNif() + cliente.getNombre() +
                ", articulo=" + articulo.getId() + articulo.getDescripcion() +
                ", precio_articulo=" + articulo.getPrecio() +
                ", cantidad_articulo=" + cantidadArticulo +
                ", precio total del pedido=" + precioPedido +
                ", Coste de envio=" + precioEnvio() +
                ", precio total del pedido + envio=" + (precioEnvio() + precioPedido) +
                ", Pedido enviado=" + pedidoEnviado() +
                '}';
    }

}
