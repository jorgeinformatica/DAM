package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere Jimenez
 */
public class StockId implements java.io.Serializable, BaseBean {

    private short codLocal;
    private short codProd;

    public StockId() {
    }

    public StockId(short codLocal, short codProd) {
        this.codLocal = codLocal;
        this.codProd = codProd;
    }

    public short getCodLocal() {
        return this.codLocal;
    }

    public void setCodLocal(short codLocal) {
        this.codLocal = codLocal;
    }

    public short getCodProd() {
        return this.codProd;
    }

    public void setCodProd(short codProd) {
        this.codProd = codProd;
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof StockId)) {
            return false;
        }
        StockId castOther = (StockId) other;

        return (this.getCodLocal() == castOther.getCodLocal())
                && (this.getCodProd() == castOther.getCodProd());
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getCodLocal();
        result = 37 * result + this.getCodProd();
        return result;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return "Local: " + codLocal + " Producto: " + codProd;
    }

}//fin de clase
