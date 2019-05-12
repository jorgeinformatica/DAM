package Beans;

import BeansFX.BaseFX;
import BeansFX.ProductoFX;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class Producto implements java.io.Serializable, BaseBean {

    private Short codProd;
    private String nombre;
    private BigDecimal precio;
    private String tipoIva;
    private String descripcion;
    private boolean estado;
    private Set lineaTickets = new HashSet(0);
    private Set stocks = new HashSet(0);
    private Set lineaPedidos = new HashSet(0);

    public Producto() {
    }

    public Producto(String nombre, BigDecimal precio, String tipoIva, boolean estado) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipoIva = tipoIva;
        this.estado = estado;
    }

    public Producto(String nombre, BigDecimal precio, String tipoIva, String descripcion, boolean estado, Set lineaTickets, Set stocks, Set lineaPedidos) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipoIva = tipoIva;
        this.descripcion = descripcion;
        this.estado = estado;
        this.lineaTickets = lineaTickets;
        this.stocks = stocks;
        this.lineaPedidos = lineaPedidos;
    }

    public Short getCodProd() {
        return this.codProd;
    }

    public void setCodProd(Short codProd) {
        this.codProd = codProd;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return this.precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getTipoIva() {
        return this.tipoIva;
    }

    public void setTipoIva(String tipoIva) {
        this.tipoIva = tipoIva;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Set getLineaTickets() {
        return this.lineaTickets;
    }

    public void setLineaTickets(Set lineaTickets) {
        this.lineaTickets = lineaTickets;
    }

    public Set getStocks() {
        return this.stocks;
    }

    public void setStocks(Set stocks) {
        this.stocks = stocks;
    }

    public Set getLineaPedidos() {
        return this.lineaPedidos;
    }

    public void setLineaPedidos(Set lineaPedidos) {
        this.lineaPedidos = lineaPedidos;
    }

    @Override
    public void actualizarDatos(BaseFX o) {
        nombre = ((ProductoFX) o).getNombre();
        precio = ((ProductoFX) o).getPrecio();
        tipoIva = ((ProductoFX) o).getTipoIva();
        descripcion = ((ProductoFX) o).getDescripcion();
    }

    @Override
    public String toString() {
        return nombre;
    }

}//fin de clase
