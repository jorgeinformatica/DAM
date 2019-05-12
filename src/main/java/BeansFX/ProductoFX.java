package BeansFX;

import Beans.LineaPedido;
import Beans.LineaTicket;
import Beans.Producto;
import Beans.Stock;
import java.math.BigDecimal;
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
public class ProductoFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> codProd;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty nombre;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty tipoIva;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<BigDecimal> precio;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty descripcion;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<LineaTicket> lineaTickets;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<LineaPedido> lineaPedidos;
    @SuppressWarnings("FieldMayBeFinal")
    private SetProperty<Stock> stocks;
    @SuppressWarnings("FieldMayBeFinal")
    private BooleanProperty estado;

    public ProductoFX() {
        this.beanFX = new SimpleObjectProperty<>(this);
        this.codProd = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.tipoIva = new SimpleStringProperty();
        this.precio = new SimpleObjectProperty<>();
        this.descripcion = new SimpleStringProperty();
        this.estado = new SimpleBooleanProperty();
        this.lineaTickets = new SimpleSetProperty<>(FXCollections.observableSet());
        this.lineaPedidos = new SimpleSetProperty<>(FXCollections.observableSet());
        this.stocks = new SimpleSetProperty<>(FXCollections.observableSet());
    }

    public ProductoFX(Producto producto) {
        this.beanFX = new SimpleObjectProperty<>(this);
        this.codProd = new SimpleObjectProperty<>(producto.getCodProd());
        this.nombre = new SimpleStringProperty(producto.getNombre());
        this.tipoIva = new SimpleStringProperty(producto.getTipoIva());
        this.precio = new SimpleObjectProperty<>(producto.getPrecio());
        this.descripcion = new SimpleStringProperty(producto.getDescripcion());
        this.estado = new SimpleBooleanProperty(producto.getEstado());
        this.lineaTickets = new SimpleSetProperty<>(FXCollections.observableSet(producto.getLineaTickets()));
        this.lineaPedidos = new SimpleSetProperty<>(FXCollections.observableSet(producto.getLineaPedidos()));
        this.stocks = new SimpleSetProperty<>(FXCollections.observableSet(producto.getStocks()));
        this.bean = producto;
    }

    public Short getCodProd() {
        return codProd.get();
    }

    public void setCodProd(Short codProd) {
        this.codProd.set(codProd);
    }

    public ObjectProperty<Short> codProdProperty() {
        return codProd;
    }

    public String getNombre() {
        return nombre.getValueSafe();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getTipoIva() {
        return tipoIva.getValueSafe();
    }

    public void setTipoIva(String tipoIva) {
        this.tipoIva.set(tipoIva);
    }

    public StringProperty tipoIvaProperty() {
        return tipoIva;
    }

    public BigDecimal getPrecio() {
        return precio.get();
    }

    public void setPrecio(BigDecimal precio) {
        this.precio.set(precio);
    }

    public ObjectProperty<BigDecimal> precioProperty() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion.getValueSafe();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
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

    public Set getLineaPedidos() {
        return lineaPedidos.get();
    }

    public void setLineaPedidos(Set lineaPedidos) {
        this.lineaPedidos.clear();
        this.lineaPedidos.addAll(lineaPedidos);
    }

    public SetProperty<LineaPedido> lineaPedidosProperty() {
        return lineaPedidos;
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

    public Boolean getEstado() {
        return estado.get();
    }

    public void setEstado(Boolean estado) {
        this.estado.set(estado);
    }

    public BooleanProperty estadoProperty() {
        return estado;
    }

    @Override
    public boolean comprobarCambios() {
        if (!((Producto) bean).getNombre().equals(getNombre())) {
            return true;
        }
        if (((Producto) bean).getPrecio().compareTo(getPrecio()) != 0) {
            return true;
        }
        if (!((Producto) bean).getTipoIva().equals(getTipoIva())) {
            return true;
        }
        return !((Producto) bean).getDescripcion().equals(getDescripcion());
    }

    @Override
    public void sinCambios() {
        setNombre(((Producto) bean).getNombre());
        setDescripcion(((Producto) bean).getDescripcion());
        setTipoIva(((Producto) bean).getTipoIva());
        setPrecio(((Producto) bean).getPrecio());
    }

    @Override
    public String toString() {
        return getNombre();
    }

}//fin de clase
