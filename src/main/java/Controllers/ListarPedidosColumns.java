package Controllers;

import BeansFX.LineaPedidoFX;
import BeansFX.PedidoFX;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * @author Jorge Sempere Jimenez
 */
public class ListarPedidosColumns {

    private final ListarPedidosController parentController;
    private ObservableList<String> estados;

    public ListarPedidosColumns(ListarPedidosController parentController) {
        this.parentController = parentController;
        estados = FXCollections.observableArrayList("EN PRODUCCION", "PREPARADO", "ENTREGADO", "ANULADO");
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

    @SuppressWarnings("Convert2Lambda")
    public void doColumnActionPedido(TableColumn accionesTC) {
        accionesTC.setCellValueFactory(new PropertyValueFactory<>("ProductoFX"));
        accionesTC.setCellFactory(new Callback<TableColumn<PedidoFX, Void>, TableCell<PedidoFX, Void>>() {
            @Override
            public TableCell<PedidoFX, Void> call(TableColumn<PedidoFX, Void> param) {
                TableCell<PedidoFX, Void> cell = new TableCell<PedidoFX, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            PedidoFX pedido = getTableView().getItems().get(getIndex());
                            Button btn = GlyphsDude.createIconButton(FontAwesomeIcon.EYE);
                            btn.setOnAction((event) -> {
                                parentController.getViewControl().getLogic().setPedido(pedido);
                                parentController.getViewControl().getmItModPed().fire();
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
                            Button btnOk = GlyphsDude.createIconButton(FontAwesomeIcon.CHECK);
                            btnOk.setTooltip(new Tooltip("Aceptar cambios en la linea de pedido"));
                            Button btnCancel = GlyphsDude.createIconButton(FontAwesomeIcon.CLOSE);
                            btnCancel.setTooltip(new Tooltip("Cancelar cambios en la linea de pedido"));
                            HBox h = new HBox(1, btnModif, btnOk, btnCancel);
                            h.alignmentProperty().setValue(Pos.CENTER_LEFT);
                            setGraphic(h);
                            btnOk.setVisible(linea.getEsEditable());
                            btnCancel.setVisible(linea.getEsEditable());
                            btnModif.setDisable(linea.getEsEditable());
                            btnModif.setOnAction(eventoModificar(linea, this));
                            btnOk.setOnAction(eventoAceptar(linea, this));
                            btnCancel.setOnAction(eventoCancelar(linea, this));
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

}//fin de clase
