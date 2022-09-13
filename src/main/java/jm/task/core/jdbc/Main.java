package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.logging.Logger;
import java.util.List;

import static java.util.logging.Level.SEVERE;

public class Main {
    final static Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());
  //  static List<User> allUsers = null;
    public static void main(String[] args) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(
                            "CREATE TABLE IF NOT EXISTS users" +
                                    " (id INT NOT NULL AUTO_INCREMENT," +
                                    "name VARCHAR(100) DEFAULT NULL," +
                                    "lastname VARCHAR(100) DEFAULT NULL," +
                                    "age INT DEFAULT NULL," +
                                    "PRIMARY KEY (id))"
                    )
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана.");
        } catch (
                HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                LOGGER.log(SEVERE, e.toString(), e);
                e.printStackTrace();
            }
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}




/*
        Session session = SESSION_FACTORY.openSession();
        try{
            session.beginTransaction();
           Query query = session.createQuery("From User");
           allUsers =query.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
            SESSION_FACTORY.close();
        }
        for(User u: allUsers) {
            System.out.println(u.toString());
        }
    }
}


 */

     /*
    private static final UserService userService = new UserServiceImpl();
    private static final User user1 = new User("Adam", "First", (byte) 101);
    private static final User user2 = new User("Eve", "Second", (byte) 100);
    private static final User user3 = new User("Lilith", "Zero", (byte) 125);
    private static final User user4 = new User("Snake", "Apple", (byte) 125);


    public static void main(String[] args) {

        userService.createUsersTable();
        if (userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge())) {
            System.out.println("User с именем – " + "name" + "добавлен в базу данных");
        }
        if (userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge())) {
            System.out.println("User с именем – " + "name" + "добавлен в базу данных");
        }

        if (userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge())) {
            System.out.println("User с именем – " + "name" + "добавлен в базу данных");
        }

        if (userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge())) {
            System.out.println("User с именем – " + "name" + "добавлен в базу данных");
        }


        System.out.println(userService.getAllUsers());


        // userService.removeUserById(2);

        // userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}

*/