package dam.proyecto;

import Beans.BaseBean;
import Beans.Ciudad;
import Beans.CiudadConcp;
import Beans.CodigoPostal;
import Beans.Empleado;
import Beans.Producto;
import Beans.Provincia;
import BeansFX.BaseFX;
import BeansFX.CiudadConcpFX;
import BeansFX.CiudadFX;
import BeansFX.CodigoPostalFX;
import BeansFX.EmpleadoFX;
import BeansFX.PedidoFX;
import BeansFX.ProductoFX;
import BeansFX.ProvinciaFX;
import Controllers.AAController;
import Controllers.LogueoController;
import Hibernate.HibernateController;
import Utils.Constantes;
import Utils.PropertiesUtil;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author Jorge Sempere
 */
public class LogicController {

    private Hibernate.HibernateController hibControl;
    private HashMap mapProperties;
    private AAController viewControl;
    private static ObservableList<CiudadFX> ciudades;
    private static ObservableList<ProvinciaFX> provincias;
    private static ObservableList<CodigoPostalFX> cps;
    private static ObservableList<CiudadConcpFX> ccps;
    private ObservableList<ProductoFX> productos;
    private Parent root;
    private EmpleadoFX usuario;
    private ProductoFX producto;
    private PedidoFX pedido;

    public LogicController(AAController viewControl) {
        this.producto = null;
        this.pedido = null;
        this.viewControl = viewControl;
        LogicController.provincias = FXCollections.observableArrayList();
        LogicController.ciudades = FXCollections.observableArrayList();
        this.productos = FXCollections.observableArrayList();
        LogicController.cps = FXCollections.observableArrayList();
        LogicController.ccps = FXCollections.observableArrayList();
        initHibernate();
        initOList();
        initmapProperties();
        initUser();
    }

    private void initHibernate() {
        this.hibControl = new HibernateController();
    }

    private void initOList() {
        FXCollections.observableList(hibControl.getList(Provincia.class, Constantes.HQLCondicion.NEUTRO.getCondicion())).forEach((pro) -> {
            this.provincias.add(new ProvinciaFX((Provincia) pro));
        });
        FXCollections.observableList(hibControl.getList(Ciudad.class, Constantes.HQLCondicion.NEUTRO.getCondicion())).forEach((ciu) -> {
            this.ciudades.add(new CiudadFX((Ciudad) ciu));
        });
        FXCollections.observableList(hibControl.getList(CodigoPostal.class, Constantes.HQLCondicion.NEUTRO.getCondicion())).forEach((cp) -> {
            this.cps.add(new CodigoPostalFX((CodigoPostal) cp));
        });
        FXCollections.observableList(hibControl.getList(CiudadConcp.class, Constantes.HQLCondicion.NEUTRO.getCondicion())).forEach((ccp) -> {
            this.ccps.add(new CiudadConcpFX((CiudadConcp) ccp));
        });
        FXCollections.observableList(hibControl.getList(Producto.class, Constantes.HQLCondicion.ESTADO.getCondicion())).forEach((pro) -> {
            this.productos.add(new ProductoFX((Producto) pro));
        });
    }

    private void initmapProperties() {
        PropertiesUtil prop = PropertiesUtil.getPropUtil();
        mapProperties = prop.getStatus();
    }

    private void initUser() {
        viewControl.setLogic(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Logueo.fxml"));
        setNodo(loader);
        viewControl.getBarra().setVisible(false);
        ((LogueoController) viewControl.getSceneActiva()).setViewControl(viewControl);
    }

    public boolean setUser(String user, String pass) {
        Object objeto = hibControl.searchElement(Empleado.class, " dni like '" + pass + "' AND telefono=" + user);
        if (objeto != null) {
            usuario = new EmpleadoFX((Empleado) objeto);
            return true;
        }
        return false;
    }

    public EmpleadoFX getUsuario() {
        return usuario;
    }

    public ObservableList<CiudadFX> getCiudades() {
        return ciudades;
    }

    public ObservableList<ProvinciaFX> getProvincias() {
        return provincias;
    }

    public ObservableList<CodigoPostalFX> getCps() {
        return cps;
    }

    public ObservableList<ProductoFX> getProductos() {
        return productos;
    }

    public HibernateController getHibControl() {
        return hibControl;
    }

    public ProductoFX getProducto() {
        return producto;
    }

    public void setProducto(ProductoFX producto) {
        this.producto = producto;
    }

    public PedidoFX getPedido() {
        return pedido;
    }

    public void setPedido(PedidoFX pedido) {
        this.pedido = pedido;
    }

    public Parent getRoot() {
        return root;
    }

    /**
     * Cambia el "panel" en la escene
     *
     * @param loader es el "cargador" del nodo a asignar a la scene principal
     */
    public void setNodo(FXMLLoader loader) {
        try {
            root = loader.load();
            AnchorPane.setTopAnchor(root, 0d);
            AnchorPane.setLeftAnchor(root, 0d);
            AnchorPane.setRightAnchor(root, 0d);
            AnchorPane.setBottomAnchor(root, 0d);
            viewControl.getBase().getChildren().add(root);
            viewControl.setSceneActiva(loader.getController());
        } catch (IOException ex) {
            Logger.getLogger(LogicController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param elem es la instancia que ha cambiado con respecto a su "Bean"
     * @return Boolean si se ha actualizado o no.
     */
    @SuppressWarnings("null")
    public boolean actualizarMsg(BaseFX elem) {
        if (elem != null) {
            if (elem.comprobarCambios()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Han cambiado las características de: \"" + elem.toString() + "\"" + System.lineSeparator() + "¿Quiere actualizarlo?");
                alert.initOwner(viewControl.getStage());
                Optional<ButtonType> resol = alert.showAndWait();
                if (resol.get() == ButtonType.OK) {
                    hibControl.initTransaction();
                    elem.getBean().actualizarDatos(elem);
                    hibControl.UpdateElement(elem.getBean());
                    return false;
                } else {
                    elem.sinCambios();
                }
            }
        }
        return true;
    }

    /**
     * @param elem es la instancia que se quiere desactivar
     * @return Boolean si se ha desactivado o no
     */
    @SuppressWarnings({"null", "element-type-mismatch"})
    public boolean desactivarMsg(BaseFX elem) {
        if (elem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Tiene elegido el elemento: \"" + elem.toString() + "\"."
                    + System.lineSeparator() + "¿Quiere desactivarlo?");
            alert.initOwner(viewControl.getStage());
            Optional<ButtonType> resol = alert.showAndWait();
            if (resol.get() == ButtonType.OK) {
                Method[] metodos = elem.getBean().getClass().getDeclaredMethods();
                for (Method metodo : metodos) {
                    if (metodo.getName().equals("setEstado")) {
                        try {
                            metodo.invoke(elem.getBean(), false);
                            hibControl.initTransaction();
                            hibControl.UpdateElement(elem.getBean());
                            return true;
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            ex.getMessage();
                        }
                    }
                }
            }
        }
        return false;
    }

    public void aceptarCambiosBtn(Button btn, BaseFX bean) {
        btn.setVisible(true);
        Timeline flash = new Timeline(
                new KeyFrame(Duration.seconds(0), evt -> btn.setStyle(" -fx-text-fill: rgba(183, 28, 28,0)")),
                new KeyFrame(Duration.seconds(0.50), evt -> btn.setStyle(" -fx-text-fill: rgba(183, 28, 28,0.5)")),
                new KeyFrame(Duration.seconds(1), evt -> btn.setStyle(" -fx-text-fill: rgba(183, 28, 28,1)"))
        );
        flash.setCycleCount(Animation.INDEFINITE);
        flash.play();
        btn.setOnAction((ActionEvent evt) -> {
            hibControl.initTransaction();
            bean.getBean().actualizarDatos(bean);
            viewControl.getLogic().getHibControl().UpdateElement(bean.getBean());
            flash.stop();
            btn.setVisible(false);
        });
    }

    public static ProvinciaFX getProvFX(Provincia pro) {
        for (ProvinciaFX provincia : provincias) {
            if (Objects.equals(((Provincia) provincia.getBean()), pro)) {
                return provincia;
            }
        }
        return null;
    }

    public static CiudadFX getCiuFX(Ciudad ciu) {
        for (CiudadFX ciudad : ciudades) {
            if (Objects.equals(((Ciudad) ciudad.getBean()), ciu)) {
                return ciudad;
            }
        }
        return null;
    }

    public static CodigoPostalFX getCPFX(CodigoPostal cop) {
        for (CodigoPostalFX cp : cps) {
            if (Objects.equals(((CodigoPostal) cp.getBean()), cop)) {
                return cp;
            }
        }
        return null;
    }

    public static CiudadConcpFX getCCFX(CiudadFX ciu, CodigoPostalFX cp) {
        for (CiudadConcpFX ccp : ccps) {
            if (Objects.equals(ccp.getId().getCodCiudad(), ciu.getCodCiudad())
                    && cp.getCodPostal().equals(ccp.getId().getCodPostal())) {
                return ccp;
            }
        }
        return null;
    }

}//fin de la clase
