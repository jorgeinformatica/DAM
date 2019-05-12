package BeansFX;

import Beans.Direccion;
import Beans.Empleado;
import Beans.Local;
import Beans.Pedido;
import Beans.Stock;
import Beans.Ticket;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;

/**
 * @author Jorge Sempere Jimenez
 */
public class LocalFX extends BaseFX {
    
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> codLocal;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Direccion> direccion;
    @SuppressWarnings("FieldMayBeFinal")
    private BooleanProperty estado;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<Pedido> pedidos;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<Empleado> empleados;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<Ticket> tickets;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<Stock> stocks;
    
    public LocalFX() {
        this.estado = new SimpleBooleanProperty();
        this.codLocal = new SimpleObjectProperty<>();
        this.direccion = new SimpleObjectProperty<>();
        this.pedidos = new SimpleSetProperty<>(FXCollections.observableSet());
        this.empleados = new SimpleSetProperty<>(FXCollections.observableSet());
        this.tickets = new SimpleSetProperty<>(FXCollections.observableSet());
        this.stocks = new SimpleSetProperty<>(FXCollections.observableSet());
        this.beanFX = new SimpleObjectProperty<>(this);
    }
    
    public LocalFX(Local local) {
        this.estado = new SimpleBooleanProperty(local.getEstado());
        this.codLocal = new SimpleObjectProperty<>(local.getCodLocal());
        this.direccion = new SimpleObjectProperty<>(local.getDireccion());
        this.pedidos = new SimpleSetProperty<>(FXCollections.observableSet(local.getPedidos()));
        this.empleados = new SimpleSetProperty<>(FXCollections.observableSet(local.getEmpleados()));
        this.tickets = new SimpleSetProperty<>(FXCollections.observableSet(local.getTickets()));
        this.stocks = new SimpleSetProperty<>(FXCollections.observableSet(local.getStocks()));
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean = local;
    }
    
    public Short getCodLocal() {
        return codLocal.get();
    }
    
    public void setCodLocal(Short codLocal) {
        this.codLocal.set(codLocal);
    }
    
    public ObjectProperty<Short> codLocalProperty() {
        return codLocal;
    }
    
    public Boolean getEstado() {
        return estado.get();
    }
    
    public void setEstado(Boolean estado) {
        this.estado.set(estado);
    }
    
    public BooleanProperty estadoProperty() {
        return estado;
    }
    
    public Direccion getDireccion() {
        return direccion.get();
    }
    
    public void setDireccion(Direccion direccion) {
        this.direccion.set(direccion);
    }
    
    public ObjectProperty<Direccion> direccionProperty() {
        return direccion;
    }
    
    public Set getPedidos() {
        return pedidos.get();
    }
    
    public void setPedidos(Set pedidos) {
        this.pedidos.clear();
        this.pedidos.addAll(pedidos);
    }
    
    public SetProperty<Pedido> pedidosProperty() {
        return pedidos;
    }
    
    public Set getEmpleados() {
        return empleados.get();
    }
    
    public void setEmpleados(Set empleados) {
        this.empleados.clear();
        this.empleados.addAll(empleados);
    }
    
    public SetProperty<Empleado> empleadosProperty() {
        return empleados;
    }
    
    public Set getTickets() {
        return tickets.get();
    }
    
    public void setTickets(Set tickets) {
        this.tickets.clear();
        this.tickets.addAll(tickets);
    }
    
    public SetProperty<Ticket> ticketsProperty() {
        return tickets;
    }
    
    public Set getStocks() {
        return stocks.get();
    }
    
    public void setStocks(Set stocks) {
        this.stocks.clear();
        this.stocks.addAll(stocks);
    }
    
    public SetProperty<Stock> stocksProperty() {
        return stocks;
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
        return "Local: " + getCodLocal() + " " + getDireccion().toString();
    }
    
}//fin de clase
