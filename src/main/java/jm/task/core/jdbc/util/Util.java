package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {// 4. в каком запросе?
    private static Connection connection;
    private static SessionFactory sessionFactory;
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myscheme", "root", "GlowStone:89");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "GlowStone:89");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/myscheme");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(Environment.DEFAULT_SCHEMA, "myscheme");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            Configuration configuration = new Configuration();
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }
}
