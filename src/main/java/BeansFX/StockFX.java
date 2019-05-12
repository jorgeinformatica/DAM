package BeansFX;

import Beans.Local;
import Beans.Producto;
import Beans.Stock;
import Beans.StockId;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class StockFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<StockId> id;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Local> local;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Producto> producto;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> cantidad;

    public StockFX() {
        this.id = new SimpleObjectProperty<>();
        this.local = new SimpleObjectProperty<>();
        this.producto = new SimpleObjectProperty<>();
        this.cantidad = new SimpleObjectProperty<>();
        this.beanFX = new SimpleObjectProperty<>(this);
    }

    public StockFX(Stock stock) {
        this.id = new SimpleObjectProperty<>(stock.getId());
        this.local = new SimpleObjectProperty<>(stock.getLocal());
        this.producto = new SimpleObjectProperty<>(stock.getProducto());
        this.cantidad = new SimpleObjectProperty<>(stock.getCantidad());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean = stock;
    }

    public StockId getId() {
        return id.get();
    }

    public void setId(StockId id) {
        this.id.set(id);
    }

    public ObjectProperty<StockId> idProperty() {
        return id;
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

    public Producto getProducto() {
        return producto.get();
    }

    public void setProducto(Producto producto) {
        this.producto.set(producto);
    }

    public ObjectProperty<Producto> productoProperty() {
        return producto;
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
        return getProducto().toString() + " cantidad:" + getCantidad();
    }

}//fin de clase
