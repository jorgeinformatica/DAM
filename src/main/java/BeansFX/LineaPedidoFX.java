package BeansFX;

import Beans.LineaPedido;
import Beans.LineaPedidoId;
import Beans.Pedido;
import Beans.Producto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaPedidoFX extends BaseFX{

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<LineaPedidoId> idLineaPedido;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Pedido> pedido;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Producto> producto;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> cantidad;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty estado;

    public LineaPedidoFX() {
        this.idLineaPedido = new SimpleObjectProperty<>();
        this.pedido = new SimpleObjectProperty<>();
        this.producto = new SimpleObjectProperty<>();
        this.cantidad = new SimpleObjectProperty<>();
        this.estado = new SimpleStringProperty();
        this.beanFX = new SimpleObjectProperty<>(this);
    }

    public LineaPedidoFX(LineaPedido lineaPedido) {
        this.idLineaPedido = new SimpleObjectProperty<>(lineaPedido.getId());
        this.pedido = new SimpleObjectProperty<>(lineaPedido.getPedido());
        this.producto = new SimpleObjectProperty<>(lineaPedido.getProducto());
        this.cantidad = new SimpleObjectProperty<>(lineaPedido.getCantidad());
        this.estado = new SimpleStringProperty(lineaPedido.getEstado());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean=lineaPedido;
    }

    public LineaPedidoId getIdLineaPedido() {
        return idLineaPedido.get();
    }

    public void setIdLineaPedido(LineaPedidoId idLineaPedido) {
        this.idLineaPedido.set(idLineaPedido);
    }

    public ObjectProperty<LineaPedidoId> idLineaPedidoProperty() {
        return idLineaPedido;
    }

    public Pedido getPedido() {
        return pedido.get();
    }

    public void setPedido(Pedido pedido) {
        this.pedido.set(pedido);
    }

    public ObjectProperty<Pedido> pedidoProperty() {
        return pedido;
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

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public StringProperty estadoProperty() {
        return estado;
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
        return "Linea: " + getIdLineaPedido().getNumLinPed() + " producto: " + getProducto().toString();
    }
    
}//fin de clase
