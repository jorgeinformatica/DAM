package Beans;

import BeansFX.BaseFX;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class CodigoPostal implements java.io.Serializable, BaseBean {

    private String codPostal;
    private Set ciudadConcps = new HashSet(0);

    public CodigoPostal() {
    }

    public CodigoPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public CodigoPostal(String codPostal, Set ciudadConcps) {
        this.codPostal = codPostal;
        this.ciudadConcps = ciudadConcps;
    }

    public String getCodPostal() {
        return this.codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public Set getCiudadConcps() {
        return this.ciudadConcps;
    }

    public void setCiudadConcps(Set ciudadConcps) {
        this.ciudadConcps = ciudadConcps;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return codPostal;
    }

}//fin de clase
