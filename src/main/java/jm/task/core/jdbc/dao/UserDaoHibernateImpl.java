package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserDaoHibernateImpl implements UserDao {

    private final Session session = Util.getSessionFactory().openSession();
    final String sqlQuery = "CREATE TABLE IF NOT EXISTS users (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NULL,`lastName` VARCHAR(45) NULL,`age` VARCHAR(45) NULL, PRIMARY KEY (`id`), UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try {
            session.beginTransaction();
            session.createSQLQuery(sqlQuery).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        String hql = "DELETE User WHERE id = :id";
        try {
            session.beginTransaction();
            //session.delete(new User(id));
            session.createQuery(hql);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        try {
            users = session.createQuery("FROM User").list();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String hql = "delete User";

        try {
            session.beginTransaction();
            session.createQuery(hql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }
}
