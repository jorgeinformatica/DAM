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
    private Set empleados = new HashSet(0);
    private Set stocks = new HashSet(0);
    private Set tickets = new HashSet(0);
    private Set pedidos = new HashSet(0);
    
    public Local() {
    }
    
    public Local(Direccion direccion, boolean estado) {
        this.direccion = direccion;
        this.estado = estado;
    }
    
    public Local(Direccion direccion, boolean estado, Set empleados, Set stocks, Set tickets, Set pedidos) {
        this.direccion = direccion;
        this.estado = estado;
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
        direccion.setNombre(((LocalFX) o).getDireccion().getNombre());
        direccion.setNumero(((LocalFX) o).getDireccion().getNumero());
        direccion.getCiudadConcp().setCiudad((Ciudad) ((LocalFX) o).getDireccion().getRelCpCiu().getCiudad().getBean());
        direccion.getCiudadConcp().setCodigoPostal((CodigoPostal) ((LocalFX) o).getDireccion().getRelCpCiu().getCodigoPostal().getBean());
        direccion.getCiudadConcp().setProvincia((Provincia) ((LocalFX) o).getDireccion().getRelCpCiu().getProvincia().getBean());
    }
    
    @Override
    public String toString() {
        return "Local: " + codLocal + " " + direccion;
    }
    
}//fin de clase
