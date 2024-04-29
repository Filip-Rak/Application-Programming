package org.example.pau3;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.pau3.external.ClassEmployee;
import org.example.pau3.external.Employee;
import org.example.pau3.external.Rate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.FileWriter;
import java.io.IOException;
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

    private static void saveToCSV()
    {
        DatabaseController.exportToCSV(ClassEmployee.class, "output/classEmployee.csv");
        DatabaseController.exportToCSV(Employee.class, "output/Employee.csv");
        DatabaseController.exportToCSV(Rate.class, "output/Rate.csv");
    }

    public static List<ClassEmployee> loadDB()
    {
        try (Session session = DatabaseController.getSessionFactory().openSession())
        {
            // HQL query
            String hql = "FROM ClassEmployee";
            Query<ClassEmployee> query = session.createQuery(hql, ClassEmployee.class);

            // Save data to a csv
            saveToCSV();

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

    public static <T> void exportToCSV(Class<T> entityClass, String filename)
    {
        try (Session session = sessionFactory.openSession(); FileWriter writer = new FileWriter(filename))
        {
            // Stworzenie zapytania CriteriaQuery dla klasy encji
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);

            // Wykonanie zapytania
            Query<T> query = session.createQuery(cq);
            List<T> results = query.getResultList();

            // Nagłóweki CSV
            String header = String.join(", ", DatabaseReflection.getFieldNames(entityClass)) + "\n";
            writer.append(header);

            // Zapisywanie danych do pliku CSV
            for (T result : results)
            {
                writer.append(DatabaseReflection.convertToCsvString(result));
                writer.append("\n");
            }

        }
        catch (IOException e) { System.out.println("Error writing CSV file: " + e.getMessage()); }
    }

    public static void shutdown()
    {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}