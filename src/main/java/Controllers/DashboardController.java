package Controllers;

import BeansFX.PedidoFX;
import BeansFX.ProductoFX;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Jorge Sempere Jimenez
 */
public class DashboardController implements Initializable {

    @FXML
    private ScrollPane scrollPanel;
    @FXML
    private TableColumn<ProductoFX, String> tcProductos;
    @FXML
    private TableColumn tcClientes;
    @FXML
    private TableColumn tcTotal;
    @FXML
    private TableView<ProductoFX> tvCuadro;

    private AAController viewControl;
    private ObservableList<PedidoFX> listaPedido;
    private FilteredList<ProductoFX> filterProducto;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void init() {

    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

}//fin de la clase
