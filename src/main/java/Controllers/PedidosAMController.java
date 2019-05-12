package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @author Jorge Sempere
 */
public class PedidosAMController implements Initializable {

    @FXML
    private TextField numPedido;
    @FXML
    private DatePicker fechaPedidoDP;
    @FXML
    private DatePicker fechaEntregaDP;
    @FXML
    private TableView<?> lineasTV;
    @FXML
    private TableColumn<?, ?> lineaTC;
    @FXML
    private TableColumn<?, ?> productoTC;
    @FXML
    private TableColumn<?, ?> cantidadTC;
    @FXML
    private TableColumn<?, ?> estadoTC;
    @FXML
    private TableColumn<?, ?> accionesTC;
    @FXML
    private ChoiceBox<?> estadoCB;
    @FXML
    private ComboBox<?> cbBuscar;
    @FXML
    private Button nuevoBTN1;
    @FXML
    private Button borrarBTN1;
    @FXML
    private ComboBox<?> cbElementos;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Label infoFiltro;
    
/**
 * @param url
 * @param rb 
 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void init() {

    }

    void setViewControl(AAController aThis) {

    }

    @FXML
    private void nuevoProducto(ActionEvent event) {
    }

    @FXML
    private void borrarProducto(ActionEvent event) {
    }

}
