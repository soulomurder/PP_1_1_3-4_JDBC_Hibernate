package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("create TABLE IF NOT EXISTS  users (id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(15),lastName VARCHAR(15),age TINYINT);").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP table if exists users").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete from User where id =" + id).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().getCurrentSession()){
            session.beginTransaction();
            List<User> list = session.createQuery("from User ", User.class).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().getCurrentSession()){
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
