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
    private Button btnAceptarCambio;

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
        btnAceptarCambio.setVisible(false);
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
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Empleado.class, Constantes.HQLCondicion.ESTADO.getSentencia())).forEach((Object emp) -> {
            listaEmpleados.add(new EmpleadoFX((Empleado) emp));
        });
        filterEmpleados = new FilteredList<>(listaEmpleados, p -> true);
        cbEmpleados.setItems(filterEmpleados.sorted());
        listaLocal = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Local.class, Constantes.HQLCondicion.ESTADO.getSentencia())).forEach((Object lo) -> {
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
    private void nuevoEmpleado(ActionEvent event) {
        viewControl.getLogic().getHibControl().initTransaction();
        CiudadConcp ccCP = (CiudadConcp) viewControl.getLogic().getHibControl().searchElement(CiudadConcp.class, Constantes.HQLCondicion.CENTRALREF.getSentencia());
        Direccion direc = new Direccion();
        direc.setNumero((short) 1);
        direc.setNombre("CALLE");
        direc.setCiudadConcp(ccCP);
        viewControl.getLogic().getHibControl().save(direc);
        viewControl.getLogic().getHibControl().initTransaction();
        Empleado tempo = new Empleado("23232323T", direc, (Local) cbLocal.getItems().get(0).getBean(), "nombre", "primer Apellido", "segundo Apellido", "999999999", "Correo electrónico", true);
        viewControl.getLogic().getHibControl().save(tempo);
        empleado = new EmpleadoFX(tempo);
        listaEmpleados.add(empleado);
        cbEmpleados.getSelectionModel().select(empleado);

    }

    @FXML
    private void borrarEmpleado(ActionEvent event) {
        if (empleado != null) {
            if (viewControl.getLogic().desactivarMsg(empleado)) {
                listaEmpleados.remove(empleado);
                cbEmpleados.getSelectionModel().selectFirst();
            }
        }
    }

    private void configurarTxtDni() {
        txtDni.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtDni, 8));
        txtDni.setTextFormatter(MetodosEstaticos.soloNumeros());
        txtDni.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                if (txtDni.getText().isEmpty() || txtDni.getText().length() < 8) {
                    txtDni.requestFocus();
                }
                if (!((Empleado) empleado.getBean()).getDni().subSequence(0, 7).equals(txtDni.getText())) {
                    String juegoCaracteres = "TRWAGMYFPDXBNJZSQVHLCKE";
                    int modulo = Integer.valueOf(txtDni.getText()) % 23;
                    labLetra.setText(juegoCaracteres.charAt(modulo) + "");
                    empleado.setDni(txtDni.getText() + labLetra.getText());
                    aceptarCambios();
                }
            }
        });
    }

    private void configurarTxtNom() {
        txtNom.lengthProperty().addListener(MetodosEstaticos.longMaxima(txtNom, 39));
        txtNom.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.setNombre(txtNom.getText().toUpperCase());
                aceptarCambios();
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
                aceptarCambios();
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
                aceptarCambios();
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
                aceptarCambios();
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
                empleado.setTelefono(txtTel.getText().toUpperCase());
                aceptarCambios();
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
                empleado.getDireccion().setNombre(txtCalle.getText().toUpperCase());
                aceptarCambios();
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
                empleado.getDireccion().setNumero(Short.valueOf(txtNum.getText().toUpperCase()));
                aceptarCambios();
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
                empleado.getDireccion().setRelCpCiu(LogicController.getCCFX(empleado.getDireccion().getRelCpCiu().getCiudad(), empleado.getDireccion().getRelCpCiu().getCodigoPostal())
                        == null ? empleado.getDireccion().getRelCpCiu()
                                : LogicController.getCCFX(empleado.getDireccion().getRelCpCiu().getCiudad(), empleado.getDireccion().getRelCpCiu().getCodigoPostal()));
                aceptarCambios();
            }
        });
    }

    private void configurarComboPro() {
        cbProv.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.getDireccion().getRelCpCiu().setProvincia(cbProv.getValue());
                empleado.getDireccion().setRelCpCiu(LogicController.getCCFX(empleado.getDireccion().getRelCpCiu().getCiudad(), empleado.getDireccion().getRelCpCiu().getCodigoPostal())
                        == null ? empleado.getDireccion().getRelCpCiu()
                                : LogicController.getCCFX(empleado.getDireccion().getRelCpCiu().getCiudad(), empleado.getDireccion().getRelCpCiu().getCodigoPostal()));
                aceptarCambios();
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
                empleado.getDireccion().setRelCpCiu(LogicController.getCCFX(empleado.getDireccion().getRelCpCiu().getCiudad(), empleado.getDireccion().getRelCpCiu().getCodigoPostal())
                        == null ? empleado.getDireccion().getRelCpCiu()
                                : LogicController.getCCFX(empleado.getDireccion().getRelCpCiu().getCiudad(), empleado.getDireccion().getRelCpCiu().getCodigoPostal()));
                aceptarCambios();
            }
        });
    }

    private void configurarComboEmp() {
        cbEmpleados.valueProperty().addListener(
                (ObservableValue<? extends EmpleadoFX> lo, EmpleadoFX oV, EmpleadoFX nV) -> {
                    if (nV != null) {
                        empleado = nV;
                        refrescarVista();
                        if (oV != null) {
                            actualizarEmpleado(oV);
                        }
                    }
                });
        cbEmpleados.getSelectionModel().selectFirst();
    }

    private void configurarComboLocal() {
        cbLocal.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (empleado != null && !nV) {
                empleado.setLocal(cbLocal.getValue());
                aceptarCambios();
            }
        });
    }

    private void actualizarEmpleado(EmpleadoFX lo) {
        if (viewControl.getLogic().actualizarMsg(lo)) {
            refrescarVista();
        }
    }

    private void refrescarVista() {
        txtNom.setText(empleado.getNombre());
        txtApe1.setText(empleado.getApe1());
        txtApe2.setText(empleado.getApe2());
        txtDni.setText(empleado.getDni().substring(0, 8));
        labLetra.setText(empleado.getDni().substring(8));
        txtEmail.setText(empleado.getEmail());
        txtTel.setText(empleado.getTelefono());
        txtCalle.setText(empleado.getDireccion().getNombre());
        txtNum.setText(empleado.getDireccion().getNumero() + "");
        cbProv.getSelectionModel().select(empleado.getDireccion().getRelCpCiu().getProvincia());
        cbCiudad.getSelectionModel().select(empleado.getDireccion().getRelCpCiu().getCiudad());
        cbCP.getSelectionModel().select(empleado.getDireccion().getRelCpCiu().getCodigoPostal());
        cbLocal.getSelectionModel().select(empleado.getLocal());
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
                actualizarEmpleado(empleado);
                base.removeListener(this);
            }
        });
    }

    private void aceptarCambios() {
        if (!btnAceptarCambio.isVisible() && empleado.comprobarCambios()) {
            viewControl.getLogic().aceptarCambiosBtn(btnAceptarCambio, empleado);
        }
    }
}//fin de la clase
