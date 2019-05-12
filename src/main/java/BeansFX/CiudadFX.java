package BeansFX;

import Beans.Ciudad;
import Beans.CiudadConcp;
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
public class CiudadFX extends BaseFX{
        
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> codCiudad;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty nombre;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<CiudadConcp> ciudadConcp;

    public CiudadFX() {
        this.codCiudad = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.beanFX = new SimpleObjectProperty<>(this);
        this.ciudadConcp = new SimpleSetProperty<>(FXCollections.observableSet());
    }

    public CiudadFX(Ciudad ciudad) {
        this.codCiudad = new SimpleObjectProperty<>(ciudad.getCodCiudad());
        this.nombre = new SimpleStringProperty(ciudad.getNombre());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.ciudadConcp = new SimpleSetProperty<>(FXCollections.observableSet(ciudad.getCiudadConcps()));
        this.bean=ciudad;
    }

    public Short getCodCiudad() {
        return codCiudad.get();
    }

    public void setCodCiudad(short codCiudad) {
        this.codCiudad.set(codCiudad);
    }

    public ObjectProperty<Short> codCiudadProperty() {
        return codCiudad;
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

    public Set getCiudadConcp() {
        return ciudadConcp.get();
    }

    public void setCiudadConcp(Set codigos) {
        this.ciudadConcp.clear();
        this.ciudadConcp.addAll(codigos);
    }

    public SetProperty<CiudadConcp> ciudadConcpProperty() {
        return ciudadConcp;
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
        return getNombre();
    }

}//fin de la clase
