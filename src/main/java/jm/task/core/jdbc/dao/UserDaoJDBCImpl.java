package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement stnt = Util.getConnection().createStatement()) {
            stnt.execute("CREATE TABLE `users` (\n" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(40) NOT NULL,\n" +
                    "  `second_name` varchar(40) NOT NULL,\n" +
                    "  `age` int NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            throw new RuntimeException();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Statement stnt = Util.getConnection().createStatement()) {
            stnt.execute(query);
        } catch (SQLException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement("INSERT INTO users (name, second_name, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pstnt = Util.getConnection().prepareStatement("DELETE FROM users WHERE Id = ?")) {
            pstnt.setLong(1, id);
            pstnt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM users;";
        List<User> userList = new ArrayList<>();
        try (Statement stnt = Util.getConnection().createStatement()) {
            ResultSet resultSet = stnt.executeQuery(query);
            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("second_name"),
                        resultSet.getByte("age")
                ));
            }
        } catch (SQLException e) {
            e.getStackTrace();
            throw new RuntimeException();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement stnt = Util.getConnection().createStatement()) {
            stnt.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.getStackTrace();
            throw new RuntimeException();
        }
    }
}
