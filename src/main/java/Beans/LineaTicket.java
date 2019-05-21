package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaTicket implements java.io.Serializable, BaseBean {

    private Long numLinTicket;
    private Producto producto;
    private Ticket ticket;
    private short cantidad;

    public LineaTicket() {
    }

    public LineaTicket(Long id, Producto producto, Ticket ticket, short cantidad) {
        this.numLinTicket = id;
        this.producto = producto;
        this.ticket = ticket;
        this.cantidad = cantidad;
    }

    public Long getNumLinTicket() {
        return this.numLinTicket;
    }

    public void setNumLinTicket(Long id) {
        this.numLinTicket = id;
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
        return " Linea: " + numLinTicket + " producto: " + producto;
    }

}//fin de clase
