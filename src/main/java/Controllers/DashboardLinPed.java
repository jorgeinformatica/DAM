package Controllers;

import BeansFX.LineaPedidoFX;
import Utils.Constantes;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * @author Jorge Sempere Jimenez
 */
public class DashboardLinPed implements Initializable {
    
    @FXML
    private TableView<LineaPedidoFX> tvLineas;
    @FXML
    private TableColumn ltcLinea;
    @FXML
    private TableColumn<LineaPedidoFX, String> tcProducto;
    @FXML
    private TableColumn<LineaPedidoFX, Short> tcCantidad;
    @FXML
    private TableColumn<LineaPedidoFX, String> tcEstado;
    
    private ObservableList<LineaPedidoFX> lineas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvLineas.setId(Constantes.CSSId.TABLEVIEWID.getId());
    }
    
    public void init(ObservableList<LineaPedidoFX> lineas) {
        this.lineas = lineas;
        doColumnLineasPedido(ltcLinea);
        tcProducto.setCellValueFactory(linea -> linea.getValue().getProducto().nombreProperty());
        tcCantidad.setCellValueFactory(linea -> linea.getValue().cantidadProperty());
        tcEstado.setCellValueFactory(linea -> linea.getValue().estadoProperty());
        tvLineas.getItems().addAll(lineas);
    }
    
    @SuppressWarnings("Convert2Lambda")
    public void doColumnLineasPedido(TableColumn lineasTC) {
        lineasTC.setCellValueFactory(new PropertyValueFactory<>("LineaPedidoFX"));
        lineasTC.setCellFactory(new Callback<TableColumn<LineaPedidoFX, Void>, TableCell<LineaPedidoFX, Void>>() {
            @Override
            public TableCell<LineaPedidoFX, Void> call(TableColumn<LineaPedidoFX, Void> param) {
                TableCell<LineaPedidoFX, Void> cell = new TableCell<LineaPedidoFX, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Label lb = new Label((getIndex() + 1) + "");
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
    
}//fin de clase
