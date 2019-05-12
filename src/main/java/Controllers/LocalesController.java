package Controllers;

import Beans.Local;
import BeansFX.CiudadFX;
import BeansFX.CodigoPostalFX;
import BeansFX.EmpleadoFX;
import BeansFX.LocalFX;
import BeansFX.ProvinciaFX;
import Utils.MetodosEstaticos;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 *
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
    
    private LocalFX local;
    private AAController viewControl;
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
        configurarComboProv();
        configurarComboCiudad();
        configurarComboCP();
        configurarComboLocal();
        configurarTxtCalle();
        configurarTxtNum();
        configurarTxtFiltro();
        configurarTablaEmpl();
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
//        cbProv.getSelectionModel().selectedItemProperty().addListener((obs, oV, nV) -> {
//            CiudadFX selected = cbCiudad.getSelectionModel().getSelectedItem();
//            Platform.runLater(() -> {
//                if (!Objects.equals(selected.getProvincia().getCodProv(), nV.getCodProvincia())) {
//                    filterCiudad.setPredicate(item -> {
//                        return Objects.equals(((CiudadFX) item).getProvincia().getCodProv(), nV.getCodProvincia());
//                    });
//                }
//            });
//        });
    }
    
    private void configurarComboCiudad() {
        
    }
    
    private void configurarComboCP() {
        cbCP.getSelectionModel().selectedItemProperty().addListener((obs, oV, nV) -> {
            CiudadFX selected = cbCiudad.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null) {
                    filterCiudad.setPredicate(item -> {
                        //return Objects.equals(((CiudadFX) item).getProvincia().getCodProv(), nV.getCodProvincia());
                        return true;
                    });
                }
            });
        });
        
    }
    
    private void configurarComboLocal() {
        infoFiltro.setTooltip(new Tooltip("FILTRA LOS LOCALES EN BASE AL TEXTO INTRODUCIDO"));
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, "1=1")).forEach((lo) -> {
            listaLocal.add(new LocalFX((Local) lo));
        });
        filterLocal = new FilteredList<>(listaLocal, p -> true);
        cbElementos.setItems(filterLocal);
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
                local.getDireccion().setNumero(Short.valueOf(txtNum.getText().isEmpty() ? "1" : txtNum.getText().toUpperCase()));
            }
            if (txtNum.getText().isEmpty()) {
                txtNum.requestFocus();
            }
        });
        
    }
    
    private void configurarTxtNum() {
        txtCalle.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && oV && !nV) {
                local.getDireccion().setNombre(txtCalle.getText().isEmpty() ? "" : txtCalle.getText().toUpperCase());
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
    
    private void initValues() {
        listaLocal = FXCollections.observableArrayList();
        listaEmpleados = FXCollections.observableArrayList();
        filterProv = new FilteredList<>(viewControl.getLogic().getProvincias(), p -> true);
        cbProv.setItems(filterProv);
        filterCiudad = new FilteredList<>(viewControl.getLogic().getCiudades(), p -> true);
        cbCiudad.setItems(filterCiudad);
        filterCP = new FilteredList<>(viewControl.getLogic().getCps(), p -> true);
        cbCP.setItems(filterCP);
    }
    
}//fin de la clase
