package Controllers;

import Beans.LineaPedido;
import Beans.Pedido;
import Beans.Producto;
import BeansFX.LineaPedidoFX;
import BeansFX.ProductoFX;
import Utils.MetodosEstaticos;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private final ObservableList<String> estados;

    public PedidosAMColumns(PedidosAMController parentController) {
        this.parentController = parentController;
        estados = FXCollections.observableArrayList("EN PRODUCCION", "PREPARADO", "ENTREGADO", "ANULADO");
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
                            LineaPedidoFX linea = getTableView().getItems().get(getIndex());
                            Button btnModif = GlyphsDude.createIconButton(FontAwesomeIcon.EDIT);
                            btnModif.setTooltip(new Tooltip("Modificar linea de pedido"));
                            Button btnBorrar = GlyphsDude.createIconButton(FontAwesomeIcon.ERASER);
                            btnBorrar.setTooltip(new Tooltip("Borrar linea de pedido"));
                            Button btnOk = GlyphsDude.createIconButton(FontAwesomeIcon.CHECK);
                            btnOk.setTooltip(new Tooltip("Aceptar cambios en la linea de pedido"));
                            Button btnCancel = GlyphsDude.createIconButton(FontAwesomeIcon.CLOSE);
                            btnCancel.setTooltip(new Tooltip("Cancelar cambios en la linea de pedido"));
                            HBox h = new HBox(1, btnModif, btnBorrar, btnOk, btnCancel);
                            if (getTableView().getItems().size() - 1 == getIndex()) {
                                Button btnNuevo = GlyphsDude.createIconButton(FontAwesomeIcon.PLUS);
                                btnNuevo.setTooltip(new Tooltip("Añadir linea de pedido"));
                                btnNuevo.setOnAction(eventoNuevo());
                                h.getChildren().add(btnNuevo);
                                btnNuevo.setDisable(linea.getEsEditable());
                            }
                            h.alignmentProperty().setValue(Pos.CENTER_LEFT);
                            setGraphic(h);
                            btnOk.setVisible(linea.getEsEditable());
                            btnCancel.setVisible(linea.getEsEditable());
                            btnBorrar.setDisable(linea.getEsEditable());
                            btnModif.setDisable(linea.getEsEditable());
                            btnModif.setOnAction(eventoModificar(linea, this));
                            btnOk.setOnAction(eventoAceptar(linea, this));
                            btnCancel.setOnAction(eventoCancelar(linea, this));
                            btnBorrar.setOnAction(eventoEliminar(linea));
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
                            cbProductos.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
                                if (!nV) {
                                    linea.setProducto(cbProductos.getValue());
                                }
                            });
                            HBox h = new HBox(1, cbProductos);
                            cbProductos.getSelectionModel().select(linea.getProducto());
                            cbProductos.setDisable(!linea.getEsEditable());
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
                            tf.alignmentProperty().setValue(Pos.CENTER);
                            tf.setText(linea.getCantidad().toString());
                            tf.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
                                if (!nV) {
                                    linea.setCantidad(Short.valueOf(tf.getText()));
                                }
                                if (tf.getText().isEmpty()) {
                                    tf.requestFocus();
                                }
                            });
                            HBox h = new HBox(1, tf);
                            tf.setEditable(linea.getEsEditable());
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
                            ComboBox<String> cbEstate = new ComboBox<>(estados);
                            HBox h = new HBox(1, cbEstate);
                            cbEstate.getSelectionModel().select(linea.getEstado());
                            cbEstate.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
                                if (!nV) {
                                    linea.setEstado(cbEstate.getValue());
                                }
                            });
                            cbEstate.setDisable(!linea.getEsEditable());
                            h.alignmentProperty().setValue(Pos.CENTER);
                            setGraphic(h);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public EventHandler<ActionEvent> eventoModificar(LineaPedidoFX linea, TableCell<LineaPedidoFX, Void> aThis) {
        return (ActionEvent event) -> {
            linea.setEsEditable(true);
            aThis.getTableView().refresh();
        };
    }

    public EventHandler<ActionEvent> eventoAceptar(LineaPedidoFX linea, TableCell<LineaPedidoFX, Void> aThis) {
        return (ActionEvent event) -> {
            if (linea.comprobarCambios()) {
                parentController.getViewControl().getLogic().getHibControl().initTransaction();
                linea.getBean().actualizarDatos(linea);
                parentController.getViewControl().getLogic().getHibControl().UpdateElement(linea.getBean());
            }
            linea.setEsEditable(false);
            aThis.getTableView().refresh();
        };
    }

    public EventHandler<ActionEvent> eventoCancelar(LineaPedidoFX linea, TableCell<LineaPedidoFX, Void> aThis) {
        return (ActionEvent event) -> {
            if (linea.comprobarCambios()) {
                linea.sinCambios();
            }
            linea.setEsEditable(false);
            aThis.getTableView().refresh();
        };
    }

    public EventHandler<ActionEvent> eventoEliminar(LineaPedidoFX linea) {
        return (ActionEvent event) -> {
            parentController.getViewControl().getLogic().getHibControl().initTransaction();
            parentController.getViewControl().getLogic().getHibControl().remove(linea.getBean());
            parentController.getLinPedido().remove(linea);
        };
    }

    public EventHandler<ActionEvent> eventoNuevo() {
        return (ActionEvent event) -> {
            if (parentController.getLocal() != null && parentController.getPedido() != null) {
                parentController.getViewControl().getLogic().getHibControl().initTransaction();
                LineaPedido tempo = new LineaPedido();
                tempo.setPedido((Pedido) parentController.getPedido().getBean());
                tempo.setProducto((Producto) parentController.getViewControl().getLogic().getProductos().get(0).getBean());
                tempo.setCantidad((short) 1);
                tempo.setEstado("EN PRODUCCION");
                parentController.getViewControl().getLogic().getHibControl().save(tempo);
                parentController.getViewControl().getLogic().getHibControl().refresco(parentController.getPedido().getBean());
                parentController.refrescarVista();
                
            }

        };
    }

}//fin de clase
