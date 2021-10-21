package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    final String create = "create TABLE if not exists users " +
            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50) NOT NULL, " +
            "lastName VARCHAR(50) NOT NULL, " +
            "age TINYINT NOT NULL)";
    final String drop = "drop table if exists users";
    private Transaction transaction = null;
    private Session session = null;
    private List<User> list = null;
    public UserDaoHibernateImpl() {

    }

    private void createDropQuery(String sql){
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (RuntimeException ignore) {
            if (transaction != null){
                try {
                    transaction.rollback();
                } catch (RuntimeException ignored) {

                }
            }
        } finally {
            if (session != null){
                try {
                    session.close();
                } catch (RuntimeException ignored) {

                }
            }
        }
    }

    @Override
    public void createUsersTable() {
        createDropQuery(create);
    }

    @Override
    public void dropUsersTable() {
        createDropQuery(drop);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (RuntimeException ignore) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException ignored) {

                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ignored) {

                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (RuntimeException ignore) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException ignored) {

                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ignored) {

                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            list = session.createQuery("FROM User", User.class).getResultList();
            transaction.commit();
        } catch (RuntimeException ignore) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException ignored) {

                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ignored) {

                }
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (HibernateException ignore) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException ignored) {

                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ignored) {

                }
            }
        }
    }
}
