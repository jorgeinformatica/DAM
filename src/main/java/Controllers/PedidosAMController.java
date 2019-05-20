package Controllers;

import Beans.LineaPedido;
import Beans.Local;
import Beans.Pedido;
import BeansFX.LineaPedidoFX;
import BeansFX.LocalFX;
import BeansFX.PedidoFX;
import Utils.Columns;
import Utils.MetodosEstaticos;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
    private TableColumn lineaTC;
    @FXML
    private TableColumn productoTC;
    @FXML
    private TableColumn cantidadTC;
    @FXML
    private TableColumn<LineaPedidoFX, String> estadoTC;
    @FXML
    private TableColumn accionesTC;
    @FXML
    private ChoiceBox<String> estadoCB;
    @FXML
    private Button nuevoBTN;
    @FXML
    private ComboBox<LocalFX> cbLocales;
    @FXML
    private TextField txtFiltroLocales;
    @FXML
    private ComboBox<PedidoFX> cbPedidos;
    @FXML
    private TextField txtFiltroPedidos;
    @FXML
    private Label infoFiltroLocal;
    @FXML
    private Label infoFiltroPedido;

    private AAController viewControl;
    private PedidoFX pedido;
    private LocalFX local;
    private ObservableList<LineaPedidoFX> linPedido;
    private ObservableList<PedidoFX> listaPedido;
    private FilteredList<PedidoFX> filterPedido;
    private ObservableList<LocalFX> listaLocal;
    private FilteredList<LocalFX> filterLocal;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void init(ObservableList<Node> base) {
        initValues();
        configurarComboLocal();
        configurarComboPedido();
        configurarComboEstado();
        configurarTxtFiltroLocal();
        configurarTxtFiltroPedido();
        configurarDPpedido();
        configurarDPentrega();
        configurarBase(base);
    }

    private void initValues() {
        infoFiltroLocal.setTooltip(new Tooltip("FILTRA LOS LOCALES EN BASE AL TEXTO INTRODUCIDO"));
        infoFiltroPedido.setTooltip(new Tooltip("FILTRA LOS PEDIDOS EN BASE AL TEXTO INTRODUCIDO"));
        linPedido = FXCollections.observableArrayList();
        listaLocal = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, "1=1")).forEach((Object lo) -> {
            listaLocal.add(new LocalFX((Local) lo));
        });
        filterLocal = new FilteredList<>(listaLocal, p -> true);
        cbLocales.setItems(filterLocal.sorted());
        listaPedido = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Pedido.class, "1=1")).forEach((Object ped) -> {
            listaPedido.add(new PedidoFX((Pedido) ped));
        });
        filterPedido = new FilteredList<>(listaPedido, p -> true);
        cbPedidos.setItems(filterPedido.sorted());
        Columns.doColumnLineasPedido(lineaTC);
        Columns.doColumnProductoPedido(productoTC, viewControl);
        Columns.doColumnCantidadPedido(cantidadTC);
        estadoTC.setCellValueFactory(linea -> linea.getValue().estadoProperty());
        Columns.doColumnActionsPedido(accionesTC);
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
        if (pedido != null) {
            numPedido.setText(pedido.getNumPed() + "");
            fechaPedidoDP.setValue(MetodosEstaticos.ToLocalDate(pedido.getFechaPed()));
            fechaEntregaDP.setValue(MetodosEstaticos.ToLocalDate(pedido.getFechaEntrega()));
            estadoCB.getSelectionModel().select(pedido.getEstado());
            linPedido.clear();
            pedido.getLineasPedido().forEach((o) -> {
                linPedido.add(new LineaPedidoFX(((LineaPedido) o)));
            });
            lineasTV.setItems(linPedido);
        }
    }

    @FXML
    private void nuevoPedido(ActionEvent event) {
        if (local != null) {
            viewControl.getLogic().getHibControl().initTransaction();
            Pedido tempo = new Pedido();
            tempo.setLocal((Local) local.getBean());
            tempo.setFechaPed(MetodosEstaticos.ToDate(LocalDate.now()));
            tempo.setFechaEntrega(MetodosEstaticos.ToDate(LocalDate.now().plusDays(1)));
            tempo.setEstado("INCOMPLETO");
            viewControl.getLogic().getHibControl().save(tempo);
            pedido = new PedidoFX(tempo);
            listaPedido.add(pedido);
            cbPedidos.getSelectionModel().select(pedido);
        } else {
            Alert aviso = new Alert(Alert.AlertType.INFORMATION, "Por favor, seleccione un local.", ButtonType.OK);
            aviso.show();
        }
    }

    private void configurarTxtFiltroLocal() {
        txtFiltroLocales.textProperty().addListener((obs, oldValue, newValue) -> {
            LocalFX selected = cbLocales.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.getDireccion().getNombre().toUpperCase().equals(newValue.toUpperCase())) {
                    filterLocal.setPredicate(item -> {
                        return ((LocalFX) item).getDireccion().getNombre().toUpperCase().contains(newValue.toUpperCase());
                    });
                }
            });
        });
    }

    private void configurarTxtFiltroPedido() {
        txtFiltroPedidos.textProperty().addListener((obs, oldValue, newValue) -> {
            PedidoFX selected = cbPedidos.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.getFechaPed().toString().equals(newValue)) {
                    filterPedido.setPredicate(item -> {
                        return ((PedidoFX) item).getFechaPed().toString().contains(newValue);
                    });
                }
            });
        });
    }

    private void configurarComboPedido() {
        cbPedidos.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && nV) {
                filterPedido.setPredicate(ped -> {
                    return recorrerPedidos(ped, local.getPedidos());
                });
            }
        });
        cbPedidos.valueProperty().addListener(
                (ObservableValue<? extends PedidoFX> lo, PedidoFX oV, PedidoFX nV) -> {
                    if (nV != null) {
                        pedido = nV;
                        refrescarVista();
                        if (oV != null) {
                            actualizarPedido(pedido);
                        }
                    }
                });
        cbPedidos.getSelectionModel().selectFirst();
    }

    private void configurarComboLocal() {
        cbLocales.valueProperty().addListener(
                (ObservableValue<? extends LocalFX> lo, LocalFX oV, LocalFX nV) -> {
                    if (nV != null) {
                        local = nV;
                        refrescarVista();
                        if (oV != null) {
                            actualizarPedido(pedido);
                        }
                    }
                });
        cbLocales.getSelectionModel().selectFirst();
    }

    private void configurarDPpedido() {
        fechaPedidoDP.setOnAction((ActionEvent event) -> {
            pedido.setFechaPed(MetodosEstaticos.ToDate(fechaPedidoDP.getValue()));
        });
    }

    private void configurarDPentrega() {
        fechaEntregaDP.setOnAction((ActionEvent event) -> {
            pedido.setFechaEntrega(MetodosEstaticos.ToDate(fechaEntregaDP.getValue()));
        });
    }

    private void configurarComboEstado() {
        ObservableList estados = FXCollections.observableArrayList("INCOMPLETO", "TERMINADO", "ANULADO");
        estadoCB.setItems(estados);
        estadoCB.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (pedido != null && !nV) {
                pedido.setEstado(estadoCB.getValue());
            }
        });
    }

    private boolean recorrerPedidos(PedidoFX ped, Set lista) {
        return lista.stream().anyMatch((o) -> Objects.equals(ped.getNumPed(), ((Pedido) o).getNumPed()));
    }

    private void configurarBase(ObservableList<Node> base) {
        ListChangeListener<Node> changeList = (ListChangeListener.Change<? extends Node> c) -> {
            actualizarPedido(pedido);
        };
        base.addListener(changeList);
    }

}//fin de clase
