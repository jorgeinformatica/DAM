package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Jorge Sempere
 */
public class DashboardController implements Initializable {

    @FXML
    private ScrollPane scrollPanel;
    @FXML
    private TableColumn<?, ?> tcProductos;
    @FXML
    private TableColumn<?, ?> tcClientes;
    @FXML
    private TableColumn<?, ?> tcTotal;
    @FXML
    private TableView<?> tvCuadro;

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

    void setViewControl(AAController aThis) {
    }

}//fin de la clase
