package BeansFX;

import Beans.BaseBean;

/**
 * @author Jorge Sempere Jimenez
 */
public abstract class BaseFX {

    protected BaseBean bean;

    public abstract boolean comprobarCambios();

    public abstract void sinCambios();

    public BaseBean getBean() {
        return bean;
    }

    public void setBean(BaseBean bean) {
        this.bean = bean;
    }

}//fin de clase
