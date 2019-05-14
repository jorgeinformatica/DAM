package Controllers;

import Beans.Producto;
import BeansFX.ProductoFX;
import Utils.MetodosEstaticos;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

/**
 * @author Jorge Sempere
 */
public class ProductosAMController implements Initializable {

    @FXML
    private Button nuevoBTN;
    @FXML
    private Button borrarBTN;
    @FXML
    private TextField nombreTXT;
    @FXML
    private TextField precioTXT;
    @FXML
    private TextField descripcionTXT;
    @FXML
    private LineChart<?, ?> lineasEvolutivo;
    @FXML
    private BarChart<?, ?> barrasComparativo;
    @FXML
    private PieChart tartaPorcentaje;
    @FXML
    private NumberAxis evoCanAx;
    @FXML
    private CategoryAxis evoDiaAx;
    @FXML
    private NumberAxis comCantAx;
    @FXML
    private CategoryAxis comLocAx;
    @FXML
    private TextField numCod;
    @FXML
    private ChoiceBox<String> ivaCB;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Label infoFiltro;
    @FXML
    private ComboBox<ProductoFX> cbElementos;
    @FXML
    private AnchorPane anchorPrincipal;

    private AAController viewControl;
    private ProductoFX producto;
    private FilteredList<ProductoFX> filteredItems;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void init(ProductoFX pro, ObservableList<Node> base) {
        configurarTxtPrecio();
        configurarTxtNombre();
        configurarTxtDescripcion();
        configurarComboIvas();
        configurarComboProductos(pro);
        configurarTxtFiltro();
        configurarGraficos();
        configurarBase(base);

    }

    void setViewControl(AAController aThis) {
        viewControl = aThis;
    }

    @FXML
    private void nuevoProducto(ActionEvent event) {
        viewControl.getLogic().getHibControl().initTransaction();
        Producto tempo = new Producto();
        tempo.setNombre("NUEVO");
        tempo.setTipoIva("GENERAL");
        tempo.setPrecio(BigDecimal.ZERO);
        tempo.setDescripcion("-");
        tempo.setEstado(Boolean.TRUE);
        viewControl.getLogic().getHibControl().save(tempo);
        producto = new ProductoFX(tempo);
        viewControl.getLogic().getProductos().add(producto);
        cbElementos.getSelectionModel().select(producto);
    }

    @FXML
    private void borrarProducto(ActionEvent event) {
        if (producto != null) {
            if (viewControl.getLogic().desactivarMsg(producto)) {
                viewControl.getLogic().getProductos().remove(producto);
            }
        }
    }

    private void configurarComboProductos(ProductoFX pro) {
        infoFiltro.setTooltip(new Tooltip("FILTRA LOS PRODUCTOS EN BASE AL TEXTO INTRODUCIDO"));
        filteredItems = new FilteredList<>(viewControl.getLogic().getProductos(), p -> true);
        cbElementos.setItems(filteredItems);
        cbElementos.valueProperty().addListener(
                (ObservableValue<? extends ProductoFX> o, ProductoFX oV, ProductoFX nV) -> {
                    if (nV != null) {
                        producto = nV;
                        refrescarVista();
                        if (oV != null) {
                            actualizarProducto(oV);
                        }
                    }
                });
        if (pro == null) {
            cbElementos.getSelectionModel().selectFirst();
        } else {
            cbElementos.getSelectionModel().select(pro);
        }
    }

    private void configurarComboIvas() {
        ObservableList ivas = FXCollections.observableArrayList("GENERAL", "REDUCIDO", "SUPERREDUCIDO");
        ivaCB.setItems(ivas);
        ivaCB.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (producto != null && !nV) {
                producto.setTipoIva(ivaCB.getValue());
            }
        });
    }

    private void configurarTxtPrecio() {
        precioTXT.lengthProperty().addListener(MetodosEstaticos.longMaxima(precioTXT, 6));
        precioTXT.setTextFormatter(MetodosEstaticos.soloDecimales());
        precioTXT.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (producto != null && !nV) {
                try {
                    producto.setPrecio(BigDecimal.valueOf(Double.valueOf(precioTXT.getText())));
                } catch (NumberFormatException e) {
                    System.out.println("ERROR EN EL PARSEO");
                }
            }
            if (precioTXT.getText().isEmpty()) {
                precioTXT.requestFocus();
            }
        });
    }

    private void configurarTxtNombre() {
        nombreTXT.lengthProperty().addListener(MetodosEstaticos.longMaxima(nombreTXT, 59));
        nombreTXT.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (producto != null && !nV) {
                producto.setNombre(nombreTXT.getText().toUpperCase());
            }
            if (nombreTXT.getText().isEmpty()) {
                nombreTXT.requestFocus();
            }
        });
    }

    private void configurarTxtDescripcion() {
        descripcionTXT.lengthProperty().addListener(MetodosEstaticos.longMaxima(descripcionTXT, 254));
        descripcionTXT.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (producto != null && !nV) {
                producto.setDescripcion(descripcionTXT.getText().toUpperCase());
            }
            if (nombreTXT.getText().isEmpty()) {
                nombreTXT.requestFocus();
            }
        });
    }

    private void configurarGraficos() {

    }

    private void actualizarProducto(ProductoFX p) {
        if (viewControl.getLogic().actualizarMsg(p)) {
            refrescarVista();
        }
    }

    private void configurarTxtFiltro() {
        txtFiltro.textProperty().addListener((obs, oldValue, newValue) -> {
            ProductoFX selected = cbElementos.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.getNombre().equals(newValue.toUpperCase())) {
                    filteredItems.setPredicate(item -> {
                        return ((ProductoFX) item).getNombre().contains(newValue.toUpperCase());
                    });
                }
            });
        });
    }

    private void refrescarVista() {
        nombreTXT.setText(producto.getNombre());
        numCod.setText(producto.getCodProd() + "");
        precioTXT.setText(producto.getPrecio() + "");
        ivaCB.getSelectionModel().select(producto.getTipoIva());
        descripcionTXT.setText(producto.getDescripcion());
    }

    private void configurarBase(ObservableList<Node> base) {
        ListChangeListener<Node> changeList = (ListChangeListener.Change<? extends Node> c) -> {
            actualizarProducto(producto);
        };
        base.addListener(changeList);
    }

}
