package Beans;

import BeansFX.BaseFX;
import BeansFX.LineaPedidoFX;

/**
 * @author Jorge Sempere
 */
public class LineaPedido implements java.io.Serializable, BaseBean {

    private Long numLinPed;
    private Pedido pedido;
    private Producto producto;
    private short cantidad;
    private String estado;

    public LineaPedido() {
    }

    public LineaPedido(Long id, Pedido pedido, Producto producto, short cantidad, String estado) {
        this.numLinPed = id;
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public Long getNumLinPed() {
        return this.numLinPed;
    }

    public void setNumLinPed(Long id) {
        this.numLinPed = id;
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
        estado = ((LineaPedidoFX) o).getEstado();
        cantidad=((LineaPedidoFX) o).getCantidad();
        producto=(Producto) ((LineaPedidoFX) o).getProducto().getBean();
    }

    @Override
    public String toString() {
        return "Linea: " + numLinPed + " producto: " + producto;
    }

}//fin de clase
