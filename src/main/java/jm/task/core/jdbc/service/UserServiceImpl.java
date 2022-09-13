package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao hibernateDao = new UserDaoHibernateImpl();


    public void createUsersTable() {
        hibernateDao.createUsersTable();
    }

    public void dropUsersTable() {
        hibernateDao.dropUsersTable();
    }

    public boolean saveUser(String name, String lastName, byte age) {
        hibernateDao.saveUser(name, lastName, age);
        return false;
    }

    public void removeUserById(long id) {
        hibernateDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return hibernateDao.getAllUsers();
    }

    public void cleanUsersTable() {
        hibernateDao.cleanUsersTable();
    }
}