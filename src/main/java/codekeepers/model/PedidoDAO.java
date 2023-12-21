package codekeepers.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class PedidoDAO implements GenericDAO<Pedido, String> {

    private final SessionFactory sessionFactory;

    public PedidoDAO() {
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
    public Pedido obtenerPorId(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Pedido.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el pedido por ID", e);
        }
    }

    @Override
    public List<Pedido> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los pedidos", e);
        }
    }

    @Override
    public void insertar(Pedido pedido) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(pedido);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar el pedido", e);
        }
    }

    @Override
    public void actualizar(Pedido entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entidad);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el pedido", e);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Pedido pedido = session.get(Pedido.class, id);
            if (pedido != null) {
                session.delete(pedido);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el pedido", e);
        }
    }

    public List<Pedido> obtenerPedidosPendientesEnvio() {
        try (Session session = sessionFactory.openSession()) {
            Query<Pedido> query = session.createQuery(
                    "FROM Pedido p " +
                            "JOIN FETCH p.articulo a " +
                            "WHERE TIMESTAMPADD(MINUTE, a.tiempoPreparacion, p.fechaHora) > CURRENT_TIMESTAMP",
                    Pedido.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener pedidos pendientes de envío", e);
        }
    }

    // Método para obtener solamente los pedidos pendientes de envío de un determinado cliente
    public List<Pedido> obtenerPedidosPendientesPorCliente(String emailCliente) {
        try (Session session = sessionFactory.openSession()) {
            Query<Pedido> query = session.createQuery(
                    "FROM Pedido p " +
                            "JOIN FETCH p.articulo a " +
                            "JOIN FETCH p.cliente c " +
                            "WHERE TIMESTAMPADD(MINUTE, a.tiempoPreparacion, p.fechaHora) > CURRENT_TIMESTAMP " +
                            "AND c.email = :emailCliente",
                    Pedido.class);
            query.setParameter("emailCliente", emailCliente);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener pedidos pendientes de envío por cliente", e);
        }
    }

    // Método para obtener solamente los pedidos enviados (los que poseen un artículo cuyo tiempo de preparación ha finalizado)
    public List<Pedido> obtenerPedidosEnviados() {
        try (Session session = sessionFactory.openSession()) {
            Query<Pedido> query = session.createQuery(
                    "FROM Pedido p " +
                            "JOIN FETCH p.articulo a " +
                            "WHERE TIMESTAMPADD(MINUTE, a.tiempoPreparacion, p.fechaHora) <= CURRENT_TIMESTAMP",
                    Pedido.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener pedidos enviados", e);
        }
    }

    // Método para obtener solamente los pedidos enviados de un determinado cliente
    public List<Pedido> obtenerPedidosEnviadosPorCliente(String emailCliente) {
        try (Session session = sessionFactory.openSession()) {
            Query<Pedido> query = session.createQuery(
                    "FROM Pedido p " +
                            "JOIN FETCH p.articulo a " +
                            "JOIN FETCH p.cliente c " +
                            "WHERE TIMESTAMPADD(MINUTE, a.tiempoPreparacion, p.fechaHora) <= CURRENT_TIMESTAMP " +
                            "AND c.email = :emailCliente",
                    Pedido.class);
            query.setParameter("emailCliente", emailCliente);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener pedidos enviados por cliente", e);
        }
    }
}
