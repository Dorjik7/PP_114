package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.*;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.HibernateUtil.getSessionFactory();
    final static Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            session.createNativeQuery(
                            "CREATE TABLE IF NOT EXISTS User" +
                                    " (id INT NOT NULL AUTO_INCREMENT," +
                                    "name VARCHAR(100)," +
                                    "lastname VARCHAR(100)," +
                                    "age INT," +
                                    "PRIMARY KEY (id))"
                    )
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана.");
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                LOGGER.log(SEVERE, e.toString(), e);
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(
                            "DROP TABLE IF EXISTS User"
                    )
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена.");
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                LOGGER.log(SEVERE, e.toString(), e);
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public String saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.get(User.class, id);
            session.getTransaction().commit();
            System.out.println("User удален");
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                LOGGER.log(SEVERE, e.toString(), e);
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> allUsers = null;
        try {
            session.beginTransaction();
            allUsers = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                LOGGER.log(SEVERE, e.toString(), e);
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                LOGGER.log(SEVERE, e.toString(), e);
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}
