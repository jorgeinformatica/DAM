package Controllers;

import Beans.CiudadConcp;
import Beans.Direccion;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    public void init(ObservableList<Node> base) {
        initValues();
        configurarComboLocal();
        configurarComboProv();
        configurarComboCiudad();
        configurarComboCP();
        configurarTxtCalle();
        configurarTxtNum();
        configurarTxtFiltro();
        configurarTablaEmpl();
        configurarBase(base);
    }

    private void initValues() {
        infoFiltro.setTooltip(new Tooltip("FILTRA LOS LOCALES EN BASE AL TEXTO INTRODUCIDO"));
        listaEmpleados = FXCollections.observableArrayList();
        listaLocal = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, "1=1")).forEach((Object lo) -> {
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
        viewControl.getLogic().getHibControl().initTransaction();
        Direccion direc = new Direccion();
        direc.setNumero((short)1);
        direc.setNombre("CALLE");
        viewControl.getLogic().getHibControl().save(direc);
        viewControl.getLogic().getHibControl().goCommit();
        viewControl.getLogic().getHibControl().initTransaction();
        Local tempo = new Local(direc, true);
        viewControl.getLogic().getHibControl().save(tempo);
        viewControl.getLogic().getHibControl().goCommit();
        local = new LocalFX(tempo);
        listaLocal.add(local);
        cbElementos.getSelectionModel().select(local);
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
                local.getDireccion().getRelCpCiu().setProvincia(cbProv.getValue());
                filterCiudad.setPredicate(ciu -> {
                    return recorrerCCP(ciu, local.getDireccion().getRelCpCiu().getProvincia());
                });
            }
        });
    }

    private void configurarComboCiudad() {
        cbCiudad.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && !nV) {
                local.getDireccion().getRelCpCiu().setCiudad(cbCiudad.getValue());
                filterCP.setPredicate(cp -> {
                    return recorrerCCP(cp, local.getDireccion().getRelCpCiu().getCiudad());
                });
            }
        });
    }

    private void configurarComboCP() {
        cbCP.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && !nV) {
                local.getDireccion().getRelCpCiu().setCodigoPostal(cbCP.getValue());
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
            if (local != null && !nV) {
                local.getDireccion().setNumero(Short.valueOf(txtNum.getText().isEmpty() ? "" : txtNum.getText().toUpperCase()));
            }
            if (txtNum.getText().isEmpty()) {
                txtNum.requestFocus();
            }
        });

    }

    private void configurarTxtNum() {
        txtCalle.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (local != null && !nV) {
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
        cbProv.getSelectionModel().select(local.getDireccion().getRelCpCiu().getProvincia());
        cbCiudad.getSelectionModel().select(local.getDireccion().getRelCpCiu().getCiudad());
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
        ListChangeListener<Node> changeList = (ListChangeListener.Change<? extends Node> c) -> {
            actualizarLocal(local);
        };
        base.addListener(changeList);
    }

}//fin de la clase
