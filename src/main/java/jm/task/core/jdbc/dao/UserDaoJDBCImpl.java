package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    Connection connection = Util.getConnection();

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS task " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), " +
                "lastName VARCHAR(20), age TINYINT) ";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);

            System.out.println("Таблица создана");
        } catch (SQLException ex) {
            System.out.println("Ошибка создания");
            System.out.println(ex);
        }
    }

    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE IF EXISTS task";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);

            System.out.println("Таблица удалена");
        } catch (SQLException ex) {
            System.out.println("Ошибка удаления");
            System.out.println(ex);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "INSERT INTO task (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных");

        } catch (SQLException ex) {
            System.out.println("Ошибка добавления");
            System.out.println(ex);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM task WHERE id =" + id)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            System.out.println("User с id: " + id + " удален из базы данных");
        } catch (SQLException ex) {
            System.out.println("Ошибка удаления");
            System.out.println(ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sqlCommand = "SELECT * FROM task";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlCommand);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка импорта");
            System.out.println(ex);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sqlCommand = "DELETE FROM task";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица очищена");
        } catch (SQLException ex) {
            System.out.println("Ошибка очистки таблицы");
            System.out.println(ex);
        }
    }
}