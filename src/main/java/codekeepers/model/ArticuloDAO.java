package codekeepers.model;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ArticuloDAO implements GenericDAO<Articulo, String>  {

    private final SessionFactory sessionFactory;

    public ArticuloDAO() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

        try {
            Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }
    @Override
    public Articulo obtenerPorId(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Articulo.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el artículo por ID", e);
        }
    }

    @Override
    public List<Articulo> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Articulo", Articulo.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los artículos", e);
        }
    }

    @Override
    public void insertar(Articulo articulo) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(articulo);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar el artículo", e);
        }
    }

    public int obtenerTiempoPreparacion(int idArticulo) {
        try (Session session = sessionFactory.openSession()) {
            return (int) session.createQuery("SELECT tiempoPreparacion FROM Articulo WHERE id = :id")
                    .setParameter("id", idArticulo)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el tiempo de preparación del artículo", e);
        }
    }

    @Override
    public void actualizar(Articulo entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entidad);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el artículo", e);
        }
    }
    @Override
    public void eliminar(int id) {

    }
}
