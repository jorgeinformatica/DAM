package BeansFX;

import Beans.CiudadConcp;
import Beans.CodigoPostal;
import java.util.Set;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * @author Jorge Sempere Jimenez
 */
public class CodigoPostalFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty codPostal;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<CiudadConcp> relCpCiu;

    public CodigoPostalFX() {
        this.codPostal = new SimpleStringProperty();
        this.relCpCiu = new SimpleSetProperty<>(FXCollections.observableSet());
    }

    public CodigoPostalFX(CodigoPostal cp) {
        this.codPostal = new SimpleStringProperty(cp.getCodPostal());
        this.relCpCiu = new SimpleSetProperty<>(FXCollections.observableSet(cp.getCiudadConcps()));
        this.bean = cp;
    }

    public String getCodPostal() {
        return codPostal.getValueSafe();
    }

    public void setCodPostal(String codPostal) {
        this.codPostal.set(codPostal);
    }

    public StringProperty codPostalProperty() {
        return codPostal;
    }

    public Set getDirecciones() {
        return relCpCiu.get();
    }

    public void setDirecciones(Set direcciones) {
        this.relCpCiu.clear();
        this.relCpCiu.addAll(direcciones);
    }

    public SetProperty<CiudadConcp> relCpCiuProperty() {
        return relCpCiu;
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
        return getCodPostal();
    }

}//fin de clase
