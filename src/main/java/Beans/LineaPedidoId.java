package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaPedidoId implements java.io.Serializable, BaseBean {

    private short numLinPed;
    private int numPed;

    public LineaPedidoId() {
    }

    public LineaPedidoId(short numLinPed, int numPed) {
        this.numLinPed = numLinPed;
        this.numPed = numPed;
    }

    public short getNumLinPed() {
        return this.numLinPed;
    }

    public void setNumLinPed(short numLinPed) {
        this.numLinPed = numLinPed;
    }

    public int getNumPed() {
        return this.numPed;
    }

    public void setNumPed(int numPed) {
        this.numPed = numPed;
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof LineaPedidoId)) {
            return false;
        }
        LineaPedidoId castOther = (LineaPedidoId) other;

        return (this.getNumLinPed() == castOther.getNumLinPed())
                && (this.getNumPed() == castOther.getNumPed());
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getNumLinPed();
        result = 37 * result + this.getNumPed();
        return result;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return "Pedido: " + numPed + " linea: " + numLinPed;
    }

}//fin de clase
