package BeansFX;

import Beans.CiudadConcpId;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class CiudadConcpIdFX extends BaseFX {

    private ObjectProperty<Short> codCiudad;
    private StringProperty codPostal;

    public CiudadConcpIdFX() {
        this.beanFX = new SimpleObjectProperty<>(this);
        this.codCiudad = new SimpleObjectProperty<>();
        this.codPostal = new SimpleStringProperty();
    }

    public CiudadConcpIdFX(CiudadConcpId cccpId) {
        this.beanFX = new SimpleObjectProperty<>(this);
        this.codCiudad = new SimpleObjectProperty<>(cccpId.getCodCiudad());
        this.codPostal = new SimpleStringProperty(cccpId.getCodPostal());
        this.bean = cccpId;
    }

    public Short getCodCiudad() {
        return codCiudad.get();
    }

    public void setCodCiudad(Short codCiudad) {
        this.codCiudad.set(codCiudad);
    }

    public ObjectProperty<Short> codCiudadProperty() {
        return codCiudad;
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

    @Override
    public boolean comprobarCambios() {
        return false;
    }

    @Override
    public void sinCambios() {

    }

    @Override
    public String toString() {
        return "Ciudad: " + getCodCiudad() + ", CP: " + getCodPostal();
    }

}//fin de clase
