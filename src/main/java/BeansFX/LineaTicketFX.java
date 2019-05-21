package BeansFX;

import Beans.LineaTicket;
import Beans.Producto;
import Beans.Ticket;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaTicketFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Long> id;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Producto> producto;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Ticket> ticket;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> cantidad;

    public LineaTicketFX() {
        this.id = new SimpleObjectProperty<>();
        this.producto = new SimpleObjectProperty<>();
        this.ticket = new SimpleObjectProperty<>();
        this.cantidad = new SimpleObjectProperty<>();
        this.beanFX = new SimpleObjectProperty<>(this);
    }

    public LineaTicketFX(LineaTicket lineaTicket) {
        this.id = new SimpleObjectProperty<>(lineaTicket.getNumLinTicket());
        this.producto = new SimpleObjectProperty<>(lineaTicket.getProducto());
        this.ticket = new SimpleObjectProperty<>(lineaTicket.getTicket());
        this.cantidad = new SimpleObjectProperty<>(lineaTicket.getCantidad());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean = lineaTicket;
    }

    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public ObjectProperty<Long> idProperty() {
        return id;
    }

    public Producto getProducto() {
        return producto.get();
    }

    public void setProducto(Producto producto) {
        this.producto.set(producto);
    }

    public ObjectProperty<Producto> productoProperty() {
        return producto;
    }

    public Ticket getTicket() {
        return ticket.get();
    }

    public void setTicket(Ticket ticket) {
        this.ticket.set(ticket);
    }

    public ObjectProperty<Ticket> ticketProperty() {
        return ticket;
    }

    public Short getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(Short cantidad) {
        this.cantidad.set(cantidad);
    }

    public ObjectProperty<Short> cantidadProperty() {
        return cantidad;
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
        return " Linea: " + getId() + " producto: " + getProducto().toString();
    }

}//fin de clase
