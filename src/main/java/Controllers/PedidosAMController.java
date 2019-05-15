package Controllers;

import Beans.LineaPedidoId;
import Beans.Producto;
import BeansFX.LineaPedidoFX;
import BeansFX.LocalFX;
import BeansFX.PedidoFX;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private TableView<LineaPedidoFX> lineasTV;
    @FXML
    private TableColumn<LineaPedidoFX, LineaPedidoId> lineaTC;
    @FXML
    private TableColumn<LineaPedidoFX, Producto> productoTC;
    @FXML
    private TableColumn<LineaPedidoFX, Short> cantidadTC;
    @FXML
    private TableColumn<LineaPedidoFX, String> estadoTC;
    @FXML
    private TableColumn accionesTC;
    @FXML
    private ChoiceBox<String> estadoCB;
    @FXML
    private ComboBox<PedidoFX> cbBuscar;
    @FXML
    private ComboBox<LocalFX> cbElementos;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Label infoFiltro;

    private AAController viewControl;
    private PedidoFX pedido;
     private ObservableList<PedidoFX> listaPedidos;
    @FXML
    private Button nuevoBTN;
    @FXML
    private Button borrarBTN;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void init(ObservableList<Node> base) {

        configurarBase(base);
    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }


    private void actualizarPedido(PedidoFX p) {
        if (viewControl.getLogic().actualizarMsg(p)) {
            refrescarVista();
        }
    }

    private void refrescarVista() {

    }

    private void configurarBase(ObservableList<Node> base) {
        ListChangeListener<Node> changeList = (ListChangeListener.Change<? extends Node> c) -> {
            actualizarPedido(pedido);
        };
        base.addListener(changeList);
    }

    @FXML
    private void nuevoPedido(ActionEvent event) {
    }

    @FXML
    private void borrarPedido(ActionEvent event) {
                if (pedido != null) {
            if (viewControl.getLogic().desactivarMsg(pedido)) {
                listaPedidos.remove(pedido);
            }
        }
    }
}
