package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere Jimenez
 */
public class CiudadConcpId implements java.io.Serializable, BaseBean {

    private short codCiudad;
    private String codPostal;

    public CiudadConcpId() {
    }

    public CiudadConcpId(short codCiudad, String codPostal) {
        this.codCiudad = codCiudad;
        this.codPostal = codPostal;
    }

    public short getCodCiudad() {
        return this.codCiudad;
    }

    public void setCodCiudad(short codCiudad) {
        this.codCiudad = codCiudad;
    }

    public String getCodPostal() {
        return this.codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof CiudadConcpId)) {
            return false;
        }
        CiudadConcpId castOther = (CiudadConcpId) other;

        return (this.getCodCiudad() == castOther.getCodCiudad())
                && ((this.getCodPostal() == null ? castOther.getCodPostal() == null : this.getCodPostal().equals(castOther.getCodPostal())) || (this.getCodPostal() != null && castOther.getCodPostal() != null && this.getCodPostal().equals(castOther.getCodPostal())));
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getCodCiudad();
        result = 37 * result + (getCodPostal() == null ? 0 : this.getCodPostal().hashCode());
        return result;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
         return "Ciudad: " + codCiudad + ", CP: " + codPostal;
    }

}//fin de clase
