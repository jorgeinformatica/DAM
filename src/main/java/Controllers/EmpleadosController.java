package Controllers;

import Beans.CiudadConcp;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Jorge Sempere
 */
public class EmpleadosController implements Initializable {

    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtApe1;
    @FXML
    private TextField txtApe2;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTel;
    @FXML
    private ComboBox<LocalFX> cbLocal;
    @FXML
    private TextField txtCalle;
    @FXML
    private TextField txtNum;
    @FXML
    private ComboBox<CodigoPostalFX> cbCP;
    @FXML
    private ComboBox<CiudadFX> cbCiudad;
    @FXML
    private ComboBox<ProvinciaFX> cbProv;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Label infoFiltro;
    @FXML
    private ComboBox<EmpleadoFX> cbEmpleados;
    @FXML
    private Label labLetra;
    @FXML
    private Button nuevoBTN;
    @FXML
    private Button borrarBTN;

    private EmpleadoFX empleado;
    private AAController viewControl;
    private FilteredList<CiudadFX> filterCiudad;
    private FilteredList<CodigoPostalFX> filterCP;
    private FilteredList<EmpleadoFX> filterEmpleados;
    private ObservableList<EmpleadoFX> listaEmpleados;
    private ObservableList<LocalFX> listaLocal;


    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    void init(ObservableList<Node> base) {
        initValues();
        configurarTxtDni();
        configurarTxtNom();
        configurarTxtApe1();
        configurarTxtApe2();
        configurarEmail();
        configurarTxtTel();
        configurarTxtCalle();
        configurarTxtNum();
        configurarTxtFiltro();
        configurarComboCP();
        configurarComboPro();
        configurarComboCi();
        configurarComboEmp();
        configurarComboLocal();
        configurarBase(base);
    }

    private void initValues() {
        infoFiltro.setTooltip(new Tooltip("FILTRA LOS EMPLEADOS EN BASE AL TEXTO INTRODUCIDO"));
        listaEmpleados = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Empleado.class, "1=1")).forEach((Object emp) -> {
            listaEmpleados.add(new EmpleadoFX((Empleado) emp));
        });
        filterEmpleados = new FilteredList<>(listaEmpleados, p -> true);
        cbEmpleados.setItems(filterEmpleados.sorted());
        listaLocal = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, "1=1")).forEach((Object lo) -> {
            listaLocal.add(new LocalFX((Local) lo));
        });
        cbLocal.setItems(listaLocal);
        cbProv.setItems(viewControl.getLogic().getProvincias().sorted());
        filterCiudad = new FilteredList<>(viewControl.getLogic().getCiudades(), p -> true);
        cbCiudad.setItems(filterCiudad.sorted());
        filterCP = new FilteredList<>(viewControl.getLogic().getCps(), p -> true);
        cbCP.setItems(filterCP.sorted());
    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    @FXML
    private void nuevoProducto(ActionEvent event) {
    }

    @FXML
    private void borrarProducto(ActionEvent event) {
        if (empleado != null) {
            if (viewControl.getLogic().desactivarMsg(empleado)) {
                listaEmpleados.remove(empleado);
            }
        }
    }

    private void configurarTxtDni() {
        txtDni.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtDni, 8));
        txtDni.setTextFormatter(MetodosEstaticos.soloNumeros());
        
    }

    private void configurarTxtNom() {
        txtNom.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtNom, 39));
        txtNom.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.setNombre(txtNom.getText().toUpperCase());
            }
            if (txtNom.getText().isEmpty()) {
                txtNom.requestFocus();
            }
        });
    }

    private void configurarTxtApe1() {
        txtApe1.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtApe1, 39));
        txtApe1.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.setApe1(txtApe1.getText().toUpperCase());
            }
            if (txtApe1.getText().isEmpty()) {
                txtApe1.requestFocus();
            }
        });
    }

    private void configurarTxtApe2() {
        txtApe2.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtApe2, 39));
        txtApe2.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.setApe2(txtApe2.getText().toUpperCase());
            }
            if (txtApe2.getText().isEmpty()) {
                txtApe2.requestFocus();
            }
        });
    }

    private void configurarEmail() {
        txtEmail.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtEmail, 99));
        txtEmail.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.setEmail(txtEmail.getText().toUpperCase());
            }
            if (txtEmail.getText().isEmpty()) {
                txtEmail.requestFocus();
            }
        });
    }

    private void configurarTxtTel() {
        txtTel.setTextFormatter(MetodosEstaticos.soloNumeros());
        txtTel.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtTel, 9));
        txtTel.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.setEmail(txtTel.getText().toUpperCase());
            }
            if (txtTel.getText().isEmpty()) {
                txtTel.requestFocus();
            }
        });
    }

    private void configurarTxtCalle() {
        txtCalle.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtCalle, 74));
        txtCalle.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.getDireccion().setNombre(txtCalle.getText().isEmpty() ? "1" : txtCalle.getText().toUpperCase());
            }
            if (txtCalle.getText().isEmpty()) {
                txtCalle.requestFocus();
            }
        });
    }

    private void configurarTxtNum() {
        txtNum.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtNum, 3));
        txtNum.setTextFormatter(MetodosEstaticos.soloNumeros());
        txtNum.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.getDireccion().setNumero(Short.valueOf(txtNum.getText().isEmpty() ? "" : txtNum.getText().toUpperCase()));
            }
            if (txtNum.getText().isEmpty()) {
                txtNum.requestFocus();
            }
        });
    }

    private void configurarTxtFiltro() {
        txtFiltro.textProperty().addListener((obs, oldValue, newValue) -> {
            EmpleadoFX selected = cbEmpleados.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.toString().toUpperCase().equals(newValue.toUpperCase())) {
                    filterEmpleados.setPredicate(item -> {
                        return ((EmpleadoFX) item).toString().toUpperCase().contains(newValue.toUpperCase());
                    });
                }
            });
        });
    }

    private void configurarComboCP() {
        cbCP.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && nV) {
                filterCP.setPredicate(cp -> {
                    return recorrerCCP(cp, empleado.getDireccion().getRelCpCiu().getCiudad());
                });
            }
            if (empleado != null && !nV) {
                empleado.getDireccion().getRelCpCiu().setCodigoPostal(cbCP.getValue());
            }
        });
    }

    private void configurarComboPro() {
        cbProv.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.getDireccion().getRelCpCiu().setProvincia(cbProv.getValue());
            }
        });
    }

    private void configurarComboCi() {
        cbCiudad.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && nV) {
                filterCiudad.setPredicate(ciu -> {
                    return recorrerCCP(ciu, empleado.getDireccion().getRelCpCiu().getProvincia());
                });
            }
            if (empleado != null && !nV) {
                empleado.getDireccion().getRelCpCiu().setCiudad(cbCiudad.getValue());
            }
        });
    }

    private void configurarComboEmp() {

    }

    private void configurarComboLocal() {
        cbLocal.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.setLocal((Local) cbLocal.getValue().getBean());
            }
        });
    }

    private void actualizarEmpleado(EmpleadoFX lo) {
        if (viewControl.getLogic().actualizarMsg(lo)) {
            refrescarVista();
        }
    }

    private void refrescarVista() {

    }

    private boolean recorrerCCP(CodigoPostalFX cpFX, CiudadFX nV) {
        return nV.getCiudadConcp().stream().anyMatch((o) -> (Objects.equals(((CiudadConcp) o).getCodigoPostal().getCodPostal(), cpFX.getCodPostal())));
    }

    private boolean recorrerCCP(CiudadFX cFX, ProvinciaFX nV) {
        return nV.getCiudadConcp().stream().anyMatch((o) -> (Objects.equals(((CiudadConcp) o).getCiudad().getCodCiudad(), cFX.getCodCiudad())));
    }

    private void configurarBase(ObservableList<Node> base) {
        ListChangeListener<Node> changeList = (ListChangeListener.Change<? extends Node> c) -> {
            actualizarEmpleado(empleado);
        };
        base.addListener(changeList);
    }
}//fin de la clase
