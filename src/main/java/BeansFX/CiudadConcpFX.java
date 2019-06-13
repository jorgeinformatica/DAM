package BeansFX;

import Beans.Ciudad;
import Beans.CiudadConcp;
import Beans.CodigoPostal;
import Beans.Direccion;
import Beans.Provincia;
import dam.proyecto.LogicController;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;

/**
 * @author Jorge Sempere Jimenez
 */
public class CiudadConcpFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<CiudadConcpIdFX> id;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<CiudadFX> ciudad;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<CodigoPostalFX> codigoPostal;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<ProvinciaFX> provincia;
    private SetProperty<Direccion> direcciones;

    public CiudadConcpFX() {
        this.id = new SimpleObjectProperty<>();
        this.ciudad = new SimpleObjectProperty<>();
        this.codigoPostal = new SimpleObjectProperty<>();
        this.provincia = new SimpleObjectProperty<>();
        this.direcciones = new SimpleSetProperty<>(FXCollections.observableSet());
    }

    public CiudadConcpFX(CiudadConcp cccp) {
        this.id = new SimpleObjectProperty<>(new CiudadConcpIdFX(cccp.getId()));
        this.ciudad = new SimpleObjectProperty<>(new CiudadFX(cccp.getCiudad()));
        this.codigoPostal = new SimpleObjectProperty<>(new CodigoPostalFX(cccp.getCodigoPostal()));
        this.provincia = new SimpleObjectProperty<>(new ProvinciaFX(cccp.getProvincia()));
        this.direcciones = new SimpleSetProperty<>(FXCollections.observableSet(cccp.getDireccions()));
        this.bean = cccp;
    }

    public CiudadConcpIdFX getId() {
        return id.get();
    }

    public void setId(CiudadConcpIdFX id) {
        this.id.set(id);
    }

    public ObjectProperty<CiudadConcpIdFX> idProperty() {
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
        if(!Objects.equals(getCiudad().getCodCiudad(), ((CiudadConcp)getBean()).getCiudad().getCodCiudad())){
            return true;
        }
        if(!getCodigoPostal().getCodPostal().equalsIgnoreCase(((CiudadConcp)getBean()).getCodigoPostal().getCodPostal())){
            return true;
        }
        return getProvincia().getCodProvincia()!=((CiudadConcp)getBean()).getProvincia().getCodProv();
    }

    @Override
    public void sinCambios() {
        setCiudad(LogicController.getCiuFX((Ciudad) getCiudad().getBean()));
        setCodigoPostal(LogicController.getCPFX((CodigoPostal) getCodigoPostal().getBean()));
        setProvincia(LogicController.getProvFX((Provincia) getProvincia().getBean()));
    }

    @Override
    public String toString() {
        return getCiudad() + ", " + getCodigoPostal() + ", " + getProvincia();
    }

}//fin de clase
