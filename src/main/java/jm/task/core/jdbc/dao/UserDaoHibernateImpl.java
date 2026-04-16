package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createNativeQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), " +
                    "lastName VARCHAR(50), age TINYINT)");
            query.executeUpdate();
            session.getTransaction().commit();

            System.out.println("Таблица создана");
        } catch (Exception ex) {
            System.out.println("Ошибка создания");
            System.out.println(ex);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createNativeQuery("DROP TABLE IF EXISTS users");
            query.executeUpdate();
            session.getTransaction().commit();

            System.out.println("Таблица удалена");
        } catch (Exception ex) {
            System.out.println("Ошибка удаления");
            System.out.println(ex);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();

        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception ex) {
            System.out.println("Ошибка добавления");
            System.out.println(ex);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();

            System.out.println("User с id: " + id + " удален из базы данных");
        } catch (Exception ex) {
            System.out.println("Ошибка удаления");
            System.out.println(ex);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).getResultList();

            for (User user : users)
                System.out.println(user);

            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            session.getTransaction().commit();

            System.out.println("Таблица очищена");
        } catch (Exception ex) {
            System.out.println("Ошибка очистки таблицы");
            System.out.println(ex);
        }
    }
}