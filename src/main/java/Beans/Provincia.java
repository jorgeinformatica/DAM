package Beans;

import BeansFX.BaseFX;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class Provincia implements java.io.Serializable, BaseBean {

    private short codProv;
    private String nombre;
    private Set ciudadConcps = new HashSet(0);

    public Provincia() {
    }

    public Provincia(short codProv, String nombre) {
        this.codProv = codProv;
        this.nombre = nombre;
    }

    public Provincia(short codProv, String nombre, Set ciudadConcps) {
        this.codProv = codProv;
        this.nombre = nombre;
        this.ciudadConcps = ciudadConcps;
    }

    public short getCodProv() {
        return this.codProv;
    }

    public void setCodProv(short codProv) {
        this.codProv = codProv;
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
