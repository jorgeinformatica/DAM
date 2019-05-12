package Hibernate;

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
    public void initTransaction() {
        session.beginTransaction();
    }

    /**
     * Lanza los cambios realizados a la BD
     */
    public void goCommit() {
        session.getTransaction().commit();
    }

    /**
     * @param elemento: Es la instancia del objeto que se ha de actualizar.
     */
    public void UpdateElement(Object elemento) {
        session.update(elemento);
    }

    /**
     * @param object Es la instancia del objeto que se desea persistir en la BD
     */
    public void save(Object object) {
        session.save(object);
    }

    /**
     * @param object Es la instancia del objeto que se desea eliminar de la BD
     */
    public void remove(Object object) {
        session.delete(object);
    }

    /**
     * @param c La clase que se va ha instanciar
     * @param condition Las condiciones que se han de definir en el where
     * @return Devuelve todos los registros de la BD del tipo recibido como
     * parámetro
     */
    public List<Object> getList(Class c, String condition) {
        return session.createQuery("FROM " + c.getName() + " WHERE " + condition).list();
    }

    /**
     * @param c La clase que se va ha instanciar
     * @param condition Las condiciones que se han de definir en el where
     * @return Un elemento de la BD
     */
    public Object searchElement(Class c, String condition) {
        return session.createQuery("SELECT elemento FROM "
                + c.getName() + " elemento WHERE " + condition).uniqueResult();
    }

}//fin de la clase
