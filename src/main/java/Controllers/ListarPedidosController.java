package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
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
    private TableView<?> pedidosTV;
    @FXML
    private TableColumn<?, ?> pedidoTC;
    @FXML
    private TableColumn<?, ?> fechaPedidoTC;
    @FXML
    private TableColumn<?, ?> fechaEntregaTC;
    @FXML
    private TableColumn<?, ?> accionesPedidosTC;
    @FXML
    private TableColumn<?, ?> localTC;
    @FXML
    private TableColumn<?, ?> estadoPedidoTC;
    @FXML
    private TableView<?> lineasTV;
    @FXML
    private TableColumn<?, ?> accionesLineasTC;
    @FXML
    private TableColumn<?, ?> lineaTC;
    @FXML
    private TableColumn<?, ?> productoTC;
    @FXML
    private TableColumn<?, ?> cantidadTC;
    @FXML
    private TableColumn<?, ?> estadoLineaTC;

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
    }

}
