package Controllers;

import dam.proyecto.LogicController;
import Utils.MetodosEstaticos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Sempere Jimenez
 */
public class AAController implements Initializable {

    @FXML
    private MenuItem mItListProd;
    @FXML
    private MenuItem mItModProd;
    @FXML
    private MenuItem mItUser;
    @FXML
    private MenuItem mItSalir;
    @FXML
    private MenuItem mItListPed;
    @FXML
    private MenuItem mItModPed;
    @FXML
    private MenuItem mItDash;
    @FXML
    private AnchorPane Base;
    @FXML
    private MenuBar Barra;
    @FXML
    private Menu Inicio;
    @FXML
    private Menu Productos;
    @FXML
    private Menu Pedidos;
    @FXML
    private Menu Preparacion;
    @FXML
    private MenuItem mItLocal;
    @FXML
    private MenuItem mItEmpleado;
    @FXML
    private MenuItem mItConfig;

    private Initializable sceneActiva;
    private Stage stage;
    private LogicController logic;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void mitList(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListarProductos.fxml"));
        logic.setNodo(loader);
        ListarProductosController controller = loader.getController();
        ((ListarProductosController) sceneActiva).setViewControl(this);
        controller.init();
    }

    @FXML
    private void mitModProd(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProductosAM.fxml"));
        logic.setNodo(loader);
        ProductosAMController controller = loader.getController();
        ((ProductosAMController) sceneActiva).setViewControl(this);
        controller.init(logic.getProducto(), getBase().getChildren());
    }

    @FXML
    private void mitUser(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Logueo.fxml"));
        logic.setNodo(loader);
        getBarra().setVisible(false);
        ((LogueoController) sceneActiva).setViewControl(this);
        stage.setFullScreen(false);
        stage.setHeight(300);
        stage.setWidth(400);
        stage.setResizable(false);
    }

    @FXML
    private void mitSalir(ActionEvent event) {
        MetodosEstaticos.SalirApp(stage);
    }

    @FXML
    private void mitPedido(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListarPedidos.fxml"));
        logic.setNodo(loader);
        ListarPedidosController controller = loader.getController();
        ((ListarPedidosController) sceneActiva).setViewControl(this);
        controller.init();
    }

    @FXML
    private void mitModPedido(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PedidosAM.fxml"));
        logic.setNodo(loader);
        PedidosAMController controller = loader.getController();
        ((PedidosAMController) sceneActiva).setViewControl(this);
        controller.init(logic.getPedido(), getBase().getChildren());
    }

    @FXML
    private void mitDash(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
        logic.setNodo(loader);
        DashboardController controller = loader.getController();
        ((DashboardController) sceneActiva).setViewControl(this);
        controller.init();
    }

    @FXML
    private void mitConfig(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Configuracion.fxml"));
        logic.setNodo(loader);
        ConfiguracionController controller = loader.getController();
        ((ConfiguracionController) sceneActiva).setViewControl(this);
        controller.init();
    }

    @FXML
    private void mitLocal(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Locales.fxml"));
        logic.setNodo(loader);
        LocalesController controller = loader.getController();
        ((LocalesController) sceneActiva).setViewControl(this);
        controller.init(getBase().getChildren());
    }

    @FXML
    private void mitEmpleado(ActionEvent event) {
        getBase().getChildren().remove(logic.getRoot());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Empleados.fxml"));
        logic.setNodo(loader);
        EmpleadosController controller = loader.getController();
        ((EmpleadosController) sceneActiva).setViewControl(this);
        controller.init(getBase().getChildren());
    }

    public MenuItem getmItModProd() {
        return mItModProd;
    }

    public Initializable getSceneActiva() {
        return sceneActiva;
    }

    public void setSceneActiva(Initializable sceneActiva) {
        this.sceneActiva = sceneActiva;
    }

    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public AnchorPane getBase() {
        return Base;
    }

    public MenuBar getBarra() {
        return Barra;
    }

    public LogicController getLogic() {
        return logic;
    }

    public void setLogic(LogicController logic) {
        this.logic = logic;
    }

}//fin de la clase
