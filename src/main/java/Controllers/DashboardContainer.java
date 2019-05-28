package Controllers;

import Beans.LineaPedido;
import java.util.HashMap;
import java.util.Map;
import Utils.Constantes;

/**
 * @author Jorge Sempere Jimenez
 */
public class DashboardContainer {

    private final short id;
    private final String nombre;
    private final Map<Short, String> lineaTot;

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
        if (Constantes.Estados.ENPRODUCCION.getNom().equalsIgnoreCase(lp.getEstado())) {
            if (lineaTot.containsKey(lp.getProducto().getCodProd())) {
                String sub = agregarProd(lineaTot.get(lp.getProducto().getCodProd()), lp.getCantidad(), false);
                lineaTot.replace(lp.getProducto().getCodProd(), sub);
            } else {
                lineaTot.put(lp.getProducto().getCodProd(), "0|" + lp.getCantidad());
            }
        } else if (Constantes.Estados.PREPARADO.getNom().equalsIgnoreCase(lp.getEstado())) {
            if (lineaTot.containsKey(lp.getProducto().getCodProd())) {
                String sub = agregarProd(lineaTot.get(lp.getProducto().getCodProd()), lp.getCantidad(), true);
                lineaTot.replace(lp.getProducto().getCodProd(), sub);
            } else {
                lineaTot.put(lp.getProducto().getCodProd(), lp.getCantidad() + "|" + lp.getCantidad());
            }
        }
    }

    public void modProduct(Short codProd, String variacion) {
        lineaTot.replace(codProd, variacion);
    }

    public String getCantidad(Short codProd) {
        return lineaTot.getOrDefault(codProd, "");
    }

    private String agregarProd(String get, short cantidad, boolean opc) {
        String[] split = get.split("[|]");
        int preparacion = Integer.valueOf(split[0]);
        int produccion = Integer.valueOf(split[1]);
        produccion += cantidad;
        if (opc) {
            preparacion += cantidad;
        }
        return preparacion + "|" + produccion;
    }

}//fin de clase
