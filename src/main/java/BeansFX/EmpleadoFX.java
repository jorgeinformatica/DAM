package BeansFX;

import Beans.Direccion;
import Beans.Empleado;
import Beans.Local;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class EmpleadoFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty dni;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Direccion> direccion;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Local> local;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty nombre;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty ape1;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty ape2;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty telefono;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty email;
    @SuppressWarnings("FieldMayBeFinal")
    private BooleanProperty estado;

    public EmpleadoFX() {
        this.dni = new SimpleStringProperty();
        this.direccion = new SimpleObjectProperty<>();
        this.local = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.ape1 = new SimpleStringProperty();
        this.ape2 = new SimpleStringProperty();
        this.telefono = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.estado = new SimpleBooleanProperty();
        this.beanFX = new SimpleObjectProperty<>(this);
    }

    public EmpleadoFX(Empleado empleado) {
        this.dni = new SimpleStringProperty(empleado.getDni());
        this.direccion = new SimpleObjectProperty<>(empleado.getDireccion());
        this.local = new SimpleObjectProperty<>(empleado.getLocal());
        this.nombre = new SimpleStringProperty(empleado.getNombre());
        this.ape1 = new SimpleStringProperty(empleado.getApe1());
        this.ape2 = new SimpleStringProperty(empleado.getApe2());
        this.telefono = new SimpleStringProperty(empleado.getTelefono());
        this.email = new SimpleStringProperty(empleado.getEmail());
        this.estado = new SimpleBooleanProperty(empleado.getEstado());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean = empleado;
    }

    public String getDni() {
        return dni.get();
    }

    public void setDni(String dni) {
        this.dni.set(dni);
    }

    public StringProperty dniProperty() {
        return dni;
    }

    public Direccion getDireccion() {
        return direccion.get();
    }

    public void setDireccion(Direccion direccion) {
        this.direccion.set(direccion);
    }

    public ObjectProperty<Direccion> direccionProperty() {
        return direccion;
    }

    public Local getLocal() {
        return local.get();
    }

    public void setLocal(Local local) {
        this.local.set(local);
    }

    public ObjectProperty<Local> localProperty() {
        return local;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getApe1() {
        return ape1.get();
    }

    public void setApe1(String ape1) {
        this.ape1.set(ape1);
    }

    public StringProperty ape1Property() {
        return ape1;
    }

    public String getApe2() {
        return ape2.get();
    }

    public void setApe2(String ape2) {
        this.ape2.set(ape2);
    }

    public StringProperty ape2Property() {
        return ape2;
    }

    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public Boolean getEstado() {
        return estado.get();
    }

    public void setEstado(Boolean estado) {
        this.estado.set(estado);
    }

    public BooleanProperty estadoProperty() {
        return estado;
    }

    @Override
    public boolean comprobarCambios() {
        return false;
    }

    @Override
    public void sinCambios() {

    }

    @Override
    public String toString() {
        return getApe1() + " " + getApe2() + ", " + getNombre();
    }

}//fin de clase
