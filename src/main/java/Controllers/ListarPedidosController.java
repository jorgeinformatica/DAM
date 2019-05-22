package Controllers;

import Beans.LineaPedido;
import Beans.Pedido;
import BeansFX.LineaPedidoFX;
import BeansFX.LocalFX;
import BeansFX.PedidoFX;
import BeansFX.ProductoFX;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

/**
 * @author Jorge Sempere Jimenez
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
    private ObservableList<PedidoFX> listaPedidos;
    private FilteredList<PedidoFX> filterPedidos;
    private ListarPedidosColumns tableController;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void init() {
        initValues();
        configurarPedidosTV();
        configurarTxtBuscar();
    }

    private void initValues() {
        tableController = new ListarPedidosColumns(this);
        listaLineas = FXCollections.observableArrayList();
        listaPedidos = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Pedido.class, "1=1")).forEach((Object ped) -> {
            listaPedidos.add(new PedidoFX((Pedido) ped));
        });
        filterPedidos = new FilteredList<>(listaPedidos, p -> true);
        pedidoTC.setCellValueFactory(pedido -> pedido.getValue().numPedProperty());
        fechaPedidoTC.setCellValueFactory(pedido -> pedido.getValue().fechaPedProperty());
        fechaEntregaTC.setCellValueFactory(pedido -> pedido.getValue().fechaEntregaProperty());
        localTC.setCellValueFactory(pedido -> pedido.getValue().localProperty());
        estadoPedidoTC.setCellValueFactory(pedido -> pedido.getValue().estadoProperty());
        productoTC.setCellValueFactory(linea -> linea.getValue().productoProperty());
        cantidadTC.setCellValueFactory(linea -> linea.getValue().cantidadProperty());
        tableController.doColumnLineasPedido(lineaTC);
        tableController.doColumnEstadoPedido(estadoLineaTC);
        tableController.doColumnActionPedido(accionesPedidosTC);
        tableController.doColumnActionsPedido(accionesLineasTC);
        pedidosTV.getItems().addAll(filterPedidos);
        lineasTV.getItems().addAll(listaLineas);
    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    public AAController getViewControl() {
        return viewControl;
    }

    public ObservableList<LineaPedidoFX> getListaLineas() {
        return listaLineas;
    }

    public FilteredList<PedidoFX> getFilterPedidos() {
        return filterPedidos;
    }

    private void configurarTxtBuscar() {
        txtBuscar.textProperty().addListener((oble, oV, nV) -> {
            filterPedidos.setPredicate(p -> {
                if (nV == null || nV.isEmpty()) {
                    return true;
                }
                String letras = nV.toLowerCase();
                return p.toString().toLowerCase().contains(letras);
            });
        });
    }

    private void configurarPedidosTV() {
        pedidosTV.setRowFactory(ptv -> {
            TableRow<PedidoFX> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Set lineasPed = row.getItem().getLineasPedido();
                    lineasTV.getItems().clear();
                    lineasPed.forEach((linea) -> {
                        lineasTV.getItems().add(new LineaPedidoFX((LineaPedido) linea));
                    });
                }
            });
            return row;
        });
    }

}//fin de clase
