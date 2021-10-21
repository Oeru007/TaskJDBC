package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        List<User> users = new ArrayList<>();
        users.add(new User("Sergey", "Alexandrov", (byte) 24));
        users.add(new User("Alexandr", "Sergeev", (byte) 42));
        users.add(new User("Vasiliy", "Ivanov", (byte) 88));
        users.add(new User("Ivan", "Vasilevich", (byte) 10));
        userService.createUsersTable();
        users.forEach(user -> {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем – %s добавлен в базу данных\n" , user.getName());
        });
        users = userService.getAllUsers();
        users.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
