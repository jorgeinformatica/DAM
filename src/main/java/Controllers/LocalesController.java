package Controllers;

import Beans.Ciudad;
import Beans.CiudadConcp;
import Beans.CodigoPostal;
import Beans.Local;
import Beans.Provincia;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
    private TableView<EmpleadoFX> tvEmpleados;
    @FXML
    private TableColumn tcAsignados;
    @FXML
    private TableColumn tcEmpleados;
    @FXML
    private Button nuevoBTN1;
    @FXML
    private Button borrarBTN1;
    @FXML
    private ComboBox<LocalFX> cbElementos;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Label infoFiltro;
    @FXML
    private RadioButton rbCP;
    @FXML
    private RadioButton rbProv;
    @FXML
    private RadioButton rbCiu;

    private LocalFX local;
    private AAController viewControl;
    private ToggleGroup grupo;
    private FilteredList<ProvinciaFX> filterProv;
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

    public void init() {
        initValues();
        configurarComboLocal();
        configurarComboProv();
        configurarComboCiudad();
        configurarComboCP();
        configurarTxtCalle();
        configurarTxtNum();
        configurarTxtFiltro();
        configurarGrupo();
        configurarTablaEmpl();
    }

    private void initValues() {
        grupo = new ToggleGroup();
        rbCP.setToggleGroup(grupo);
        rbCP.setUserData("CP");
        rbCiu.setToggleGroup(grupo);
        rbCiu.setUserData("ciudad");
        rbProv.setToggleGroup(grupo);
        rbProv.setUserData("provincia");

        infoFiltro.setTooltip(new Tooltip("FILTRA LOS LOCALES EN BASE AL TEXTO INTRODUCIDO"));
        listaEmpleados = FXCollections.observableArrayList();
        listaLocal = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, "1=1")).forEach((lo) -> {
            listaLocal.add(new LocalFX((Local) lo));
        });
        filterLocal = new FilteredList<>(listaLocal, p -> true);
        cbElementos.setItems(filterLocal.sorted());

        filterProv = new FilteredList<>(viewControl.getLogic().getProvincias(), p -> true);
        cbProv.setItems(filterProv.sorted());

        filterCiudad = new FilteredList<>(viewControl.getLogic().getCiudades(), p -> true);
        cbCiudad.setItems(filterCiudad.sorted());

        filterCP = new FilteredList<>(viewControl.getLogic().getCps(), p -> true);
        cbCP.setItems(filterCP.sorted());
    }

    public void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    @FXML
    private void nuevoProducto(ActionEvent event) {

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
            if (local != null && oV && !nV) {
                local.getDireccion().getCiudadConcp().setProvincia(((Provincia) cbProv.getValue().getBean()));
            }
        });
    }

    private void configurarComboCiudad() {
        cbCiudad.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && oV && !nV) {
                local.getDireccion().getCiudadConcp().setCiudad(((Ciudad) cbCiudad.getValue().getBean()));
            }
        });
    }

    private void configurarComboCP() {
        cbCP.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && oV && !nV) {
                local.getDireccion().getCiudadConcp().setCodigoPostal(((CodigoPostal) cbCP.getValue().getBean()));
            }
        });
    }

    private void configurarComboLocal() {
        cbElementos.valueProperty().addListener(
                (ObservableValue<? extends LocalFX> lo, LocalFX oV, LocalFX nV) -> {
                    if (nV != null) {
                        local = nV;
                        refrescarVista();
                        if (oV != null) {
                            actualizarLocal(oV);
                        }
                    }
                });
        cbElementos.getSelectionModel().selectFirst();
    }

    private void configurarTxtCalle() {
        txtNum.setTextFormatter(MetodosEstaticos.soloNumeros());
        txtNum.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && oV && !nV) {
                local.getDireccion().setNumero(Short.valueOf(txtNum.getText().isEmpty() ? "" : txtNum.getText().toUpperCase()));
            }
            if (txtNum.getText().isEmpty()) {
                txtNum.requestFocus();
            }
        });

    }

    private void configurarTxtNum() {
        txtCalle.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && oV && !nV) {
                local.getDireccion().setNombre(txtCalle.getText().isEmpty() ? "1" : txtCalle.getText().toUpperCase());
            }
            if (txtCalle.getText().isEmpty()) {
                txtCalle.requestFocus();
            }
        });
    }

    private void configurarTxtFiltro() {
        txtFiltro.textProperty().addListener((obs, oldValue, newValue) -> {
            LocalFX selected = cbElementos.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.getDireccion().getNombre().toUpperCase().equals(newValue.toUpperCase())) {
                    filterLocal.setPredicate(item -> {
                        return ((LocalFX) item).getDireccion().getNombre().toUpperCase().contains(newValue.toUpperCase());
                    });
                }
            });
        });
    }

    private void configurarTablaEmpl() {

    }

    private void refrescarVista() {
        txtCalle.setText(local.getDireccion().getNombre());
        txtNum.setText(local.getDireccion().getNumero() + "");
        cbProv.getSelectionModel().select(viewControl.getLogic().getProvFX(local.getDireccion().getCiudadConcp().getProvincia()));
        cbCiudad.getSelectionModel().select(viewControl.getLogic().getCiuFX(local.getDireccion().getCiudadConcp().getCiudad()));
        cbCP.getSelectionModel().select(viewControl.getLogic().getCPFX(local.getDireccion().getCiudadConcp().getCodigoPostal()));
    }

    private void actualizarLocal(LocalFX lo) {
        if (viewControl.getLogic().actualizarMsg(lo)) {
            refrescarVista();
        }
    }

    private void configurarGrupo() {
        grupo.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> o, Toggle oV, Toggle nV) -> {
            if (grupo.getSelectedToggle() != null) {
                switch ((String) nV.getUserData()) {
                    case "CP":
                        cbCP.setDisable(false);
                        Ciudad c = local.getDireccion().getCiudadConcp().getCiudad();
                        if (c != null) {
                            filterCP.setPredicate((cp) -> {
                                return recorrerCCP(cp, viewControl.getLogic().getCiuFX(c));
                            });
                        } else {
                            filterCP.setPredicate(f -> true);
                        }
                        break;
                    case "ciudad":
                        cbCiudad.setDisable(false);
                        Provincia p = local.getDireccion().getCiudadConcp().getProvincia();
                        if (p != null) {
                            filterCiudad.setPredicate((ci) -> {
                                return recorrerCCP(ci, viewControl.getLogic().getProvFX(p));
                            });
                        } else {
                            filterCiudad.setPredicate(f -> true);
                        }
                        break;
                    case "provincia":
                        cbProv.setDisable(false);
                        break;
                }
                if (oV != null) {
                    switch ((String) oV.getUserData()) {
                        case "CP":
                            filterCP.setPredicate(f -> true);
                            cbCP.setDisable(true);
                            break;
                        case "ciudad":
                            filterCiudad.setPredicate(f -> true);
                            cbCiudad.setDisable(true);
                            break;
                        case "provincia":
                            cbProv.setDisable(true);
                            break;
                    }
                }
            }
        });
    }

    private boolean recorrerCCP(CodigoPostalFX cpFX, CiudadFX nV) {
        return nV.getCiudadConcp().stream().anyMatch((o) -> (Objects.equals(((CiudadConcp) o).getCodigoPostal().getCodPostal(), cpFX.getCodPostal())));
    }

    private boolean recorrerCCP(CiudadFX cFX, ProvinciaFX nV) {
        return nV.getCiudadConcp().stream().anyMatch((o) -> (Objects.equals(((CiudadConcp) o).getCiudad().getCodCiudad(), cFX.getCodCiudad())));
    }
}//fin de la clase
