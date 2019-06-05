package Controllers;

import Beans.CiudadConcp;
import Beans.Direccion;
import Beans.LineaPedido;
import Beans.Local;
import Beans.Pedido;
import BeansFX.CiudadFX;
import BeansFX.CodigoPostalFX;
import BeansFX.LocalFX;
import BeansFX.PedidoFX;
import BeansFX.ProvinciaFX;
import Utils.Constantes;
import Utils.MetodosEstaticos;
import dam.proyecto.LogicController;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

/**
 * @author Jorge Sempere
 */
public class LocalesController implements Initializable {

    @FXML
    private TextField txtCalle;
    @FXML
    private TextField txtNum;
    @FXML
    private ComboBox<CiudadFX> cbCiudad;
    @FXML
    private ComboBox<CodigoPostalFX> cbCP;
    @FXML
    private ComboBox<ProvinciaFX> cbProv;
    @FXML
    private ComboBox<LocalFX> cbLocales;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Label infoFiltro;
    @FXML
    private PieChart tartaPorcentaje;
    @FXML
    private LineChart<BigDecimal, String> lineasEvolutivo;
    @FXML
    private NumberAxis evoPreAx;
    @FXML
    private CategoryAxis evoDiaAx;
    @FXML
    private BarChart<BigDecimal, String> barrasComparativo;
    @FXML
    private NumberAxis comPreAx;
    @FXML
    private CategoryAxis comLocAx;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnAceptarCambio;

    private LocalFX local;
    private AAController viewControl;
    private FilteredList<CiudadFX> filterCiudad;
    private FilteredList<CodigoPostalFX> filterCP;
    private FilteredList<LocalFX> filterLocal;
    private ObservableList<LocalFX> listaLocal;
    private ObservableList<PedidoFX> listaPedidos;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAceptarCambio.setVisible(false);
    }

    public void init(ObservableList<Node> base) {
        initValues();
        configurarComboLocal();
        configurarComboProv();
        configurarComboCiudad();
        configurarComboCP();
        configurarTxtCalle();
        configurarTxtNum();
        configurarTxtNombre();
        configurarTxtFiltro();
        configurarGraficos();
        configurarBase(base);
    }

    private void initValues() {
        infoFiltro.setTooltip(new Tooltip("FILTRA LOS LOCALES EN BASE AL TEXTO INTRODUCIDO"));
        listaLocal = FXCollections.observableArrayList();
        listaPedidos = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Pedido.class, Constantes.HQLCondicion.NEUTRO.getCondicion())).forEach((Object ped) -> {
            listaPedidos.add(new PedidoFX((Pedido) ped));
        });
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, Constantes.HQLCondicion.ESTADO.getCondicion())).forEach((Object lo) -> {
            listaLocal.add(new LocalFX((Local) lo));
        });
        filterLocal = new FilteredList<>(listaLocal, p -> true);
        cbLocales.setItems(filterLocal.sorted());
        cbProv.setItems(viewControl.getLogic().getProvincias().sorted());
        filterCiudad = new FilteredList<>(viewControl.getLogic().getCiudades(), p -> true);
        cbCiudad.setItems(filterCiudad.sorted());
        filterCP = new FilteredList<>(viewControl.getLogic().getCps(), p -> true);
        cbCP.setItems(filterCP.sorted());
    }

    public void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    @FXML
    private void nuevoLocal(ActionEvent event) {
        viewControl.getLogic().getHibControl().initTransaction();
        CiudadConcp ccCP = (CiudadConcp) viewControl.getLogic().getHibControl().searchElement(CiudadConcp.class, Constantes.HQLCondicion.CENTRALREF.getCondicion());
        Direccion direc = new Direccion();
        direc.setNumero((short) 1);
        direc.setNombre("CALLE");
        direc.setCiudadConcp(ccCP);
        viewControl.getLogic().getHibControl().save(direc);
        viewControl.getLogic().getHibControl().initTransaction();
        Local tempo = new Local(direc, true, "-");
        viewControl.getLogic().getHibControl().save(tempo);
        local = new LocalFX(tempo);
        listaLocal.add(local);
        refrescarVista();
    }

    @FXML
    private void desactivarLocal(ActionEvent event) {
        if (local != null) {
            if (viewControl.getLogic().desactivarMsg(local)) {
                listaLocal.remove(local);
            }
        }
    }

    private void configurarComboProv() {
        cbProv.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && !nV) {
                if (!Objects.equals(cbProv.getValue().getCodProvincia(), local.getDireccion().getRelCpCiu().getProvincia().getCodProvincia())) {
                    local.getDireccion().getRelCpCiu().setProvincia(cbProv.getValue());
                    local.getDireccion().setRelCpCiu(LogicController.getCCFX(local.getDireccion().getRelCpCiu().getCiudad(), local.getDireccion().getRelCpCiu().getCodigoPostal())
                            == null ? local.getDireccion().getRelCpCiu()
                                    : LogicController.getCCFX(local.getDireccion().getRelCpCiu().getCiudad(), local.getDireccion().getRelCpCiu().getCodigoPostal()));
                }
            }
        });
    }

    private void configurarComboCiudad() {
        cbCiudad.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && nV) {
                filterCiudad.setPredicate(ciu -> {
                    return recorrerCCP(ciu, local.getDireccion().getRelCpCiu().getProvincia());
                });
            }
            if (local != null && !nV) {
                if (!Objects.equals(cbCiudad.getValue().getCodCiudad(), local.getDireccion().getRelCpCiu().getCiudad().getCodCiudad())) {
                    local.getDireccion().getRelCpCiu().setCiudad(cbCiudad.getValue());
                    local.getDireccion().setRelCpCiu(LogicController.getCCFX(local.getDireccion().getRelCpCiu().getCiudad(), local.getDireccion().getRelCpCiu().getCodigoPostal())
                            == null ? local.getDireccion().getRelCpCiu()
                                    : LogicController.getCCFX(local.getDireccion().getRelCpCiu().getCiudad(), local.getDireccion().getRelCpCiu().getCodigoPostal()));
                }
            }
        });
    }

    private void configurarComboCP() {
        cbCP.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && nV) {
                filterCP.setPredicate(cp -> {
                    return recorrerCCP(cp, local.getDireccion().getRelCpCiu().getCiudad());
                });
            }
            if (local != null && !nV) {
                if (!cbCP.getValue().getCodPostal().equalsIgnoreCase(local.getDireccion().getRelCpCiu().getCodigoPostal().getCodPostal())) {
                    local.getDireccion().getRelCpCiu().setCodigoPostal(cbCP.getValue());
                    local.getDireccion().setRelCpCiu(LogicController.getCCFX(local.getDireccion().getRelCpCiu().getCiudad(), local.getDireccion().getRelCpCiu().getCodigoPostal())
                            == null ? local.getDireccion().getRelCpCiu()
                                    : LogicController.getCCFX(local.getDireccion().getRelCpCiu().getCiudad(), local.getDireccion().getRelCpCiu().getCodigoPostal()));
                }
            }
        });
    }

    private void configurarComboLocal() {
        cbLocales.valueProperty().addListener(
                (ObservableValue<? extends LocalFX> lo, LocalFX oV, LocalFX nV) -> {
                    if (nV != null) {
                        local = nV;
                        refrescarVista();
                        if (oV != null) {
                            actualizarLocal(oV);
                        }
                    }
                });
        cbLocales.getSelectionModel().selectFirst();
    }

    private void configurarTxtNum() {
        txtNum.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtNum, 3));
        txtNum.setTextFormatter(MetodosEstaticos.soloNumeros());
        txtNum.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && !nV) {
                if (!Objects.equals(Short.valueOf(txtNum.getText()), local.getDireccion().getNumero())) {
                    local.getDireccion().setNumero(Short.valueOf(txtNum.getText()));
                    aceptarCambios();
                }
            }
            if (txtNum.getText().isEmpty()) {
                txtNum.requestFocus();
            }
        });
    }

    private void configurarTxtNombre() {
        txtNombre.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtNombre, 39));
        txtNombre.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && !nV) {
                if (!txtNombre.getText().toUpperCase().equalsIgnoreCase(local.getNombre())) {
                    local.setNombre(txtNombre.getText().toUpperCase());
                    aceptarCambios();
                }
            }
            if (txtNombre.getText().isEmpty()) {
                txtNombre.requestFocus();
            }
        });
    }

    private void configurarTxtCalle() {
        txtCalle.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtCalle, 74));
        txtCalle.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && !nV) {
                if (!txtCalle.getText().toUpperCase().equalsIgnoreCase(local.getDireccion().getNombre())) {
                    local.getDireccion().setNombre(txtCalle.getText().toUpperCase());
                    aceptarCambios();
                }
            }
            if (txtCalle.getText().isEmpty()) {
                txtCalle.requestFocus();
            }
        });
    }

    private void configurarTxtFiltro() {
        txtFiltro.textProperty().addListener((obs, oldValue, newValue) -> {
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

    private void configurarGraficos() {
        Font font = new Font("Arial", 12);
        comLocAx.setTickLabelFont(font);
        comPreAx.setTickLabelFont(font);
        evoDiaAx.setTickLabelFont(font);
        evoPreAx.setTickLabelFont(font);
        comLocAx.setTickLabelRotation(-45);
        evoDiaAx.setTickLabelRotation(-45);
        tartaPorcentaje.setStartAngle(110);
        configurarEvolutivo();
        configurarBarrasLocales();
        configurarTarta();
    }

    private void refrescarVista() {
        txtNombre.setText(local.getNombre());
        txtCalle.setText(local.getDireccion().getNombre());
        txtNum.setText(local.getDireccion().getNumero() + "");
        cbProv.getSelectionModel().select(local.getDireccion().getRelCpCiu().getProvincia());
        filterCiudad.setPredicate(p -> true);
        cbCiudad.getSelectionModel().select(local.getDireccion().getRelCpCiu().getCiudad());
        filterCP.setPredicate(p -> true);
        cbCP.getSelectionModel().select(local.getDireccion().getRelCpCiu().getCodigoPostal());
        configurarTarta();
        configurarEvolutivo();
    }

    private void actualizarLocal(LocalFX lo) {
        if (viewControl.getLogic().actualizarMsg(lo)) {
            refrescarVista();
        }
    }

    private boolean recorrerCCP(CodigoPostalFX cpFX, CiudadFX nV) {
        return nV.getCiudadConcp().stream().anyMatch((o) -> (Objects.equals(((CiudadConcp) o).getCodigoPostal().getCodPostal(), cpFX.getCodPostal())));
    }

    private boolean recorrerCCP(CiudadFX cFX, ProvinciaFX nV) {
        return nV.getCiudadConcp().stream().anyMatch((o) -> (Objects.equals(((CiudadConcp) o).getCiudad().getCodCiudad(), cFX.getCodCiudad())));
    }

    private void configurarBase(ObservableList<Node> base) {
        base.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                actualizarLocal(local);
                base.removeListener(this);
            }
        });
    }

    private void aceptarCambios() {
        if (!btnAceptarCambio.isVisible() && local.comprobarCambios()) {
            viewControl.getLogic().aceptarCambiosBtn(btnAceptarCambio, local);
        }
    }

    private void configurarEvolutivo() {
        lineasEvolutivo.getData().clear();
        XYChart.Series series = new XYChart.Series();
        for (PedidoFX pedFX : listaPedidos) {
            BigDecimal valor = BigDecimal.ZERO;
            if (Objects.equals(pedFX.getLocal().getCodLocal(), local.getCodLocal())) {
                for (Object obj : pedFX.getLineasPedido()) {
                    LineaPedido lin = (LineaPedido) obj;
                    valor = valor.add(lin.getProducto().getPrecio().multiply(new BigDecimal((int) lin.getCantidad())));
                }
                series.getData().add(new XYChart.Data<>(new SimpleDateFormat("dd-MM-yyyy").format(pedFX.getFechaEntrega()), valor));
            }
        }
        lineasEvolutivo.getData().add(series);
        toolTipBar(lineasEvolutivo.getData());
    }

    private void configurarBarrasLocales() {
        XYChart.Series data = new XYChart.Series();
        for (LocalFX locFX : listaLocal) {
            BigDecimal valor = BigDecimal.ZERO;
            for (PedidoFX pedFX : listaPedidos) {
                if (Objects.equals(pedFX.getLocal().getCodLocal(), locFX.getCodLocal())) {
                    for (Object obj : pedFX.getLineasPedido()) {
                        LineaPedido lin = (LineaPedido) obj;
                        valor = valor.add(lin.getProducto().getPrecio().multiply(new BigDecimal((int) lin.getCantidad())));
                    }
                }
            }
            data.getData().add(new XYChart.Data<>(locFX.getNombre(), valor));
        }
        barrasComparativo.getData().add(data);
        toolTipBar(barrasComparativo.getData());
    }

    private void configurarTarta() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal valor = BigDecimal.ZERO;
        for (PedidoFX pedFX : listaPedidos) {
            if (Objects.equals(pedFX.getLocal().getCodLocal(), local.getCodLocal())) {
                for (Object obj : pedFX.getLineasPedido()) {
                    LineaPedido lin = (LineaPedido) obj;
                    valor = valor.add(lin.getProducto().getPrecio().multiply(new BigDecimal((int) lin.getCantidad())));
                }
            } else {
                for (Object obj : pedFX.getLineasPedido()) {
                    LineaPedido lin = (LineaPedido) obj;
                    subTotal = subTotal.add(lin.getProducto().getPrecio().multiply(new BigDecimal((int) lin.getCantidad())));
                }
            }
        }
        data.add(new PieChart.Data(local.getNombre(), valor.doubleValue()));
        data.add(new PieChart.Data("RESTO", subTotal.doubleValue()));
        tartaPorcentaje.setData(data);
        BigDecimal total = subTotal.add(valor);
        tartaPorcentaje.getData().forEach((t) -> {
            toolTipPie(t, total);
        });
    }

    private void toolTipPie(PieChart.Data t, BigDecimal valor) {
        Tooltip ttPie = new Tooltip("Valor: " + Math.round(t.getPieValue()) + " €"
                + System.lineSeparator() + "Porcentaje: " + Math.round((100 * t.getPieValue()) / valor.doubleValue()) + "%");
        ttPie.setStyle("-fx-font: 12 arial;-fx-background-color: black; -fx-text-fill: whitesmoke;");
        Tooltip.install(t.getNode(), ttPie);
    }

    private void toolTipBar(ObservableList<XYChart.Series<BigDecimal, String>> datos) {
        datos.forEach((series) -> {
            series.getData().forEach((data) -> {
                Tooltip ttBar = new Tooltip();
                ttBar.setText("Valor: " + String.valueOf(data.getYValue()) + "€");
                ttBar.setStyle("-fx-font: 12 arial;-fx-background-color: black; -fx-text-fill: whitesmoke;");
                Tooltip.install(data.getNode(), ttBar);
            });
        });
    }

}//fin de la clase
