package Beans;

import BeansFX.BaseFX;
import BeansFX.LocalFX;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jorge Sempere Jimenez
 */
public class Local implements java.io.Serializable, BaseBean {

    private Short codLocal;
    private Direccion direccion;
    private boolean estado;
    private String nombre;
    private Set empleados = new HashSet(0);
    private Set stocks = new HashSet(0);
    private Set tickets = new HashSet(0);
    private Set pedidos = new HashSet(0);

    public Local() {
    }

    public Local(Direccion direccion, boolean estado, String nombre) {
        this.direccion = direccion;
        this.estado = estado;
        this.nombre = nombre;
    }

    public Local(Direccion direccion, boolean estado, String nombre, Set empleados, Set stocks, Set tickets, Set pedidos) {
        this.direccion = direccion;
        this.estado = estado;
        this.nombre = nombre;
        this.empleados = empleados;
        this.stocks = stocks;
        this.tickets = tickets;
        this.pedidos = pedidos;
    }

    public Short getCodLocal() {
        return this.codLocal;
    }

    public void setCodLocal(Short codLocal) {
        this.codLocal = codLocal;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(Set empleados) {
        this.empleados = empleados;
    }

    public Set getStocks() {
        return this.stocks;
    }

    public void setStocks(Set stocks) {
        this.stocks = stocks;
    }

    public Set getTickets() {
        return this.tickets;
    }

    public void setTickets(Set tickets) {
        this.tickets = tickets;
    }

    public Set getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(Set pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public void actualizarDatos(BaseFX o) {
        nombre = ((LocalFX) o).getNombre();
        direccion.actualizarDatos(((LocalFX) o).getDireccion());
    }

    @Override
    public String toString() {
        return "Local: " + nombre;
    }

}//fin de clase
