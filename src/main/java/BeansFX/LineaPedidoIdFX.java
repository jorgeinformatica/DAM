package BeansFX;

import Beans.LineaPedidoId;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class LineaPedidoIdFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> numLinPed;
    @SuppressWarnings("FieldMayBeFinal")
    private IntegerProperty numPed;


    public LineaPedidoIdFX() {
        this.numLinPed = new SimpleObjectProperty<>();
        this.numPed = new SimpleIntegerProperty();
        this.beanFX = new SimpleObjectProperty<>(this);
    }

    public LineaPedidoIdFX(LineaPedidoId lineaPedidoId) {
        this.numLinPed = new SimpleObjectProperty<>(lineaPedidoId.getNumLinPed());
        this.numPed = new SimpleIntegerProperty(lineaPedidoId.getNumPed());
        this.beanFX = new SimpleObjectProperty<>(this);
        this.bean = lineaPedidoId;
    }

    public Short getNumLinPed() {
        return numLinPed.get();
    }

    public void setNumLinPed(Short numLinPed) {
        this.numLinPed.set(numLinPed);
    }

    public ObjectProperty<Short> numLinPedProperty() {
        return numLinPed;
    }

    public Integer getNumPed() {
        return numPed.get();
    }

    public void setNumPed(Integer numPed) {
        this.numPed.set(numPed);
    }

    public IntegerProperty numPedProperty() {
        return numPed;
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
        return "Pedido: " + getNumPed() + " linea: " + getNumLinPed();
    }

}//fin de clase
