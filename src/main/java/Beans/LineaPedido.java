package Beans;

import BeansFX.BaseFX;

/**
 * @author Jorge Sempere
 */
public class LineaPedido implements java.io.Serializable, BaseBean {

    private LineaPedidoId id;
    private Pedido pedido;
    private Producto producto;
    private short cantidad;
    private String estado;

    public LineaPedido() {
    }

    public LineaPedido(LineaPedidoId id, Pedido pedido, Producto producto, short cantidad, String estado) {
        this.id = id;
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public LineaPedidoId getId() {
        return this.id;
    }

    public void setId(LineaPedidoId id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public void actualizarDatos(BaseFX o) {

    }

    @Override
    public String toString() {
        return "Linea: " + getId().getNumLinPed() + " producto: " + getProducto().toString();
    }

}//fin de clase
