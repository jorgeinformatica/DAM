package BeansFX;

import Beans.Direccion;
import Beans.Empleado;
import Beans.Local;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * @author Jorge Sempere Jimenez
 */
public class DireccionFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> codDirec;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<CiudadConcpFX> relCpCiu;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty nombre;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> numero;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<Empleado> empleados;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<Local> locales;

    public DireccionFX() {
        this.codDirec = new SimpleObjectProperty<>();
        this.relCpCiu = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.numero = new SimpleObjectProperty<>();
        this.empleados = new SimpleSetProperty<>(FXCollections.observableSet());
        this.locales = new SimpleSetProperty<>(FXCollections.observableSet());
        this.beanFX = new SimpleObjectProperty<>(this);
    }

    public DireccionFX(Direccion direccion) {
        this.codDirec = new SimpleObjectProperty<>(direccion.getCodDirec());
        this.relCpCiu = new SimpleObjectProperty<>(new CiudadConcpFX(direccion.getCiudadConcp()));
        this.nombre = new SimpleStringProperty(direccion.getNombre());
        this.numero = new SimpleObjectProperty<>(direccion.getNumero());
        this.empleados = new SimpleSetProperty<>(FXCollections.observableSet());
        this.locales = new SimpleSetProperty<>(FXCollections.observableSet());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean = direccion;
    }

    public int getCodDirec() {
        return codDirec.get();
    }

    public void setCodDirec(short codDirec) {
        this.codDirec.set(codDirec);
    }

    public ObjectProperty<Short> codDirecProperty() {
        return codDirec;
    }

    public CiudadConcpFX getRelCpCiu() {
        return relCpCiu.get();
    }

    public void setRelCpCiu(CiudadConcpFX relCpCiu) {
        this.relCpCiu.set(relCpCiu);
    }

    public ObjectProperty<CiudadConcpFX> relCpCiuProperty() {
        return relCpCiu;
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

    public Short getNumero() {
        return numero.get();
    }

    public void setNumero(Short numero) {
        this.numero.set(numero);
    }

    public ObjectProperty<Short> numeroProperty() {
        return numero;
    }

    public Set getEmpleados() {
        return empleados.get();
    }

    public void setEmpleados(Set empleados) {
        this.empleados.clear();
        this.empleados.addAll(empleados);
    }

    public SetProperty<Empleado> empleadosProperty() {
        return empleados;
    }

    public Set getLocales() {
        return locales.get();
    }

    public void setLocales(Set locales) {
        this.locales.clear();
        this.locales.addAll(locales);
    }

    public SetProperty<Local> localesProperty() {
        return locales;
    }

    @Override
    public boolean comprobarCambios() {
        if (getRelCpCiu().comprobarCambios()) {
            return true;
        }
        if (!getNombre().equalsIgnoreCase(((Direccion) getBean()).getNombre())) {
            return true;
        }
        return !Objects.equals(getNumero(), ((Direccion) getBean()).getNumero());
    }

    @Override
    public void sinCambios() {
        getRelCpCiu().sinCambios();
        setNombre(((Direccion) getBean()).getNombre());
        setNumero(((Direccion) getBean()).getNumero());
    }

    @Override
    public String toString() {
        return getNombre() + ", " + getNumero();
    }

}//fin de clase
