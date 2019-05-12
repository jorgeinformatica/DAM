package BeansFX;

import Beans.BaseBean;
import javafx.beans.property.ObjectProperty;

/**
 * @author Jorge Sempere
 */
public abstract class BaseFX {

    protected ObjectProperty<BaseFX> beanFX;
    protected BaseBean bean;

    public abstract boolean comprobarCambios();

    public abstract void sinCambios();

    public BaseFX getBeanFX() {
        return beanFX.get();
    }

    public void setBeanFX(BaseFX productofx) {
        this.beanFX.set(productofx);
    }

    public ObjectProperty<BaseFX> beanFXProperty() {
        return beanFX;
    }

    public BaseBean getBean() {
        return bean;
    }

    public void setBean(BaseBean bean) {
        this.bean = bean;
    }

}//fin de la clase
