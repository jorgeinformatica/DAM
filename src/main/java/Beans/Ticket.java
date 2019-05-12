package Beans;

import BeansFX.BaseFX;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class Ticket implements java.io.Serializable, BaseBean {

    private Integer numTicket;
    private Local local;
    private Date fecha;
    private String tipoTicket;
    private BigDecimal precioTotal;
    private Set lineaTickets = new HashSet(0);

    public Ticket() {
    }

    public Ticket(Local local, Date fecha, String tipoTicket, BigDecimal precioTotal) {
        this.local = local;
        this.fecha = fecha;
        this.tipoTicket = tipoTicket;
        this.precioTotal = precioTotal;
    }

    public Ticket(Local local, Date fecha, String tipoTicket, BigDecimal precioTotal, Set lineaTickets) {
        this.local = local;
        this.fecha = fecha;
        this.tipoTicket = tipoTicket;
        this.precioTotal = precioTotal;
        this.lineaTickets = lineaTickets;
    }

    public Integer getNumTicket() {
        return this.numTicket;
    }

    public void setNumTicket(Integer numTicket) {
        this.numTicket = numTicket;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoTicket() {
        return this.tipoTicket;
    }

    public void setTipoTicket(String tipoTicket) {
        this.tipoTicket = tipoTicket;
    }

    public BigDecimal getPrecioTotal() {
        return this.precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Set getLineaTickets() {
        return this.lineaTickets;
    }

    public void setLineaTickets(Set lineaTickets) {
        this.lineaTickets = lineaTickets;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return "Fecha=" + fecha.toString() + " numero: " + numTicket;
    }

}//fin de clase
