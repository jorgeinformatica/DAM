package Beans;

import BeansFX.BaseFX;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class Direccion implements java.io.Serializable, BaseBean {

    private Short codDirec;
    private CiudadConcp ciudadConcp;
    private String nombre;
    private Short numero;
    private Set empleados = new HashSet(0);
    private Set locals = new HashSet(0);

    public Direccion() {
    }

    public Direccion(CiudadConcp ciudadConcp, String nombre, short numero) {
        this.ciudadConcp = ciudadConcp;
        this.nombre = nombre;
        this.numero = numero;
    }

    public Direccion(CiudadConcp ciudadConcp, String nombre, short numero, Set empleados, Set locals) {
        this.ciudadConcp = ciudadConcp;
        this.nombre = nombre;
        this.numero = numero;
        this.empleados = empleados;
        this.locals = locals;
    }

    public Short getCodDirec() {
        return this.codDirec;
    }

    public void setCodDirec(Short codDirec) {
        this.codDirec = codDirec;
    }

    public CiudadConcp getCiudadConcp() {
        return this.ciudadConcp;
    }

    public void setCiudadConcp(CiudadConcp ciudadConcp) {
        this.ciudadConcp = ciudadConcp;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getNumero() {
        return this.numero;
    }

    public void setNumero(short numero) {
        this.numero = numero;
    }

    public Set getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(Set empleados) {
        this.empleados = empleados;
    }

    public Set getLocals() {
        return this.locals;
    }

    public void setLocals(Set locals) {
        this.locals = locals;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return nombre + ", " + numero;
    }

}//fin de clase
