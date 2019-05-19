package Beans;

import BeansFX.BaseFX;
import BeansFX.PedidoFX;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class Pedido implements java.io.Serializable, BaseBean {

    private Integer numPed;
    private Local local;
    private Date fechaPed;
    private Date fechaEntrega;
    private String estado;
    private Set lineaPedidos = new HashSet(0);

    public Pedido() {
    }

    public Pedido(Local local, Date fechaPed, Date fechaEntrega, String estado) {
        this.local = local;
        this.fechaPed = fechaPed;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

    public Pedido(Local local, Date fechaPed, Date fechaEntrega, String estado, Set lineaPedidos) {
        this.local = local;
        this.fechaPed = fechaPed;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.lineaPedidos = lineaPedidos;
    }

    public Integer getNumPed() {
        return this.numPed;
    }

    public void setNumPed(Integer numPed) {
        this.numPed = numPed;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Date getFechaPed() {
        return this.fechaPed;
    }

    public void setFechaPed(Date fechaPed) {
        this.fechaPed = fechaPed;
    }

    public Date getFechaEntrega() {
        return this.fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set getLineaPedidos() {
        return this.lineaPedidos;
    }

    public void setLineaPedidos(Set lineaPedidos) {
        this.lineaPedidos = lineaPedidos;
    }

    @Override
    public void actualizarDatos(BaseFX o) {
        fechaPed = ((PedidoFX) o).getFechaPed();
        fechaEntrega = ((PedidoFX) o).getFechaEntrega();
        estado = ((PedidoFX) o).getEstado();
        local = ((PedidoFX) o).getLocal();
    }

    @Override
    public String toString() {
        return "Fecha: " + fechaPed.toString() + " numero: " + numPed;
    }

}//fin de clase
