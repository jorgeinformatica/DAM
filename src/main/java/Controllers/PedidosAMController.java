package Controllers;

import Beans.LineaPedido;
import Beans.Local;
import Beans.Pedido;
import Beans.Producto;
import BeansFX.LineaPedidoFX;
import BeansFX.LocalFX;
import BeansFX.PedidoFX;
import Utils.Constantes;
import Utils.MetodosEstaticos;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * @author Jorge Sempere Jimenez
 */
public class PedidosAMController implements Initializable {

    @FXML
    private TextField numPedido;
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
    private TableColumn estadoTC;
    @FXML
    private TableColumn accionesTC;
    @FXML
    private ChoiceBox<String> estadoCB;
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
    @FXML
    private TextField txtFechaPedido;

    private AAController viewControl;
    private PedidoFX pedido;
    private LocalFX local;
    private ObservableList<LineaPedidoFX> linPedido;
    private ObservableList<PedidoFX> listaPedido;
    private FilteredList<PedidoFX> filterPedido;
    private ObservableList<LocalFX> listaLocal;
    private FilteredList<LocalFX> filterLocal;
    private PedidosAMColumns tableController;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void init(PedidoFX ped, ObservableList<Node> base) {
        initValues();
        configurarComboLocal(ped);
        configurarComboPedido(ped);
        configurarComboEstado();
        configurarTxtFiltroLocal();
        configurarTxtFiltroPedido();
        configurarDPentrega();
        configurarBase(base);
    }

    private void initValues() {
        tableController = new PedidosAMColumns(this);
        infoFiltroLocal.setTooltip(new Tooltip("FILTRA LOS LOCALES EN BASE AL TEXTO INTRODUCIDO"));
        infoFiltroPedido.setTooltip(new Tooltip("FILTRA LOS PEDIDOS EN BASE AL TEXTO INTRODUCIDO"));
        linPedido = FXCollections.observableArrayList();
        listaLocal = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, "Estado=1")).forEach((Object lo) -> {
            listaLocal.add(new LocalFX((Local) lo));
        });
        filterLocal = new FilteredList<>(listaLocal, p -> true);
        cbLocales.setItems(filterLocal.sorted());
        listaPedido = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Pedido.class, "1=1")).forEach((Object ped) -> {
            listaPedido.add(new PedidoFX((Pedido) ped));
        });
        filterPedido = new FilteredList<>(listaPedido, p -> true);
        cbPedidos.setItems(filterPedido);
        tableController.doColumnLineasPedido(lineaTC);
        tableController.doColumnProductoPedido(productoTC);
        tableController.doColumnCantidadPedido(cantidadTC);
        tableController.doColumnEstadoPedido(estadoTC);
        tableController.doColumnActionsPedido(accionesTC);
    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    public AAController getViewControl() {
        return viewControl;
    }

    public PedidoFX getPedido() {
        return pedido;
    }

    public LocalFX getLocal() {
        return local;
    }

    public ObservableList<LineaPedidoFX> getLinPedido() {
        return linPedido;
    }

    private void actualizarPedido(PedidoFX p) {
        if (viewControl.getLogic().actualizarMsg(p)) {
            refrescarVista();
        }
    }

    public void refrescarVista() {
        if (pedido != null) {
            numPedido.setText(pedido.getNumPed() + "");
            txtFechaPedido.setText(new SimpleDateFormat("dd-MM-yyyy").format(pedido.getFechaPed()));
            fechaEntregaDP.setValue(MetodosEstaticos.ToLocalDate(pedido.getFechaEntrega()));
            estadoCB.getSelectionModel().select(pedido.getEstado());
            linPedido.clear();
            ((Pedido) pedido.getBean()).getLineaPedidos().forEach((o) -> {
                linPedido.add(new LineaPedidoFX(((LineaPedido) o)));
            });
            lineasTV.setItems(linPedido.sorted());
        }
    }

    @FXML
    private void nuevoPedido(ActionEvent event) {
        if (local != null) {
            viewControl.getLogic().getHibControl().initTransaction();
            Pedido tempo = new Pedido();
            tempo.setLocal((Local) local.getBean());
            tempo.setFechaPed(MetodosEstaticos.ToDate(LocalDate.now()));
            tempo.setFechaEntrega(MetodosEstaticos.ToDate(elegirFecha()));
            tempo.setEstado("INCOMPLETO");
            viewControl.getLogic().getHibControl().save(tempo);
            pedido = new PedidoFX(tempo);
            listaPedido.add(pedido);
            viewControl.getLogic().getHibControl().initTransaction();
            LineaPedido linea = new LineaPedido();
            linea.setPedido(tempo);
            linea.setProducto((Producto) viewControl.getLogic().getProductos().get(0).getBean());
            linea.setCantidad((short) 1);
            linea.setEstado("EN PRODUCCION");
            viewControl.getLogic().getHibControl().save(linea);
            viewControl.getLogic().getHibControl().refresco(tempo);
            viewControl.getLogic().getHibControl().refresco(local.getBean());
            refrescarVista();
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

    private void configurarComboPedido(PedidoFX elPed) {
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
        if (elPed == null) {
            cbPedidos.getSelectionModel().selectFirst();
        } else {
            cbPedidos.getSelectionModel().select(elPed);
            viewControl.getLogic().setPedido(null);
        }
    }

    private void configurarComboLocal(PedidoFX ped) {
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
        if (ped == null) {
            cbLocales.getSelectionModel().selectFirst();
        } else {
            cbLocales.getSelectionModel().select(ped.getLocal());
        }
    }

    private void configurarDPentrega() {
        fechaEntregaDP.setOnAction((ActionEvent event) -> {
            PedidoFX tempo = comprobarFechaPed(MetodosEstaticos.ToDate(fechaEntregaDP.getValue()));
            if (tempo == null) {
                pedido.setFechaEntrega(MetodosEstaticos.ToDate(fechaEntregaDP.getValue()));
            } else {
                if (confirmarCambio()) {
                    for (Object linPed : ((Pedido) pedido.getBean()).getLineaPedidos()) {
                        viewControl.getLogic().getHibControl().initTransaction();
                        ((LineaPedido) linPed).setPedido((Pedido) tempo.getBean());
                        viewControl.getLogic().getHibControl().UpdateElement(linPed);
                    }
                    viewControl.getLogic().getHibControl().initTransaction();
                    viewControl.getLogic().getHibControl().remove(pedido.getBean());
                    listaPedido.remove(pedido);
                    viewControl.getLogic().getHibControl().refresco(tempo.getBean());
                    pedido = tempo;
                    listaPedido.add(pedido);
                    cbPedidos.getSelectionModel().select(pedido);
                } else {
                    fechaEntregaDP.setValue(MetodosEstaticos.ToLocalDate(pedido.getFechaEntrega()));
                }
            }
        });
    }

    private void configurarComboEstado() {
        estadoCB.setItems(FXCollections.observableArrayList(
                Constantes.EstadoPedido.TERMINADO.getNom(),
                Constantes.EstadoPedido.INCOMPLETO.getNom(),
                Constantes.EstadoPedido.ENTREGADO.getNom(),
                Constantes.EstadoPedido.ANULADO.getNom()));
        estadoCB.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (pedido != null && !nV) {
                if (estadoCB.getValue().equals(Constantes.EstadoPedido.TERMINADO.getNom())) {
                    pedido.setEstado(estadoCB.getValue());
                    lineasSetEstado(Constantes.EstadoLinea.PREPARADO.getNom());
                } else if (estadoCB.getValue().equals(Constantes.EstadoPedido.ANULADO.getNom())) {
                    pedido.setEstado(estadoCB.getValue());
                    lineasSetEstado(Constantes.EstadoLinea.ANULADO.getNom());
                } else if (estadoCB.getValue().equals(Constantes.EstadoPedido.INCOMPLETO.getNom())) {
                    pedido.setEstado(estadoCB.getValue());
                    lineasSetEstado(Constantes.EstadoLinea.PRODUCCION.getNom());
                } else if (estadoCB.getValue().equals(Constantes.EstadoPedido.ENTREGADO.getNom())) {
                    pedido.setEstado(estadoCB.getValue());
                    lineasSetEstado(Constantes.EstadoLinea.ENTREGADO.getNom());
                }
            }
        });
    }

    private boolean recorrerPedidos(PedidoFX ped, Set lista) {
        return lista.stream().anyMatch((o) -> Objects.equals(ped.getNumPed(), ((Pedido) o).getNumPed()));
    }

    private void configurarBase(ObservableList<Node> base) {
        base.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                actualizarPedido(pedido);
                base.removeListener(this);
            }
        });
    }

    private PedidoFX comprobarFechaPed(Date value) {
        for (PedidoFX pedFX : listaPedido) {
            if (Objects.equals(pedFX.getLocal().getCodLocal(), pedido.getLocal().getCodLocal())) {
                String init = new SimpleDateFormat("dd-MM-yyyy").format(value);
                String end = new SimpleDateFormat("dd-MM-yyyy").format(pedFX.getFechaEntrega());
                if (init.equals(end) && !Objects.equals(pedido.getNumPed(), pedFX.getNumPed())) {
                    return pedFX;
                }
            }
        }
        return null;
    }

    private boolean confirmarCambio(String nom) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText("Va a cambiar el estado del pedido y de sus lineas al estado de: " + nom + "." + System.lineSeparator()
                + "¿Está seguro?");
        alert.setContentText(null);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private boolean confirmarCambio() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText("En la fecha especificada ya tiene un pedido." + System.lineSeparator()
                + "Si realmente desea cambiar la fecha de entrega, " + System.lineSeparator()
                + "se fusionarán todas las lineas a dicha entrega." + System.lineSeparator()
                + "¿Está seguro?");
        alert.setContentText(null);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void lineasSetEstado(String nom) {
        if (confirmarCambio(nom)) {
            for (Object o : ((Pedido) pedido.getBean()).getLineaPedidos()) {
                viewControl.getLogic().getHibControl().initTransaction();
                ((LineaPedido) o).setEstado(nom);
                viewControl.getLogic().getHibControl().UpdateElement(o);
            }
            viewControl.getLogic().getHibControl().refresco(pedido.getBean());
            lineasTV.refresh();
        } else {
            pedido.setEstado(((Pedido) pedido.getBean()).getEstado());
            estadoCB.getSelectionModel().select(pedido.getEstado());
        }
    }

    private LocalDate elegirFecha() {
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle(null);
        dialog.setGraphic(null);
        dialog.setContentText(null);
        dialog.setHeaderText("¿Para cuando quieres el pedido?");
        ButtonType btnOk = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnOk);
        DatePicker dp = new DatePicker(LocalDate.now().plusDays(1));
        dialog.getDialogPane().setContent(dp);
        dialog.setResultConverter(dialogBtn -> {
            return dp.getValue();
        });
        return dialog.showAndWait().get();
    }

}//fin de clase
