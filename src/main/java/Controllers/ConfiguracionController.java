package Controllers;

import Utils.Constantes;
import dam.proyecto.AAController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Jorge Sempere Jimenez
 */
public class ConfiguracionController implements Initializable {

    @FXML
    private WebView ww;
    @FXML
    private ToggleButton tbLocal;
    @FXML
    private ToggleButton tbProducto;
    @FXML
    private ToggleButton tbRepo;
    private AAController viewControl;
    private WebEngine engine;
    private ToggleGroup grupo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void init() {
        grupo = new ToggleGroup();
        engine = ww.getEngine();
        tbLocal.setToggleGroup(grupo);
        tbProducto.setToggleGroup(grupo);
        tbRepo.setToggleGroup(grupo);
    }

    public void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    @FXML
    private void viewLocales(ActionEvent event) {
        engine.load(Constantes.Qlik.LOCALES.getUrl());
    }

    @FXML
    private void viewProductos(ActionEvent event) {
        engine.load(Constantes.Qlik.PRODUCTOS.getUrl());
    }

    @FXML
    private void viewReporting(ActionEvent event) {
        engine.load(Constantes.Qlik.REPORTING.getUrl());
    }

}//fin de clase
