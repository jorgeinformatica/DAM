package Controllers;

import Beans.LineaPedido;
import Beans.Pedido;
import BeansFX.PedidoFX;
import BeansFX.ProductoFX;
import Utils.Constantes;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * @author Jorge Sempere Jimenez
 */
public class DashboardController implements Initializable {

    @FXML
    private TableColumn<ProductoFX, String> tcProductos;
    @FXML
    private TableColumn tcTotal;
    @FXML
    private TableView<ProductoFX> tvCuadro;

    private AAController viewControl;
    private ObservableList<PedidoFX> listaPedido;
    private FilteredList<ProductoFX> filterProducto;
    private ObservableList<DashboardContainer> listaCont;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void init() {
        listaCont = FXCollections.observableArrayList();
        listaPedido = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl().getList(Pedido.class, "1=1")).forEach((Object ped) -> {
            listaPedido.add(new PedidoFX((Pedido) ped));
        });
        initColProduct();
        initContenedores();
        initColumnas();
        doColumnTotalProducto(tcTotal);
        tvCuadro.getItems().addAll(filterProducto);
    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    private boolean recorrerPedidos(ProductoFX pro, PedidoFX ped) {
        return ped.getLineasPedido().stream().anyMatch((lin) -> Objects.equals(pro.getCodProd(), ((LineaPedido) lin).getProducto().getCodProd()));
    }

    private int contarUnidades(ProductoFX prod) {
        int cuentas = 0;
        for (PedidoFX ped : listaPedido) {
            for (LineaPedido linPed : ped.lineasPedidoProperty()) {
                if (Objects.equals(prod.getCodProd(), linPed.getProducto().getCodProd())) {
                    cuentas += linPed.getCantidad();
                }
            }
        }
        return cuentas;
    }

    private void initColProduct() {
        filterProducto = new FilteredList<>(viewControl.getLogic().getProductos(), pro -> {
            return listaPedido.stream().anyMatch((ped) -> (recorrerPedidos(pro, ped)));
        });
        tcProductos.setCellValueFactory((TableColumn.CellDataFeatures<ProductoFX, String> producto) -> producto.getValue().nombreProperty());
    }

    private void initContenedores() {
        listaPedido.forEach((pedFX) -> {
            if (listaCont.stream().anyMatch(p -> p.getId() == pedFX.getLocal().getCodLocal())) {
                listaCont.stream().filter((dC) -> (dC.getId() == pedFX.getLocal().getCodLocal())).forEachOrdered((dC) -> {
                    rellenarMap(pedFX, dC);
                });
            } else {
                DashboardContainer dC = new DashboardContainer(pedFX.getLocal().getCodLocal(), "");
                rellenarMap(pedFX, dC);
                listaCont.add(dC);
            }
        });
    }

    private void rellenarMap(PedidoFX pedFX, DashboardContainer dC) {
        pedFX.getLineasPedido().stream().map((o) -> (LineaPedido) o).forEachOrdered((lp) -> {
            dC.addProduct((LineaPedido) lp);
        });
    }

    private void initColumnas() {
        TableColumn[] colPed = new TableColumn[listaCont.size()];
        for (int i = 0; i < listaCont.size(); i++) {
            colPed[i] = new TableColumn(listaCont.get(i).getId() + "");
            colPed[i].setCellValueFactory(new PropertyValueFactory<>(listaCont.get(i).getId() + ""));
            doColumnPedidoProducto(colPed[i]);
            tvCuadro.getColumns().add(1, colPed[i]);
        }
    }

    private EventHandler<? super MouseEvent> eventosClick(DashboardContainer dC, ProductoFX prod, Button btn) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    for (PedidoFX pedFX : listaPedido) {
                        if (pedFX.getLocal().getCodLocal() == dC.getId()) {
                            for (Object o : pedFX.getLineasPedido()) {
                                if (Objects.equals(((LineaPedido) o).getProducto().getCodProd(), prod.getCodProd())) {
                                    viewControl.getLogic().getHibControl().initTransaction();
                                    ((LineaPedido) o).setEstado(Constantes.EstadosLinea.PREPARADO.getNom());
                                    viewControl.getLogic().getHibControl().UpdateElement(o);
                                    btn.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00),"
                                            + "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);"
                                            + "-fx-background-radius: 6, 5;"
                                            + "-fx-background-insets: 0, 1;"
                                            + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"
                                            + "-fx-text-fill: #395306;");
                                }
                            }
                        }
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY) {

                }
            }
        };
    }

    @SuppressWarnings("Convert2Lambda")
    public void doColumnPedidoProducto(TableColumn pedidoTC) {
        pedidoTC.setCellValueFactory(new PropertyValueFactory<>("ProductoFX"));
        pedidoTC.setCellFactory(new Callback<TableColumn<ProductoFX, Void>, TableCell<ProductoFX, Void>>() {
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
                            Button btn = null;
                            for (DashboardContainer dC : listaCont) {
                                if (dC.getId() == Short.valueOf(pedidoTC.getText())) {
                                    int valor = dC.getCantidad(prod.getCodProd());
                                    if (valor > 0) {
                                        btn = new Button(valor + "");
                                        btn.setId("dashButton");
                                        btn.setOnMouseClicked(eventosClick(dC, prod, btn));
                                    } else {
                                        btn = new Button();
                                        btn.setDisable(true);
                                    }
                                }
                            }
                            HBox h = new HBox(1, btn);
                            h.alignmentProperty().setValue(Pos.CENTER);
                            setGraphic(h);
                        }
                    }
                };
                return cell;
            }
        });
    }

    @SuppressWarnings("Convert2Lambda")
    public void doColumnTotalProducto(TableColumn TotalTC) {
        TotalTC.setCellValueFactory(new PropertyValueFactory<>("ProductoFX"));
        TotalTC.setCellFactory(new Callback<TableColumn<ProductoFX, Void>, TableCell<ProductoFX, Void>>() {
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
}//fin de la clase
