package Controllers;

import Beans.Empleado;
import Utils.MetodosEstaticos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Jorge Sempere
 */
public class LogueoController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    
    private AAController viewControl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public TextField getTxtUser() {
        return txtUser;
    }

    public PasswordField getTxtPass() {
        return txtPass;
    }

    public Empleado getUser() {
        return null;
    }

    @FXML
    private void AccederApp(ActionEvent event) {
        if (txtPass.getText().isEmpty() || txtUser.getText().isEmpty()) {
            Alert aviso = new Alert(Alert.AlertType.INFORMATION, "Por favor, Introduzca todos los datos", ButtonType.OK);
            aviso.show();
        } else {
            if (viewControl.getLogic().setUser(txtUser.getText(), txtPass.getText())) {
                Alert aviso = new Alert(Alert.AlertType.INFORMATION, "Bienvenido, " + viewControl.getLogic().getUsuario().getNombre(), ButtonType.OK);
                aviso.setHeaderText(null);
                aviso.showAndWait();
                viewControl.getBarra().setVisible(true);
        //        viewControl.getStage().setFullScreen(true);
                viewControl.getBase().getChildren().remove(viewControl.getLogic().getRoot());
                viewControl = null;
            } else {
                Alert aviso = new Alert(Alert.AlertType.INFORMATION, "Datos incorrectos, vuelva a intentarlo", ButtonType.OK);
                aviso.show();
                txtPass.setText("");
                txtUser.setText("");
            }
        }
    }

    @FXML
    private void salirApp(ActionEvent event) {
        MetodosEstaticos.SalirApp(viewControl.getStage());
    }

    public AAController getViewControl() {
        return viewControl;
    }

    public void setViewControl(AAController viewControl) {
        this.viewControl = viewControl;
    }

}//fin de la clase

