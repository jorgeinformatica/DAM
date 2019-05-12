package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere Jimenez
 */
public class Empleado implements java.io.Serializable, BaseBean {

    private String dni;
    private Direccion direccion;
    private Local local;
    private String nombre;
    private String ape1;
    private String ape2;
    private String telefono;
    private String email;
    private boolean estado;

    public Empleado() {
    }

    public Empleado(String dni, String nombre, String ape1, String ape2, String telefono, String email, boolean estado) {
        this.dni = dni;
        this.nombre = nombre;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.telefono = telefono;
        this.email = email;
        this.estado = estado;
    }

    public Empleado(String dni, Direccion direccion, Local local, String nombre, String ape1, String ape2, String telefono, String email, boolean estado) {
        this.dni = dni;
        this.direccion = direccion;
        this.local = local;
        this.nombre = nombre;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.telefono = telefono;
        this.email = email;
        this.estado = estado;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe1() {
        return this.ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return this.ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return ape1 + " " + ape2 + ", " + nombre;
    }

}//fin de clase
