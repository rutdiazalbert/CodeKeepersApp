package codekeepers.controller;
import codekeepers.model.Cliente;
import codekeepers.model.ClienteDAO;
import codekeepers.model.*;

import java.util.List;
public class ClienteController {

    // Este atributo representa la capa de acceso a datos para los Clientes
    private final ClienteDAO clienteDAO;

    // Constructor que recibe un objeto ClienteDAO
    public ClienteController(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public List<Cliente> showAllClientes() {
        // Método para obtener los datos desde la base de datos usando el método obtenerTodos de la clase genérica GenericDAO definida después en ClienteDAO
        return clienteDAO.obtenerTodos();
    }

    public List<Cliente> showClientesPremium() {
        // Método para obtener los clientes premium desde la base de datos usando un método específico en ClienteDAO
        return clienteDAO.obtenerClientesPremium();
    }

    public List<Cliente> showClientesEstandar() {
        // Método para obtener los clientes estándar desde la base de datos usando un método específico en ClienteDAO
        return clienteDAO.obtenerClientesEstandard();
    }

    // Método para insertar un nuevo cliente en la base de datos usando el método insertar de la clase genérica GenericDAO definida después en ClienteDAO
    public Cliente addNewCliente(String nombre, String nif, String domicilio,boolean tipoCliente, String email) {
        Cliente nuevoCliente;
        // Segun el tipo de cliente pasado como parámetro, crea una instancia de ClientePremium o ClienteEstandar
        if (tipoCliente) {
            nuevoCliente = new ClientePremium(
                    0,  // Dejamos que la base de datos genere el ID
                    nombre,
                    nif,
                    domicilio,
                    email

            );
        } else {
            nuevoCliente = new ClienteEstandard(
                    0,  // Dejamos que la base de datos genere el ID
                    nombre,
                    nif,
                    domicilio,
                    email
            );
        }

        // Llamar al método insertar en ClienteDAO para almacenar el nuevo cliente en la base de datos
        clienteDAO.insertar(nuevoCliente);

        return nuevoCliente;
    }

}
