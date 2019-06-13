package BeansFX;

import Beans.LineaPedido;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaPedidoFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Long> idLineaPedido;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<PedidoFX> pedido;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<ProductoFX> producto;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> cantidad;
    @SuppressWarnings("FieldMayBeFinal")
    private StringProperty estado;
    @SuppressWarnings("FieldMayBeFinal")
    private BooleanProperty esEditable;

    public LineaPedidoFX() {
        this.idLineaPedido = new SimpleObjectProperty<>();
        this.pedido = new SimpleObjectProperty<>();
        this.producto = new SimpleObjectProperty<>();
        this.cantidad = new SimpleObjectProperty<>();
        this.estado = new SimpleStringProperty();
        this.esEditable = new SimpleBooleanProperty(false);
    }

    public LineaPedidoFX(LineaPedido lineaPedido) {
        this.idLineaPedido = new SimpleObjectProperty<>(lineaPedido.getNumLinPed());
        this.pedido = new SimpleObjectProperty<>(new PedidoFX(lineaPedido.getPedido()));
        this.producto = new SimpleObjectProperty<>(new ProductoFX(lineaPedido.getProducto()));
        this.cantidad = new SimpleObjectProperty<>(lineaPedido.getCantidad());
        this.estado = new SimpleStringProperty(lineaPedido.getEstado());
        this.esEditable = new SimpleBooleanProperty(false);
        this.bean = lineaPedido;
    }

    public Long getIdLineaPedido() {
        return idLineaPedido.get();
    }

    public void setIdLineaPedido(Long idLineaPedido) {
        this.idLineaPedido.set(idLineaPedido);
    }

    public ObjectProperty<Long> idLineaPedidoProperty() {
        return idLineaPedido;
    }

    public PedidoFX getPedido() {
        return pedido.get();
    }

    public Boolean getEsEditable() {
        return esEditable.get();
    }

    public void setEsEditable(Boolean esEditable) {
        this.esEditable.set(esEditable);
    }

    public BooleanProperty esEditableProperty() {
        return esEditable;
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
        if (getCantidad() != ((LineaPedido) getBean()).getCantidad()) {
            return true;
        }
        if (!getEstado().equalsIgnoreCase(((LineaPedido) getBean()).getEstado())) {
            return true;
        }
        return !Objects.equals(getProducto().getCodProd(), ((LineaPedido) getBean()).getProducto().getCodProd());
    }

    @Override
    public void sinCambios() {
        setCantidad(((LineaPedido) getBean()).getCantidad());
        setEstado(((LineaPedido) getBean()).getEstado());
        setProducto(new ProductoFX(((LineaPedido) getBean()).getProducto()));
    }

    @Override
    public String toString() {
        return "Linea: " + getIdLineaPedido() + " producto: " + getProducto().toString();
    }

}//fin de clase
