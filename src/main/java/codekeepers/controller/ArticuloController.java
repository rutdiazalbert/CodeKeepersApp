package codekeepers.controller;


import codekeepers.model.*;

import java.util.List;

public class ArticuloController {

    // Este atributo representa la capa de acceso a datos para los Articulos
    private final ArticuloDAO articuloDAO;

    // Constructor que recibe un objeto ArticuloDAO
    public ArticuloController(ArticuloDAO articuloDAO) {
        this.articuloDAO = articuloDAO;
    }

    //metodo para obtener los datos desde la base de datos usando el metodo ObtenerTodos de la clase generica GenericDAO definida despues en ArticuloDAO
    public List<Articulo> showAllArticulos() {

        return articuloDAO.obtenerTodos();
    }


    // Método para agregar un nuevo artículo a la base de datos
    public Articulo addNewArticulo(String nombre, String descripcion, float precio, float gastoEnvio, int tiempoPreparacion, int stock) {
        // Crear un nuevo objeto Articulo con los parámetros proporcionados en la Vista
        Articulo nuevoArticulo = new Articulo(
                0,  // Dejamos que la base de datos genere el ID
                nombre,
                descripcion,
                precio,
                gastoEnvio,
                tiempoPreparacion,
                stock
        );

        // Llamamos al método insertar en ArticuloDAO para almacenar el nuevo artículo que hemos creado en la base de datos
        articuloDAO.insertar(nuevoArticulo);

        return nuevoArticulo;

    }
}