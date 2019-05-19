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
    private ObjectProperty<LineaPedidoIdFX> idLineaPedido;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<PedidoFX> pedido;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<ProductoFX> producto;
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
        this.idLineaPedido = new SimpleObjectProperty<>(new LineaPedidoIdFX(lineaPedido.getId()));
        this.pedido = new SimpleObjectProperty<>(new PedidoFX(lineaPedido.getPedido()));
        this.producto = new SimpleObjectProperty<>(new ProductoFX(lineaPedido.getProducto()));
        this.cantidad = new SimpleObjectProperty<>(lineaPedido.getCantidad());
        this.estado = new SimpleStringProperty(lineaPedido.getEstado());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean=lineaPedido;
    }

    public LineaPedidoIdFX getIdLineaPedido() {
        return idLineaPedido.get();
    }

    public void setIdLineaPedido(LineaPedidoIdFX idLineaPedido) {
        this.idLineaPedido.set(idLineaPedido);
    }

    public ObjectProperty<LineaPedidoIdFX> idLineaPedidoProperty() {
        return idLineaPedido;
    }

    public PedidoFX getPedido() {
        return pedido.get();
    }

    public void setPedido(PedidoFX pedido) {
        this.pedido.set(pedido);
    }

    public ObjectProperty<PedidoFX> pedidoProperty() {
        return pedido;
    }

    public ProductoFX getProducto() {
        return producto.get();
    }

    public void setProducto(ProductoFX producto) {
        this.producto.set(producto);
    }

    public ObjectProperty<ProductoFX> productoProperty() {
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
