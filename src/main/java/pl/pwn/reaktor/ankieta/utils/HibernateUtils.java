package pl.pwn.reaktor.ankieta.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {

    private HibernateUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final SessionFactory sessionFactory;

    static {

        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.err.println("Session Factory could not be created.\n" + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}




