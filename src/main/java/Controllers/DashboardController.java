package Controllers;

import Beans.LineaPedido;
import Beans.Pedido;
import Beans.Producto;
import BeansFX.PedidoFX;
import BeansFX.ProductoFX;
import Utils.Constantes;
import Utils.MetodosEstaticos;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    @FXML
    private DatePicker dpFecha;

    private AAController viewControl;
    private ObservableList<PedidoFX> listaPedido;
    private FilteredList<PedidoFX> filterPedido;
    private FilteredList<ProductoFX> filterProducto;
    private ObservableList<DashboardContainer> listaCont;
    private Date fecha;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fecha = Date.from(Instant.now());
        dpFecha.setOnAction((ActionEvent event) -> {
            fecha = MetodosEstaticos.ToDate(dpFecha.getValue());
            init();
        });
        dpFecha.setValue(LocalDate.now());
    }

    void init() {
        listaCont = FXCollections.observableArrayList();
        listaPedido = FXCollections.observableArrayList();
        FXCollections.observableList(viewControl.getLogic().getHibControl()
                .getList(Pedido.class, " elemento.fechaEntrega like '" + new SimpleDateFormat("yyyy-MM-dd").format(fecha) + "%'"))
                .forEach((Object ped) -> {
                    listaPedido.add(new PedidoFX((Pedido) ped));
                });
        initContenedores();
        initColProduct();
        initColumnas();
        doColumnTotalProducto(tcTotal);
        tvCuadro.getItems().clear();
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
            DashboardContainer dC = new DashboardContainer(pedFX.getLocal().getCodLocal(), "");
            rellenarMap(pedFX, dC);
            listaCont.add(dC);
        });
    }

    private void rellenarMap(PedidoFX pedFX, DashboardContainer dC) {
        pedFX.getLineasPedido().stream().map((o) -> (LineaPedido) o).forEachOrdered((lp) -> {
            dC.addProduct((LineaPedido) lp);
        });
    }

    private void initColumnas() {
        if (tvCuadro.getColumns().size() > 2) {
            tvCuadro.getColumns().remove(1, tvCuadro.getColumns().size() - 1);
        }
        TableColumn[] colPed = new TableColumn[listaCont.size()];
        for (int i = 0; i < listaCont.size(); i++) {
            colPed[i] = new TableColumn(listaCont.get(i).getId() + "");
            colPed[i].setCellValueFactory(new PropertyValueFactory<>(listaCont.get(i).getId() + ""));
            doColumnPedidoProducto(colPed[i]);
            tvCuadro.getColumns().add(1, colPed[i]);
        }

    }

    private void configurarBoton(DashboardContainer dC, ProductoFX prod, Button btn) {
        String[] split = dC.getCantidad(prod.getCodProd()).split("|");
        if (split[0].equals("0")) {
            btn.setId(Constantes.Estados.ENPRODUCCION.getId());
        } else {
            btn.setId(Constantes.Estados.PREPARADO.getId());
        }
    }

    private void marcarPreparado(DashboardContainer dC, ProductoFX prod) {
        for (PedidoFX pedFX : listaPedido) {
            if (pedFX.getLocal().getCodLocal() == dC.getId()) {
                for (Object o : pedFX.getLineasPedido()) {
                    if (Objects.equals(((LineaPedido) o).getProducto().getCodProd(), prod.getCodProd())) {
                        viewControl.getLogic().getHibControl().initTransaction();
                        ((LineaPedido) o).setEstado(Constantes.Estados.PREPARADO.getNom());
                        viewControl.getLogic().getHibControl().UpdateElement(o);
                    }
                }
            }
        }
    }

    private void marcarPreparadoParcial(DashboardContainer dC, ProductoFX prod, int v) {
        int valor = v;
        for (PedidoFX pedFX : listaPedido) {
            if (pedFX.getLocal().getCodLocal() == dC.getId()) {
                for (Object o : pedFX.getLineasPedido()) {
                    if (Objects.equals(((LineaPedido) o).getProducto().getCodProd(), prod.getCodProd()) && valor > 0) {
                        if (((LineaPedido) o).getCantidad() < valor) {
                            viewControl.getLogic().getHibControl().initTransaction();
                            ((LineaPedido) o).setEstado(Constantes.Estados.PREPARADO.getNom());
                            viewControl.getLogic().getHibControl().UpdateElement(o);
                            valor -= ((LineaPedido) o).getCantidad();
                        } else {
                            viewControl.getLogic().getHibControl().initTransaction();
                            ((LineaPedido) o).setCantidad((short) (((LineaPedido) o).getCantidad() - valor));
                            viewControl.getLogic().getHibControl().UpdateElement(o);
                            lineaExtra(dC, prod, valor, Constantes.Estados.PREPARADO.getNom());
                        }
                    }
                }
            }
        }
    }

    private ContextMenu crearContextMenu(DashboardContainer dC, ProductoFX prod, Button btn) {
        ContextMenu cm = new ContextMenu();
        MenuItem mi_1 = new MenuItem("Definir cantidad preparada");
        if (btn.getId().equals(Constantes.Estados.PREPARADO.getId())) {
            mi_1.setDisable(true);
        } else {
            mi_1.setDisable(false);
        }
        mi_1.setOnAction((event) -> {
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle(null);
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setContentText("Introduce la cantidad preparada:");
            dialog.getEditor().setTextFormatter(MetodosEstaticos.soloNumeros());
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String[] split = btn.getText().split("[|]");
                if (Integer.valueOf(split[1]) < Integer.valueOf(result.get())) {
                    int valor = Integer.valueOf(result.get()) - Integer.valueOf(split[1]);
                    btn.setText(result.get() + "|" + result.get());
                    marcarPreparado(dC, prod);
                    lineaExtra(dC, prod, valor, Constantes.Estados.ENPRODUCCION.getNom());
                    btn.setId(Constantes.Estados.PREPARADO.getId());
                } else {
                    btn.setText(result.get() + "|" + split[1]);
                    marcarPreparadoParcial(dC, prod, Integer.valueOf(result.get()));
                }
            }
        });
        MenuItem mi_2 = new MenuItem("Revisar estado lineas");
        mi_2.setOnAction((event) -> {
        });
        cm.getItems().addAll(mi_1, mi_2);
        return cm;
    }

    private void lineaExtra(DashboardContainer dC, ProductoFX prod, int valor, String estado) {
        for (PedidoFX pedFX : listaPedido) {
            if (pedFX.getLocal().getCodLocal() == dC.getId()) {
                viewControl.getLogic().getHibControl().initTransaction();
                LineaPedido tempo = new LineaPedido();
                tempo.setPedido((Pedido) pedFX.getBean());
                tempo.setProducto((Producto) prod.getBean());
                tempo.setCantidad((short) valor);
                tempo.setEstado(estado);
                viewControl.getLogic().getHibControl().save(tempo);
                viewControl.getLogic().getHibControl().refresco((Pedido) pedFX.getBean());
            }
        }
    }

    private EventHandler<? super MouseEvent> eventosClick(DashboardContainer dC, ProductoFX prod, Button btn) {
        return (MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                marcarPreparado(dC, prod);
                btn.setId(Constantes.Estados.PREPARADO.getId());
                String[] texto = dC.getCantidad(prod.getCodProd()).split("[|]");
                btn.setText(texto[1] + "|" + texto[1]);
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                ContextMenu cm = crearContextMenu(dC, prod, btn);
                cm.show(tvCuadro, event.getScreenX(), event.getScreenY());
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
                                    String valor = dC.getCantidad(prod.getCodProd());
                                    if (!valor.isEmpty()) {
                                        btn = new Button(valor);
                                        configurarBoton(dC, prod, btn);
                                        btn.setOnMouseClicked(eventosClick(dC, prod, btn));
                                    }
                                }
                            }
                            HBox h = new HBox(1, btn != null ? btn : new Label());
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

    private void initCalendar() {
        fecha = Date.from(Instant.now());
        dpFecha.setOnAction((ActionEvent event) -> {
            fecha = MetodosEstaticos.ToDate(dpFecha.getValue());
        });
        dpFecha.setValue(LocalDate.now());
    }

}//fin de la clase
