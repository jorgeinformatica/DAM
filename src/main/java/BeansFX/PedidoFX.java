package BeansFX;

import Beans.LineaPedido;
import Beans.Pedido;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
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
public class PedidoFX extends BaseFX {
    
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Long> numPed;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<LocalFX> local;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Date> fechaPed;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Date> fechaEntrega;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty estado;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<LineaPedido> lineasPedido;
    
    public PedidoFX() {
        this.numPed = new SimpleObjectProperty<>();
        this.local = new SimpleObjectProperty<>();
        this.fechaPed = new SimpleObjectProperty<>();
        this.fechaEntrega = new SimpleObjectProperty<>();
        this.estado = new SimpleStringProperty();
        this.lineasPedido = new SimpleSetProperty<>(FXCollections.observableSet());
    }
    
    public PedidoFX(Pedido pedido) {
        this.numPed = new SimpleObjectProperty<>(pedido.getNumPed());
        this.local = new SimpleObjectProperty<>(new LocalFX(pedido.getLocal()));
        this.fechaPed = new SimpleObjectProperty<>(pedido.getFechaPed());
        this.fechaEntrega = new SimpleObjectProperty<>(pedido.getFechaEntrega());
        this.estado = new SimpleStringProperty(pedido.getEstado());
        this.lineasPedido = new SimpleSetProperty<>(FXCollections.observableSet(pedido.getLineaPedidos()));
        this.bean = pedido;
    }
    
    public Long getNumPed() {
        return numPed.get();
    }
    
    public void setNumPed(Long numPed) {
        this.numPed.set(numPed);
    }
    
    public ObjectProperty<Long> numPedProperty() {
        return numPed;
    }
    
    public LocalFX getLocal() {
        return local.get();
    }
    
    public void setLocal(LocalFX local) {
        this.local.set(local);
    }
    
    public ObjectProperty<LocalFX> localProperty() {
        return local;
    }
    
    public Date getFechaPed() {
        return fechaPed.get();
    }
    
    public void setFechaPed(Date fechaPed) {
        this.fechaPed.set(fechaPed);
    }
    
    public ObjectProperty<Date> fechaPedProperty() {
        return fechaPed;
    }
    
    public Date getFechaEntrega() {
        return fechaEntrega.get();
    }
    
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega.set(fechaEntrega);
    }
    
    public ObjectProperty<Date> fechaEntregaProperty() {
        return fechaEntrega;
    }
    
    public String getEstado() {
        return estado.get();
    }
    
    public void setEstado(String estado) {
        this.estado.set(estado);
    }
    
    public StringProperty estadoProperty() {
        return estado;
    }
    
    public Set getLineasPedido() {
        return lineasPedido.get();
    }
    
    public void setLineasPedido(Set lineasPedido) {
        this.lineasPedido.clear();
        this.lineasPedido.addAll(lineasPedido);
    }
    
    public SetProperty<LineaPedido> lineasPedidoProperty() {
        return lineasPedido;
    }
    
    @Override
    public boolean comprobarCambios() {
        if (!getFechaPed().equals(((Pedido) getBean()).getFechaPed())) {
            return true;
        }
        if (!getFechaEntrega().equals(((Pedido) getBean()).getFechaEntrega())) {
            return true;
        }
        if (!Objects.equals(getLocal().getCodLocal(), ((Pedido) getBean()).getLocal().getCodLocal())) {
            return true;
        }
        return !getEstado().equals(((Pedido) getBean()).getEstado());
    }
    
    @Override
    public void sinCambios() {
        setEstado(((Pedido) getBean()).getEstado());
        setFechaPed(((Pedido) getBean()).getFechaPed());
        setFechaEntrega(((Pedido) getBean()).getFechaEntrega());
        setLocal(new LocalFX(((Pedido) getBean()).getLocal()));
    }
    
    @Override
    public String toString() {
        return new SimpleDateFormat("dd-MMMM-yyyy").format(getFechaEntrega());
    }
    
}//fin de clase
