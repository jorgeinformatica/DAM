package Controllers;

import BeansFX.ProductoFX;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
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
        doColumnActionsProducto(accionesTC);
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

    @SuppressWarnings("Convert2Lambda")
    private void doColumnActionsProducto(TableColumn accionesTC) {
        accionesTC.setCellValueFactory(new PropertyValueFactory<>("ProductoFX"));
        accionesTC.setCellFactory(new Callback<TableColumn<ProductoFX, Void>, TableCell<ProductoFX, Void>>() {
            @Override
            public TableCell<ProductoFX, Void> call(TableColumn<ProductoFX, Void> param) {
                TableCell<ProductoFX, Void> cell = new TableCell<ProductoFX, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            ProductoFX producto = getTableView().getItems().get(getIndex());
                            Button btn = GlyphsDude.createIconButton(FontAwesomeIcon.EYE);
                            btn.setOnAction((event) -> {
                                viewControl.getLogic().setProducto(producto);
                                viewControl.getmItModProd().fire();
                            });
                            btn.setTooltip(new Tooltip("Ver en detalle el producto"));
                            HBox h = new HBox(5, btn);
                            h.alignmentProperty().setValue(Pos.CENTER);
                            setGraphic(h);
                        }
                    }
                };
                return cell;
            }
        });
    }

}//fin de clase
