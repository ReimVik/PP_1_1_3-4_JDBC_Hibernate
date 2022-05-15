package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Pavel", "Ivanov", (byte) 21);
        userService.saveUser("Nikita", "Petrov", (byte) 25);
        userService.saveUser("Igor", "Vasechkin", (byte) 24);
        userService.saveUser("Kirill", "Popov", (byte) 21);
        /*userService.getAllUsers()
                        .forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();*/

        /*List<User> users = List.of(
                new User("Vasiliy", "Ivanov", (byte) 14),
                new User("Kirill", "Popov", (byte) 17),
                new User("Pavel", "Kotnev", (byte) 19),
                new User("Viktor", "Kuchnev", (byte) 21)
        );*/

        /*users.forEach(user -> {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println(user + "\n was added");
        });*/
    }
}
