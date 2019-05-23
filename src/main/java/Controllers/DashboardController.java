package Controllers;

import Beans.LineaPedido;
import Beans.Pedido;
import BeansFX.LineaPedidoFX;
import BeansFX.PedidoFX;
import BeansFX.ProductoFX;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
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
        listaPedido = FXCollections.observableArrayList();

        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Pedido.class, "1=1")).forEach((Object ped) -> {
            listaPedido.add(new PedidoFX((Pedido) ped));
        });
        filterProducto = new FilteredList<>(viewControl.getLogic().getProductos(), pro -> {
            for (PedidoFX ped : listaPedido) {
                return recorrerPedidos(pro, ped);
            }
            return false;
        });
        doColumnTotalProducto(tcTotal);
        tcProductos.setCellValueFactory((TableColumn.CellDataFeatures<ProductoFX, String> producto) -> producto.getValue().nombreProperty());
        
//tvCuadro.getColumns().get(1).getColumns().add()
        tvCuadro.getItems().addAll(filterProducto);
    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    private boolean recorrerPedidos(ProductoFX pro, PedidoFX ped) {
        return ped.getLineasPedido().stream().anyMatch((lin) -> Objects.equals(pro.getCodProd(), ((LineaPedido) lin).getProducto().getCodProd()));
    }

    @SuppressWarnings("Convert2Lambda")
    public void doColumnTotalProducto(TableColumn lineasTC) {
        lineasTC.setCellValueFactory(new PropertyValueFactory<>("ProductoFX"));
        lineasTC.setCellFactory(new Callback<TableColumn<ProductoFX, Void>, TableCell<ProductoFX, Void>>() {
            @Override
            public TableCell<ProductoFX, Void> call(TableColumn<ProductoFX, Void> param) {
                TableCell<ProductoFX, Void> cell = new TableCell<ProductoFX, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            ProductoFX prod = getTableView().getItems().get(getIndex());
                            int total = contarUnidades(prod);
                            Label lb = new Label(total + "");
                            HBox h = new HBox(1, lb);
                            h.alignmentProperty().setValue(Pos.CENTER);
                            setGraphic(h);
                        }
                    }
                };
                return cell;
            }
        });
    }

    private int contarUnidades(ProductoFX prod) {
        int cuentas = 0;
        for (PedidoFX ped : listaPedido) {
            for (LineaPedido linPed : ped.lineasPedidoProperty()) {
                if(Objects.equals(prod.getCodProd(), linPed.getProducto().getCodProd())){
                    cuentas+=linPed.getCantidad();
                }
            }
        }
        return cuentas;
    }
}//fin de la clase
