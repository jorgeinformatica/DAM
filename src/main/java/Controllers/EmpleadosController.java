package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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
    private ComboBox<?> cbLocal;
    @FXML
    private TextField txtCalle;
    @FXML
    private TextField txtNum;
    @FXML
    private ComboBox<?> cbCP;
    @FXML
    private ComboBox<?> cbCiudad;
    @FXML
    private ComboBox<?> cbProv;
    @FXML
    private Button nuevoBTN1;
    @FXML
    private Button borrarBTN1;
    @FXML
    private ComboBox<?> cbElementos;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Label infoFiltro;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void init() {

    }

    @FXML
    private void soloNum(KeyEvent event) {
    }

    void setViewControl(AAController aThis) {

    }

    @FXML
    private void nuevoProducto(ActionEvent event) {
    }

    @FXML
    private void borrarProducto(ActionEvent event) {
    }

}//fin de la clase
