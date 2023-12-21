package codekeepers.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ClienteDAO implements GenericDAO<Cliente, String> {

    private final SessionFactory sessionFactory;

    public ClienteDAO() {
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
    public Cliente obtenerPorId(String id_cliente) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cliente.class, id_cliente);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el cliente por ID", e);
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Cliente", Cliente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los clientes", e);
        }
    }

    @Override
    public void insertar(Cliente entidad) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();

            session.merge(entidad);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los clientes", e);
        }
    }

    @Override
    public void actualizar(Cliente entidad) {

    }

    @Override
    public void eliminar(int id) {

    }

    public List<Cliente> obtenerClientesEstandard() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM ClienteEstandard", Cliente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener clientes estándar", e);
        }
    }

    public List<Cliente> obtenerClientesPremium() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM ClientePremium", Cliente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener clientes premium", e);
        }
    }

    public Cliente obtenerPorEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Cliente WHERE email = :email", Cliente.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el cliente por email", e);
        }
    }
}
