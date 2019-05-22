package Controllers;

import Beans.CiudadConcp;
import Beans.Direccion;
import Beans.Empleado;
import Beans.Local;
import BeansFX.CiudadFX;
import BeansFX.CodigoPostalFX;
import BeansFX.EmpleadoFX;
import BeansFX.LocalFX;
import BeansFX.ProvinciaFX;
import Utils.MetodosEstaticos;
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
    
    private LocalFX local;
    private AAController viewControl;
    private FilteredList<CiudadFX> filterCiudad;
    private FilteredList<CodigoPostalFX> filterCP;
    private FilteredList<LocalFX> filterLocal;
    private ObservableList<LocalFX> listaLocal;
    private ObservableList<EmpleadoFX> listaEmpleados;

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
        configurarComboProv();
        configurarComboCiudad();
        configurarComboCP();
        configurarTxtCalle();
        configurarTxtNum();
        configurarTxtFiltro();
        configurarGraficos();
        configurarBase(base);
    }

    private void initValues() {
        infoFiltro.setTooltip(new Tooltip("FILTRA LOS LOCALES EN BASE AL TEXTO INTRODUCIDO"));
        listaEmpleados = FXCollections.observableArrayList();
        listaLocal = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, " Estado = 1 ")).forEach((Object lo) -> {
            listaLocal.add(new LocalFX((Local) lo));
        });
        filterLocal = new FilteredList<>(listaLocal, p -> true);
        cbLocales.setItems(filterLocal.sorted());
        cbProv.setItems(viewControl.getLogic().getProvincias().sorted());
        filterCiudad = new FilteredList<>(viewControl.getLogic().getCiudades(), p -> true);
        cbCiudad.setItems(filterCiudad.sorted());
        filterCP = new FilteredList<>(viewControl.getLogic().getCps(), p -> true);
        cbCP.setItems(filterCP.sorted());
        listaEmpleados = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Empleado.class, "1=1")).forEach((Object emp) -> {
            listaEmpleados.add(new EmpleadoFX((Empleado) emp));
        });
    }

    public void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    @FXML
    private void nuevoProducto(ActionEvent event) {
        viewControl.getLogic().getHibControl().initTransaction();
        CiudadConcp ccCP = (CiudadConcp) viewControl.getLogic().getHibControl().searchElement(CiudadConcp.class, "Cod_Ciudad=265 AND Cod_Postal='03802'");
        Direccion direc = new Direccion();
        direc.setNumero((short) 1);
        direc.setNombre("CALLE");
        direc.setCiudadConcp(ccCP);
        viewControl.getLogic().getHibControl().save(direc);
        viewControl.getLogic().getHibControl().initTransaction();
        Local tempo = new Local(direc, true);
        viewControl.getLogic().getHibControl().save(tempo);
        local = new LocalFX(tempo);
        listaLocal.add(local);
        refrescarVista();
    }

    @FXML
    private void borrarProducto(ActionEvent event) {
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

}//fin de la clase
