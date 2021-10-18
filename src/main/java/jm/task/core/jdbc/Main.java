package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        List<User> users = new ArrayList<>();
        users.add(new User("Sergey", "Alexandrov", (byte) 24));
        users.add(new User("Alexandr", "Sergeev", (byte) 42));
        users.add(new User("Vasiliy", "Ivanov", (byte) 88));
        users.add(new User("Ivan", "Vasilevich", (byte) 10));
        userDaoJDBC.createUsersTable();
        userDaoJDBC.createUsersTable();
        users.forEach(user -> {
            userDaoJDBC.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем – %s добавлен в базу данных\n" , user.getName());
        });
        users = userDaoJDBC.getAllUsers();
        users.forEach(System.out::println);
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
