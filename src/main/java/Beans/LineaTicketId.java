package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaTicketId implements java.io.Serializable, BaseBean {

    private short numLinTicket;
    private int numTicket;

    public LineaTicketId() {
    }

    public LineaTicketId(short numLinTicket, int numTicket) {
        this.numLinTicket = numLinTicket;
        this.numTicket = numTicket;
    }

    public short getNumLinTicket() {
        return this.numLinTicket;
    }

    public void setNumLinTicket(short numLinTicket) {
        this.numLinTicket = numLinTicket;
    }

    public int getNumTicket() {
        return this.numTicket;
    }

    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof LineaTicketId)) {
            return false;
        }
        LineaTicketId castOther = (LineaTicketId) other;

        return (this.getNumLinTicket() == castOther.getNumLinTicket())
                && (this.getNumTicket() == castOther.getNumTicket());
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getNumLinTicket();
        result = 37 * result + this.getNumTicket();
        return result;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return "Ticket: " + numTicket + " linea: " + numLinTicket;
    }

}//fin de clase
