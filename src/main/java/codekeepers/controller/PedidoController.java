package codekeepers.controller;
import codekeepers.model.Cliente;
import codekeepers.model.ClienteDAO;
import codekeepers.model.*;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoController {

    // Estos atributos representan la capa de acceso a datos para los articulos, clientes y pedidos
    private final PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private ArticuloDAO articuloDAO;

    // Constructor que recibe como objetos PedidoDAO, ClienteDAO, ArticuloDAO
    public PedidoController(PedidoDAO pedidoDAO, ClienteDAO clienteDAO, ArticuloDAO articuloDAO) {
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
        this.articuloDAO = articuloDAO;
    }

    // Método para obtener los datos desde la base de datos sobre los pedidos pendientes de envio usando el método obtenerPedidosPendientesEnvio creado en PedidoDAO
    public List<Pedido> showPedidosPendientesEnvio() {
        return pedidoDAO.obtenerPedidosPendientesEnvio();
    }
    // Método para obtener los datos desde la base de datos sobre los pedidos enviados usando el método obtenerPedidosEnviados creado en PedidoDAO
    public List<Pedido> showPedidosEnviados() {
        return pedidoDAO.obtenerPedidosEnviados();
    }
    // Método para obtener los datos desde la base de datos sobre los pedidos enviados de un determinado cliente usando el método obtenerPedidosEnviadosPorCliente creado en PedidoDAO
    public List<Pedido> showPedidosEnviadosPorCliente(String emailCliente) {
        return pedidoDAO.obtenerPedidosEnviadosPorCliente(emailCliente);
    }
    // Método para obtener los datos desde la base de datos sobre los pedidos pendientes de envio de un determinado cliente usando el método obtenerPedidosPendientesPorCliente creado en PedidoDAO
    public List<Pedido> showPedidosPendientesPorCliente(String emailCliente) {
        return pedidoDAO.obtenerPedidosPendientesPorCliente(emailCliente);
    }

    //Metodo para añadir un nuevo pedido a la base de datos
    public Pedido addNewPedido(String numeroArticulo, String emailCliente, int cantidad) {
        // Obtener cliente y artículo de la base de datos
        Cliente cliente = clienteDAO.obtenerPorEmail(emailCliente);
        Articulo articulo = articuloDAO.obtenerPorId(numeroArticulo);


        // Se calcula el precio segun la cantidad de articulos
        float precioTodosArticulos = articulo.getPrecio() * cantidad;

        // Se crea un nuevo pedido
        Pedido pedidoNuevo = new Pedido(
                0,
                cliente,
                articulo,
                cantidad,
                precioTodosArticulos,
                LocalDateTime.now()

        );

        // Se inserta el pedido en la base de datos
        pedidoDAO.insertar(pedidoNuevo);

        // Se actualiza el stock del artículo en la base de datos
        articulo.setStock(articulo.getStock() - cantidad);


        return pedidoNuevo;
    }

    //Metodo boleano para eliminar el pedido de la base de datos
    public boolean eliminarPedido(int idPedido) {
        Pedido pedido = pedidoDAO.obtenerPorId(String.valueOf(idPedido));

        //Si el pedido no es null y el metodo para validar si el tiempo de preparacion del pedido da true, se procede a eliminar el pedido
        if (pedido != null && validarTiempoPreparacion(pedido)) {
            pedidoDAO.eliminar(idPedido);
            System.out.println("Pedido eliminado exitosamente.");
            return true;
        } else {
            System.out.println("No se puede eliminar el pedido.");
            return false;
        }

    }

    //Metodo para validar si el tiempo de preparacion del articulo del pedido ha terminado y por lo tanto saber si se puede eliminar el pedido o no
    private boolean validarTiempoPreparacion(Pedido pedido) {
        LocalDateTime fechaHoraPedido = pedido.getFechaHora();
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        int tiempoPreparacion = articuloDAO.obtenerTiempoPreparacion(pedido.getArticulo().getId());
        LocalDateTime fechaHoraLimite = fechaHoraPedido.plusMinutes(tiempoPreparacion);

        return fechaHoraActual.isBefore(fechaHoraLimite);
    }

}
