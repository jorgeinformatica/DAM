package Controllers;

import Beans.LineaPedido;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jorge Sempere Jimenez
 */
public class DashboardContainer {

    private final short id;
    private final String nombre;
    private final Map<Short, Integer> lineaTot;

    public DashboardContainer(short id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        lineaTot = new HashMap<>();
    }

    public short getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void addProduct(LineaPedido lp) {
        if (lineaTot.containsKey(lp.getProducto().getCodProd())) {
            int sub = lineaTot.get(lp.getProducto().getCodProd()) + lp.getCantidad();
            lineaTot.replace(lp.getProducto().getCodProd(), sub);
        } else {
            lineaTot.put(lp.getProducto().getCodProd(), (int) lp.getCantidad());
        }
    }

    public void modProduct(Short codProd, int cant) {
        lineaTot.replace(codProd, cant);
    }

    public int getCantidad(Short codProd) {
        return lineaTot.getOrDefault(codProd,0);
    }

}//fin de clase
