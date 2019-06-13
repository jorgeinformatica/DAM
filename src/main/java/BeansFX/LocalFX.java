package BeansFX;

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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * @author Jorge Sempere Jimenez
 */
public class LocalFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> codLocal;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<DireccionFX> direccion;
    @SuppressWarnings("FieldMayBeFinal")
    private BooleanProperty estado;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty nombre;
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
        this.nombre = new SimpleStringProperty();
        this.pedidos = new SimpleSetProperty<>(FXCollections.observableSet());
        this.empleados = new SimpleSetProperty<>(FXCollections.observableSet());
        this.tickets = new SimpleSetProperty<>(FXCollections.observableSet());
        this.stocks = new SimpleSetProperty<>(FXCollections.observableSet());
    }

    public LocalFX(Local local) {
        this.estado = new SimpleBooleanProperty(local.getEstado());
        this.codLocal = new SimpleObjectProperty<>(local.getCodLocal());
        this.nombre = new SimpleStringProperty(local.getNombre());
        this.direccion = new SimpleObjectProperty<>(new DireccionFX(local.getDireccion()));
        this.pedidos = new SimpleSetProperty<>(FXCollections.observableSet(local.getPedidos()));
        this.empleados = new SimpleSetProperty<>(FXCollections.observableSet(local.getEmpleados()));
        this.tickets = new SimpleSetProperty<>(FXCollections.observableSet(local.getTickets()));
        this.stocks = new SimpleSetProperty<>(FXCollections.observableSet(local.getStocks()));
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

    public DireccionFX getDireccion() {
        return direccion.get();
    }

    public void setDireccion(DireccionFX direccion) {
        this.direccion.set(direccion);
    }

    public ObjectProperty<DireccionFX> direccionProperty() {
        return direccion;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
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
        if(!((Local)getBean()).getNombre().equalsIgnoreCase(getNombre())){
            return true;
        }
        return getDireccion().comprobarCambios();
    }

    @Override
    public void sinCambios() {
        setNombre(((Local)getBean()).getNombre());
        getDireccion().sinCambios();
    }

    @Override
    public String toString() {
        return getNombre();
    }

}//fin de clase
