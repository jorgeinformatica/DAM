package Controllers;

import Beans.CiudadConcp;
import Beans.Direccion;
import Beans.Local;
import BeansFX.CiudadFX;
import BeansFX.CodigoPostalFX;
import BeansFX.LocalFX;
import BeansFX.ProvinciaFX;
import Utils.Constantes;
import Utils.MetodosEstaticos;
import dam.proyecto.LogicController;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
    private LineChart<?, ?> lineasEvolutivo;
    @FXML
    private NumberAxis evoPreAx;
    @FXML
    private CategoryAxis evoDiaAx;
    @FXML
    private BarChart<?, ?> barrasComparativo;
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
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, Constantes.HQLCondicion.ESTADO.getSentencia())).forEach((Object lo) -> {
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
        CiudadConcp ccCP = (CiudadConcp) viewControl.getLogic().getHibControl().searchElement(CiudadConcp.class, Constantes.HQLCondicion.CENTRALREF.getSentencia());
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

    private void configurarTxtNombre() {
        txtNombre.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtNombre, 39));
        txtNombre.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && !nV) {
                if (!txtNombre.getText().toUpperCase().equalsIgnoreCase(local.getNombre())) {
                    local.setNombre(txtNombre.getText().toUpperCase());
                }
            }
            if (txtNombre.getText().isEmpty()) {
                txtNombre.requestFocus();
            }
        });
    }
    
    private void aceptarCambios() {
        if (!btnAceptarCambio.isVisible() && local.comprobarCambios()) {
            viewControl.getLogic().aceptarCambiosBtn(btnAceptarCambio, local);
        }
    }
    
}//fin de la clase
