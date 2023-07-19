package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection;
    private static SessionFactory sessionFactory;
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myscheme", "root", "GlowStone:89");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure("/hibernate.cfg.xml");
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
