package Controllers;

import BeansFX.ProductoFX;
import Utils.Columns;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Jorge Sempere
 */
public class ListarProductosController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<ProductoFX> productosTV;
    @FXML
    private TableColumn accionesTC;
    @FXML
    private TableColumn<ProductoFX, String> nombreTC;
    @FXML
    private TableColumn<ProductoFX, BigDecimal> precioTC;
    @FXML
    private TableColumn<ProductoFX, String> ivaTC;
    @FXML
    private TableColumn<ProductoFX, String> descripcionTC;

    private AAController viewControl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void init() {
        nombreTC.setCellValueFactory(producto -> producto.getValue().nombreProperty());
        precioTC.setCellValueFactory(producto -> producto.getValue().precioProperty());
        ivaTC.setCellValueFactory(producto -> producto.getValue().tipoIvaProperty());
        Columns.doColumnActionsProducto(accionesTC,viewControl);
        descripcionTC.setCellValueFactory(producto -> producto.getValue().descripcionProperty());
        FilteredList<ProductoFX> listaFiltrada
                = new FilteredList<>(viewControl.getLogic().getProductos(), p -> true);
        txtBuscar.textProperty().addListener((oble, oV, nV) -> {
            listaFiltrada.setPredicate(producto -> {
                if (nV == null || nV.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = nV.toLowerCase();
                return producto.getNombre().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<ProductoFX> listaOrdenada = new SortedList<>(listaFiltrada);
        listaOrdenada.comparatorProperty().bind(productosTV.comparatorProperty());
        productosTV.setItems(listaOrdenada);
    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }
}
