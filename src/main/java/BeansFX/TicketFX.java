package BeansFX;

import Beans.LineaTicket;
import Beans.Local;
import Beans.Ticket;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * @author Jorge Sempere Jimenez
 */
public class TicketFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Long> numTicket;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Local> local;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Date> fecha;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty tipoTicket;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<BigDecimal> precioTotal;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<LineaTicket> lineaTickets;

    public TicketFX() {
        this.numTicket = new SimpleObjectProperty<>();
        this.local = new SimpleObjectProperty<>();
        this.fecha = new SimpleObjectProperty<>();
        this.tipoTicket = new SimpleStringProperty();
        this.precioTotal = new SimpleObjectProperty<>();
        this.lineaTickets = new SimpleSetProperty<>(FXCollections.observableSet());
    }

    public TicketFX(Ticket ticket) {
        this.numTicket = new SimpleObjectProperty<>(ticket.getNumTicket());
        this.local = new SimpleObjectProperty<>(ticket.getLocal());
        this.fecha = new SimpleObjectProperty<>(ticket.getFecha());
        this.tipoTicket = new SimpleStringProperty(ticket.getTipoTicket());
        this.precioTotal = new SimpleObjectProperty<>(ticket.getPrecioTotal());
        this.lineaTickets = new SimpleSetProperty<>(FXCollections.observableSet());
        this.bean=ticket;
    }

    public Long getNumTicket() {
        return numTicket.get();
    }

    public void setNumTicket(Long id) {
        this.numTicket.set(id);
    }

    public ObjectProperty<Long> numTicketProperty() {
        return numTicket;
    }

    public Local getLocal() {
        return local.get();
    }

    public void setLocal(Local local) {
        this.local.set(local);
    }

    public ObjectProperty<Local> localProperty() {
        return local;
    }

    public Date getFecha() {
        return fecha.get();
    }

    public void setFecha(Date fecha) {
        this.fecha.set(fecha);
    }

    public ObjectProperty<Date> fechaProperty() {
        return fecha;
    }

    public String getTipoTicket() {
        return tipoTicket.get();
    }

    public void setTipoTicket(String tipoTicket) {
        this.tipoTicket.set(tipoTicket);
    }

    public StringProperty tipoTicketProperty() {
        return tipoTicket;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal.get();
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal.set(precioTotal);
    }

    public ObjectProperty<BigDecimal> precioTotalProperty() {
        return precioTotal;
    }

    public Set getLineaTickets() {
        return lineaTickets.get();
    }

    public void setLineaTickets(Set lineaTickets) {
        this.lineaTickets.clear();
        this.lineaTickets.addAll(lineaTickets);
    }

    public SetProperty<LineaTicket> lineaTicketsProperty() {
        return lineaTickets;
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
        return "Fecha=" + getFecha().toString() + " numero: " + getNumTicket();
    }
    
}//fin de la clase
