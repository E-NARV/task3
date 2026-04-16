package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
        //Создаем таблицу
        userDao.createUsersTable();

        //Вносим в таблицу 4-ёх юзеров
        userDao.saveUser("Alex", "Ivanov", (byte) 31);
        userDao.saveUser("Bob", "Sponge", (byte) 32);
        userDao.saveUser("Jackie", "Chan", (byte) 27);
        userDao.saveUser("Post", "Malone", (byte) 26);

        //Получаем список всех юзеров
        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }

        //Очищаем таблицу
        userDao.cleanUsersTable();

        //Удаялем таблицу
        userDao.dropUsersTable();
    }
}