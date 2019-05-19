package BeansFX;

import Beans.LineaPedido;
import Beans.Local;
import Beans.Pedido;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private IntegerProperty numPed;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Local> local;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Date> fechaPed;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Date> fechaEntrega;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty estado;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<LineaPedido> lineasPedido;
    
    public PedidoFX() {
        this.numPed = new SimpleIntegerProperty();
        this.local = new SimpleObjectProperty<>();
        this.fechaPed = new SimpleObjectProperty<>();
        this.fechaEntrega = new SimpleObjectProperty<>();
        this.estado = new SimpleStringProperty();
        this.lineasPedido = new SimpleSetProperty<>(FXCollections.observableSet());
        this.beanFX = new SimpleObjectProperty<>(this);
    }
    
    public PedidoFX(Pedido pedido) {
        this.numPed = new SimpleIntegerProperty(pedido.getNumPed());
        this.local = new SimpleObjectProperty<>(pedido.getLocal());
        this.fechaPed = new SimpleObjectProperty<>(pedido.getFechaPed());
        this.fechaEntrega = new SimpleObjectProperty<>(pedido.getFechaEntrega());
        this.estado = new SimpleStringProperty(pedido.getEstado());
        this.lineasPedido = new SimpleSetProperty<>(FXCollections.observableSet(pedido.getLineaPedidos()));
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean = pedido;
    }
    
    public Integer getNumPed() {
        return numPed.get();
    }
    
    public void setNumPed(Integer numPed) {
        this.numPed.set(numPed);
    }
    
    public IntegerProperty numPedProperty() {
        return numPed;
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
        setLocal(((Pedido) getBean()).getLocal());
    }
    
    @Override
    public String toString() {
        return "Numero: " + getNumPed() + " [" + new SimpleDateFormat("dd/MM/yyyy").format(getFechaPed()) + "]";
    }
    
}//fin de clase
