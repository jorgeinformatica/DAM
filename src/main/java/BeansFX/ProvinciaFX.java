package BeansFX;

import Beans.CiudadConcp;
import Beans.Provincia;
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
public class ProvinciaFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> codProvincia;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty nombre;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<CiudadConcp> ciudades;

    public ProvinciaFX() {
        this.beanFX = new SimpleObjectProperty<>(this);
        this.ciudades = new SimpleSetProperty<>(FXCollections.observableSet());
        this.nombre = new SimpleStringProperty();
        this.codProvincia = new SimpleObjectProperty<>();
    }

    public ProvinciaFX(Provincia provincia) {
        this.beanFX = new SimpleObjectProperty<>(this);
        this.ciudades = new SimpleSetProperty<>(FXCollections.observableSet(provincia.getCiudadConcps()));
        this.codProvincia = new SimpleObjectProperty<>(provincia.getCodProv());
        this.nombre = new SimpleStringProperty(provincia.getNombre());
        this.bean = provincia;
    }

    public Short getCodProvincia() {
        return codProvincia.get();
    }

    public void setCodProvincia(Short codProvincia) {
        this.codProvincia.set(codProvincia);
    }

    public ObjectProperty<Short> codProvinciaProperty() {
        return codProvincia;
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

    public Set getCiudades() {
        return ciudades.get();
    }

    public void setCiudades(Set<CiudadConcp> ciudades) {
        this.ciudades.clear();
        ciudades.forEach((ciu) -> {
            this.ciudades.add(ciu);
        });

    }

    public SetProperty<CiudadConcp> ciudadesProperty() {
        return ciudades;
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

}//fin de clase
