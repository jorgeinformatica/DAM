package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere Jimenez
 */
public class Stock implements java.io.Serializable, BaseBean {

    private StockId id;
    private Local local;
    private Producto producto;
    private short cantidad;

    public Stock() {
    }

    public Stock(StockId id, Local local, Producto producto, short cantidad) {
        this.id = id;
        this.local = local;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public StockId getId() {
        return this.id;
    }

    public void setId(StockId id) {
        this.id = id;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public short getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(short cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return producto.toString() + " cantidad:" + cantidad;
    }
    
}//fin de clase
