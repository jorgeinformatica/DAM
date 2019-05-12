package BeansFX;

import Beans.LineaTicketId;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaTicketIdFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> numLinTicket;
    @SuppressWarnings("FieldMayBeFinal")
    private IntegerProperty numTicket;

    public LineaTicketIdFX() {
        this.numLinTicket = new SimpleObjectProperty<>();
        this.numTicket = new SimpleIntegerProperty();
        this.beanFX = new SimpleObjectProperty<>();
    }

    public LineaTicketIdFX(LineaTicketId lineaTicketId) {
        this.numLinTicket = new SimpleObjectProperty<>(lineaTicketId.getNumLinTicket());
        this.numTicket = new SimpleIntegerProperty(lineaTicketId.getNumTicket());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean = lineaTicketId;
    }

    public Short getNumLinTicket() {
        return numLinTicket.get();
    }

    public void setNumLinTicket(Short numLinTicket) {
        this.numLinTicket.set(numLinTicket);
    }

    public ObjectProperty<Short> numLinTicketProperty() {
        return numLinTicket;
    }

    public Integer getNumTicket() {
        return numTicket.get();
    }

    public void setNumTicket(Integer numTicket) {
        this.numTicket.set(numTicket);
    }

    public IntegerProperty numTicketProperty() {
        return numTicket;
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
        return "Ticket: " + getNumTicket() + " linea: " + getNumLinTicket();
    }

}//fin de clase
