package BeansFX;

import Beans.StockId;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Jorge Sempere Jimenez
 */
public class StockIdFX extends BaseFX {

    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> codLocal;
    @SuppressWarnings("FieldMayBeFinal")
    private ObjectProperty<Short> codProd;

    public StockIdFX() {
        this.codLocal = new SimpleObjectProperty<>();
        this.codProd = new SimpleObjectProperty<>();
    }

    public StockIdFX(StockId stockId) {
        this.codLocal = new SimpleObjectProperty<>(stockId.getCodLocal());
        this.codProd = new SimpleObjectProperty<>(stockId.getCodProd());
        this.bean=stockId;
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

    public Short getCodProd() {
        return codProd.get();
    }

    public void setCodProd(Short codProd) {
        this.codProd.set(codProd);
    }

    public ObjectProperty<Short> codProdProperty() {
        return codProd;
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
        return "Local: " + getCodLocal() + " Producto: " + getCodProd();
    }
    
}//fin de clase
