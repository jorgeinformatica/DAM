package Controllers;

import BeansFX.LineaPedidoFX;
import BeansFX.ProductoFX;
import Utils.MetodosEstaticos;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * @author Jorge Sempere Jimenez
 */
public class PedidosAMColumns {
    
    private final PedidosAMController parentController;
    
    public PedidosAMColumns(PedidosAMController parentController) {
        this.parentController = parentController;
    }
    
    @SuppressWarnings("Convert2Lambda")
    public void doColumnActionsPedido(TableColumn accionesTC) {
        accionesTC.setCellValueFactory(new PropertyValueFactory<>("LineaPedidoFX"));
        accionesTC.setCellFactory(new Callback<TableColumn<LineaPedidoFX, Void>, TableCell<LineaPedidoFX, Void>>() {
            @Override
            public TableCell<LineaPedidoFX, Void> call(TableColumn<LineaPedidoFX, Void> param) {
                TableCell<LineaPedidoFX, Void> cell = new TableCell<LineaPedidoFX, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            if (getTableView().getItems().size() - 1 == getIndex()) {
                                LineaPedidoFX linea = getTableView().getItems().get(getIndex());
                                Button btn = GlyphsDude.createIconButton(FontAwesomeIcon.PLUS);
                                btn.setTooltip(new Tooltip("Añadir linea de pedido"));
                                HBox h = new HBox(1, btn);
                                h.alignmentProperty().setValue(Pos.CENTER);
                                setGraphic(h);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
                return cell;
            }
        });
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
    
    @SuppressWarnings("Convert2Lambda")
    public void doColumnProductoPedido(TableColumn productosTC) {
        productosTC.setCellValueFactory(new PropertyValueFactory<>("LineaPedidoFX"));
        productosTC.setCellFactory(new Callback<TableColumn<LineaPedidoFX, Void>, TableCell<LineaPedidoFX, Void>>() {
            @Override
            public TableCell<LineaPedidoFX, Void> call(TableColumn<LineaPedidoFX, Void> param) {
                TableCell<LineaPedidoFX, Void> cell = new TableCell<LineaPedidoFX, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            LineaPedidoFX linea = getTableView().getItems().get(getIndex());
                            ComboBox<ProductoFX> cbProductos = new ComboBox<>(parentController.getViewControl().getLogic().getProductos().sorted());
                            HBox h = new HBox(1, cbProductos);
                            cbProductos.getSelectionModel().select(linea.getProducto());
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
    public void doColumnCantidadPedido(TableColumn cantidadesTC) {
        cantidadesTC.setCellValueFactory(new PropertyValueFactory<>("LineaPedidoFX"));
        cantidadesTC.setCellFactory(new Callback<TableColumn<LineaPedidoFX, Void>, TableCell<LineaPedidoFX, Void>>() {
            @Override
            public TableCell<LineaPedidoFX, Void> call(TableColumn<LineaPedidoFX, Void> param) {
                TableCell<LineaPedidoFX, Void> cell = new TableCell<LineaPedidoFX, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            LineaPedidoFX linea = getTableView().getItems().get(getIndex());
                            TextField tf = new TextField();
                            tf.setTextFormatter(MetodosEstaticos.soloNumeros());
                            tf.setText(linea.getCantidad().toString());
                            HBox h = new HBox(1, tf);
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
    public void doColumnEstadoPedido(TableColumn estadosTC) {
        estadosTC.setCellValueFactory(new PropertyValueFactory<>("LineaPedidoFX"));
        estadosTC.setCellFactory(new Callback<TableColumn<LineaPedidoFX, Void>, TableCell<LineaPedidoFX, Void>>() {
            @Override
            public TableCell<LineaPedidoFX, Void> call(TableColumn<LineaPedidoFX, Void> param) {
                TableCell<LineaPedidoFX, Void> cell = new TableCell<LineaPedidoFX, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            LineaPedidoFX linea = getTableView().getItems().get(getIndex());
                            ComboBox<String> cbEstate = new ComboBox<>(parentController.getEstados());
                            HBox h = new HBox(1, cbEstate);
                            cbEstate.getSelectionModel().select(linea.getEstado());
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
