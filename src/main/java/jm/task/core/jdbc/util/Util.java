package jm.task.core.jdbc.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/MyDB?useSLL=false";
    private static final Connection connection;

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try /*(InputStream inputStream = Files.newInputStream(Paths.get("src/main/database.properties")))*/{

                /*Properties properties = new Properties();
                properties.load(inputStream);*/

                /*Configuration configuration = new Configuration();
                configuration.setProperty(Environment.URL, URL);
                configuration.setProperty(Environment.USER, "root");
                configuration.setProperty(Environment.PASS, "Rfgbnjirf1488");
                configuration.setProperty(Environment.SHOW_SQL, "true");
                configuration.setProperty("hibernate.current_session_context_class", "thread");
                configuration.setProperty("hibernate.connection.release_mode", "auto");
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                return configuration.buildSessionFactory(serviceRegistry);*/
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "Rfgbnjirf1488");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                configuration.setProperties(settings);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                e.getStackTrace();
                System.out.println(e.getMessage());
                throw new RuntimeException();
            }
        } return sessionFactory;
    }

    static {
        try {
            connection = DriverManager.getConnection(URL, "root", "Rfgbnjirf1488");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
