package Controllers;

import BeansFX.LineaPedidoFX;
import BeansFX.LocalFX;
import BeansFX.PedidoFX;
import BeansFX.ProductoFX;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Jorge Sempere
 */
public class ListarPedidosController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<PedidoFX> pedidosTV;
    @FXML
    private TableColumn<PedidoFX, Long> pedidoTC;
    @FXML
    private TableColumn<PedidoFX, Date> fechaPedidoTC;
    @FXML
    private TableColumn<PedidoFX, Date> fechaEntregaTC;
    @FXML
    private TableColumn accionesPedidosTC;
    @FXML
    private TableColumn<PedidoFX, LocalFX> localTC;
    @FXML
    private TableColumn<PedidoFX, String> estadoPedidoTC;
    @FXML
    private TableView<LineaPedidoFX> lineasTV;
    @FXML
    private TableColumn accionesLineasTC;
    @FXML
    private TableColumn lineaTC;
    @FXML
    private TableColumn<LineaPedidoFX, ProductoFX> productoTC;
    @FXML
    private TableColumn<LineaPedidoFX, Short> cantidadTC;
    @FXML
    private TableColumn estadoLineaTC;

    private AAController viewControl;
    private ObservableList<LineaPedidoFX> listaLineas;
    private FilteredList<LineaPedidoFX> filterLineas;
    private ObservableList<PedidoFX> listaPedidos;
    private FilteredList<PedidoFX> filterPedidos;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void init() {

    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

}
