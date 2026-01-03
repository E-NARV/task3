package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();

        String sqlCommand = "CREATE TABLE IF NOT EXISTS task (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age TINYINT)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица создана");
        } catch (SQLException ex) {
            System.out.println("Ошибка создания");
            System.out.println(ex);
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();

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
        Connection connection = Util.getConnection();

        String sqlCommand = "INSERT INTO task (name, lastName, age) VALUES ('" + name + "', '"+ lastName + "', " + age + ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException ex) {
            System.out.println("Ошибка добавления");
            System.out.println(ex);
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();

        String sqlCommand = "DELETE FROM task WHERE id =" + id;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("User с id: " + id + " удален из базы данных");
        } catch (SQLException ex) {
            System.out.println("Ошибка удаления");
            System.out.println(ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Connection connection = Util.getConnection();

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
        Connection connection = Util.getConnection();

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