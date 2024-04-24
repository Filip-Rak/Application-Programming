package org.example.pau3;
import org.example.pau3.external.ClassEmployee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DatabaseController
{
    // Attributes
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // Methods
    private static SessionFactory buildSessionFactory()
    {
        try
        {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        catch (Throwable ex)
        {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Public Methods
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public static List<ClassEmployee> loadDB()
    {
        try (Session session = DatabaseController.getSessionFactory().openSession())
        {
            // HQL query
            String hql = "FROM ClassEmployee";
            Query<ClassEmployee> query = session.createQuery(hql, ClassEmployee.class);
            return query.list();
        } catch (Exception e)
        {
            System.out.println("Couldn't create a session. " + e.getMessage());
            return null;
        }
    }

    public static void updateDB(ClassEmployee ce)
    {
        Session session = DatabaseController.getSessionFactory().openSession();
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            session.update(ce);
            transaction.commit();
        }
        catch (RuntimeException ignore) { if (transaction != null) transaction.rollback(); }
        finally  { session.close(); }
    }

    public static void removeDB(ClassEmployee data)
    {
        Session session = DatabaseController.getSessionFactory().openSession();
        Transaction transaction = null;

        try
        {
            transaction = session.beginTransaction();
            session.remove(data);
            transaction.commit();
        }
        catch (RuntimeException ignore) { if (transaction != null) transaction.rollback();}
        finally { session.close(); }
    }

    public static void addData(ClassEmployee data)
    {
        Session session = DatabaseController.getSessionFactory().openSession();
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            session.persist(data);
            transaction.commit();
        }
        catch (RuntimeException ignore) { if (transaction != null) transaction.rollback(); }
        finally { session.close(); }
    }

    public static void shutdown()
    {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}