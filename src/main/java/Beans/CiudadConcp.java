package Beans;

import BeansFX.BaseFX;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class CiudadConcp implements java.io.Serializable, BaseBean {

    private CiudadConcpId id;
    private Ciudad ciudad;
    private CodigoPostal codigoPostal;
    private Provincia provincia;
    private Set direccions = new HashSet(0);

    public CiudadConcp() {
    }

    public CiudadConcp(CiudadConcpId id, Ciudad ciudad, CodigoPostal codigoPostal, Provincia provincia) {
        this.id = id;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
    }

    public CiudadConcp(CiudadConcpId id, Ciudad ciudad, CodigoPostal codigoPostal, Provincia provincia, Set direccions) {
        this.id = id;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
        this.direccions = direccions;
    }

    public CiudadConcpId getId() {
        return this.id;
    }

    public void setId(CiudadConcpId id) {
        this.id = id;
    }

    public Ciudad getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public CodigoPostal getCodigoPostal() {
        return this.codigoPostal;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Provincia getProvincia() {
        return this.provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Set getDireccions() {
        return this.direccions;
    }

    public void setDireccions(Set direccions) {
        this.direccions = direccions;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return ciudad + ", " + codigoPostal + "," + provincia;
    }

}//fin de clase
