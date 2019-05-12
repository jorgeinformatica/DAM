package Beans;

import BeansFX.BaseFX;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class Ciudad implements java.io.Serializable, BaseBean {

    private Short codCiudad;
    private String nombre;
    private Set ciudadConcps = new HashSet(0);

    public Ciudad() {
    }

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public Ciudad(String nombre, Set ciudadConcps) {
        this.nombre = nombre;
        this.ciudadConcps = ciudadConcps;
    }

    public Short getCodCiudad() {
        return this.codCiudad;
    }

    public void setCodCiudad(Short codCiudad) {
        this.codCiudad = codCiudad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return nombre;
    }

}//fin de clase
