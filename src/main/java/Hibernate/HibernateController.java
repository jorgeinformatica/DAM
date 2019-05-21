package Hibernate;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;

/**
 * @author Jorge Sempere
 */
public class HibernateController {

    private static Session session;

    public HibernateController() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    /**
     * Inicia la "Transaccion"
     */
    public synchronized void initTransaction() {
        session.beginTransaction();
    }

    /**
     * @param elemento: Es la instancia del objeto que se ha de actualizar.
     */
    public synchronized void UpdateElement(Object elemento) {
        session.update(elemento);
        session.getTransaction().commit();
    }

    /**
     * @param object Es la instancia del objeto que se desea persistir en la BD
     * @return id el id de la instancia almacenada
     */
    public synchronized Serializable save(Object object) {
        Serializable id = session.save(object);
        session.getTransaction().commit();
        return id;
    }

    /**
     * @param object Es la instancia del objeto que se desea actualizar y en el
     * caso de que no exista, persistir.
     */
    public synchronized void saveOrUpdate(Object object) {
        session.saveOrUpdate(this);
        session.getTransaction().commit();
    }

    /**
     * @param object Es la instancia del objeto que contiene algún set que hay
     * que refrescar
     */
    public synchronized void refresco(Object object) {
        session.refresh(object);
    }

    /**
     * @param object Es la instancia del objeto que se desea eliminar de la BD
     */
    public synchronized void remove(Object object) {
        session.delete(object);
        session.getTransaction().commit();
    }

    /**
     * @param c La clase que se va ha instanciar
     * @param condition Las condiciones que se han de definir en el where
     * @return Devuelve todos los registros de la BD del tipo recibido como
     * parámetro
     */
    public synchronized List<Object> getList(Class c, String condition) {
        return session.createQuery("FROM " + c.getName() + " WHERE " + condition).list();
    }

    /**
     * @param c La clase que se va ha instanciar
     * @param condition Las condiciones que se han de definir en el where
     * @return Un elemento de la BD
     */
    public synchronized Object searchElement(Class c, String condition) {
        return session.createQuery("SELECT elemento FROM "
                + c.getName() + " elemento WHERE " + condition).uniqueResult();
    }

}//fin de la clase
