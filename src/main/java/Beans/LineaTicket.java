package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaTicket implements java.io.Serializable, BaseBean {

    private LineaTicketId id;
    private Producto producto;
    private Ticket ticket;
    private short cantidad;

    public LineaTicket() {
    }

    public LineaTicket(LineaTicketId id, Producto producto, Ticket ticket, short cantidad) {
        this.id = id;
        this.producto = producto;
        this.ticket = ticket;
        this.cantidad = cantidad;
    }

    public LineaTicketId getId() {
        return this.id;
    }

    public void setId(LineaTicketId id) {
        this.id = id;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public short getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(short cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return " Linea: " + id.getNumLinTicket() + " producto: " + producto;
    }

}//fin de clase
