package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;


import static java.util.logging.Level.*;


public class UserDaoJDBCImpl implements UserDao {
    final static Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS users" +
                "(ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(50)," +
                "lastName VARCHAR(50)," +
                "age INT)";
        try (Connection connection = Util.getConnection()) {
            Statement tableCreateStatement = connection.createStatement();
            tableCreateStatement.executeUpdate(sqlCommand);
            System.out.println("Создана новая таблица.");
        } catch (SQLException | IOException e) {
            LOGGER.log(WARNING, e.toString(), e);
        }
    }


    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection()) {
            Statement dropTableStatement = connection.createStatement();
            dropTableStatement.executeUpdate(sqlCommand);
            System.out.println("Таблица удалена.");
        } catch (SQLException | IOException e) {
            LOGGER.log(WARNING, e.toString(), e);

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "INSERT INTO users(name, lastname, age) VALUES(?,?,?)";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException | IOException e) {
            LOGGER.log(WARNING, e.toString(), e);
        }
    }

    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM users WHERE id";
        try (Connection connection = Util.getConnection()) {
            Statement removeUserStatement = connection.createStatement();
            removeUserStatement.executeUpdate(sqlCommand);
            System.out.println("User удален.");
        } catch (SQLException | IOException e) {
            LOGGER.log(WARNING, e.toString(), e);
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sqlCommand = "SELECT id, name, lastName, age FROM users";
        try (Connection connection = Util.getConnection()) {
            Statement allUsersStatement = connection.createStatement();
            ResultSet results = allUsersStatement.executeQuery(sqlCommand);
            while (results.next()) {
                User user = new User();
                user.setId(results.getLong("id"));
                user.setName(results.getString("name"));
                user.setLastName(results.getString("lastName"));
                user.setAge(results.getByte("age"));
                allUsers.add(user);
                System.out.println("Данные о пользователях: " + allUsers);
            }
        } catch (SQLException | IOException e) {
            LOGGER.log(WARNING, e.toString(), e);

        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String sqlCommand = "DELETE FROM users";
        try (Connection connection = Util.getConnection()) {
            Statement cleanTableStatement = connection.createStatement();
            cleanTableStatement.executeUpdate(sqlCommand);
            System.out.println("Все данные удалены.");
        } catch (SQLException | IOException e) {
            LOGGER.log(WARNING, e.toString(), e);
        }
    }
}

