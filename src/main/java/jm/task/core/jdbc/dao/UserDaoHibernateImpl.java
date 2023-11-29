package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(UserDaoJDBCImpl.CREATE_USERS_TABLE).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(UserDaoJDBCImpl.DROP_USERS_TABLE).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            return users;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
