package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.createSQLQuery("CREATE TABLE `users` (\n" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(40) NOT NULL,\n" +
                    "  `second_name` varchar(40) NOT NULL,\n" +
                    "  `age` int NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.createSQLQuery(query);
        } catch (HibernateException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        User user = new User();

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            user.setId(id);
            session.delete(user);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.getStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users;";
        List<User> userList = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            Query query = session.createSQLQuery(sql);

            List<Object[]> temp = query.list();
            for (Object[] o :
                    temp) {
                userList.add(new User((long) o[0], o[1].toString(), o[2].toString(), (byte) o[3]));
            }

        } catch (HibernateException e) {
            e.getStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users;";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.createSQLQuery(sql);
        } catch (HibernateException e) {
            e.getStackTrace();
        }
    }
}
