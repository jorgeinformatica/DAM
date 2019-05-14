package Controllers;

import BeansFX.CiudadFX;
import BeansFX.CodigoPostalFX;
import BeansFX.EmpleadoFX;
import BeansFX.LocalFX;
import BeansFX.ProvinciaFX;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
    private Button nuevoBTN;
    @FXML
    private Button borrarBTN;

    private EmpleadoFX empleado;
    private AAController viewControl;
    private FilteredList<CiudadFX> filterCiudad;
    private FilteredList<CodigoPostalFX> filterCP;
    private FilteredList<EmpleadoFX> filterEmpleados;
    private ObservableList<EmpleadoFX> listaEmpleados;

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
    }

    private void configurarTxtDni() {

    }

    private void configurarTxtNom() {

    }

    private void configurarTxtApe1() {

    }

    private void configurarTxtApe2() {

    }

    private void configurarEmail() {

    }

    private void configurarTxtTel() {

    }

    private void configurarTxtCalle() {

    }

    private void configurarTxtNum() {

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

    }

    private void configurarComboPro() {

    }

    private void configurarComboCi() {

    }

    private void configurarComboEmp() {

    }

    private void configurarComboLocal() {

    }

    private void actualizarEmpleado(EmpleadoFX lo) {
        if (viewControl.getLogic().actualizarMsg(lo)) {
            refrescarVista();
        }
    }

    private void refrescarVista() {

    }

    private void configurarBase(ObservableList<Node> base) {
        ListChangeListener<Node> changeList = (ListChangeListener.Change<? extends Node> c) -> {
            actualizarEmpleado(empleado);
        };
        base.addListener(changeList);
    }
}//fin de la clase
