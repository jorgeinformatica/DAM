package BeansFX;

import Beans.Ciudad;
import Beans.CiudadConcp;
import Beans.CiudadConcpId;
import Beans.CodigoPostal;
import Beans.Direccion;
import Beans.Provincia;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;

/**
 * @author Jorge Sempere Jimenez
 */
public class CiudadConcpFX extends BaseFX {

    private ObjectProperty<CiudadConcpId> id;
    private ObjectProperty<Ciudad> ciudad;
    private ObjectProperty<CodigoPostal> codigoPostal;
    private ObjectProperty<Provincia> provincia;
    private SetProperty<Direccion> direcciones;

    public CiudadConcpFX() {
        this.beanFX = new SimpleObjectProperty<>(this);
        this.id = new SimpleObjectProperty<>();
        this.ciudad = new SimpleObjectProperty<>();
        this.codigoPostal = new SimpleObjectProperty<>();
        this.provincia = new SimpleObjectProperty<>();
        this.direcciones = new SimpleSetProperty<>(FXCollections.observableSet());
    }

    public CiudadConcpFX(CiudadConcp cccp) {
        this.beanFX = new SimpleObjectProperty<>(this);
        this.id = new SimpleObjectProperty<>(cccp.getId());
        this.ciudad = new SimpleObjectProperty<>(cccp.getCiudad());
        this.codigoPostal = new SimpleObjectProperty<>(cccp.getCodigoPostal());
        this.provincia = new SimpleObjectProperty<>(cccp.getProvincia());
        this.direcciones = new SimpleSetProperty<>(FXCollections.observableSet(cccp.getDireccions()));
        this.bean = cccp;
    }

    public ObjectProperty<CiudadConcpId> getId() {
        return id;
    }

    public void setId(ObjectProperty<CiudadConcpId> id) {
        this.id = id;
    }

    public ObjectProperty<CiudadConcpId> idProperty() {
        return id;
    }

    public ObjectProperty<Ciudad> getCiudad() {
        return ciudad;
    }

    public void setCiudad(ObjectProperty<Ciudad> ciudad) {
        this.ciudad = ciudad;
    }

    public ObjectProperty<Ciudad> ciudadProperty() {
        return ciudad;
    }

    public ObjectProperty<CodigoPostal> getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(ObjectProperty<CodigoPostal> codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public ObjectProperty<CodigoPostal> codigoPostalProperty() {
        return codigoPostal;
    }

    public ObjectProperty<Provincia> getProvincia() {
        return provincia;
    }

    public void setProvincia(ObjectProperty<Provincia> provincia) {
        this.provincia = provincia;
    }

    public ObjectProperty<Provincia> provinciaProperty() {
        return provincia;
    }

    public SetProperty<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(SetProperty<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public SetProperty<Direccion> direccionesProperty() {
        return direcciones;
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
        return getCiudad() + ", " + getCodigoPostal() + ", " + getProvincia();
    }

}//fin de clase
