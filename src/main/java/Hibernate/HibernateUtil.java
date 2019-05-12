package Hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * @author Jorge Sempere
 */
public class HibernateUtil {

    private static SessionFactory SESSIONFACTORY;

    public static SessionFactory getSessionFactory() {
        if (SESSIONFACTORY == null) {
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SESSIONFACTORY = configuration.buildSessionFactory(serviceRegistry);
        }
        return SESSIONFACTORY;
    }
}//fin de la clase
