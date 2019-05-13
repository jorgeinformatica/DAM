package BeansFX;

import Beans.CiudadConcp;
import Beans.CiudadConcpId;
import Beans.CodigoPostal;
import Beans.Direccion;
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
    private ObjectProperty<CiudadFX> ciudad;
    private ObjectProperty<CodigoPostalFX> codigoPostal;
    private ObjectProperty<ProvinciaFX> provincia;
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
        this.ciudad = new SimpleObjectProperty<>(new CiudadFX(cccp.getCiudad()));
        this.codigoPostal = new SimpleObjectProperty<>(new CodigoPostalFX(cccp.getCodigoPostal()));
        this.provincia = new SimpleObjectProperty<>(new ProvinciaFX(cccp.getProvincia()));
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

    public CiudadFX getCiudad() {
        return ciudad.get();
    }

    public void setCiudad(CiudadFX ciudad) {
        this.ciudad.set(ciudad);
    }

    public ObjectProperty<CiudadFX> ciudadProperty() {
        return ciudad;
    }

    public CodigoPostalFX getCodigoPostal() {
        return codigoPostal.get();
    }

    public void setCodigoPostal(CodigoPostalFX codigoPostal) {
        this.codigoPostal.set(codigoPostal);
    }

    public ObjectProperty<CodigoPostalFX> codigoPostalProperty() {
        return codigoPostal;
    }

    public ProvinciaFX getProvincia() {
        return provincia.get();
    }

    public void setProvincia(ProvinciaFX provincia) {
        this.provincia.set(provincia);
    }

    public ObjectProperty<ProvinciaFX> provinciaProperty() {
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
