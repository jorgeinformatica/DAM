package Controllers;

import dam.proyecto.AAController;
import Beans.Producto;
import BeansFX.ProductoFX;
import Utils.Constantes;
import Utils.MetodosEstaticos;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

/**
 * @author Jorge Sempere
 */
public class ProductosAMController implements Initializable {

    @FXML
    private TextField nombreTXT;
    @FXML
    private TextField precioTXT;
    @FXML
    private TextField descripcionTXT;
    @FXML
    private LineChart<Long, String> lineasEvolutivo;
    @FXML
    private BarChart<Long, String> barrasComparativo;
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
    private Button btnAceptarCambio;

    private AAController viewControl;
    private ProductoFX producto;
    private FilteredList<ProductoFX> filteredItems;
    private SimpleDateFormat sdf;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAceptarCambio.setVisible(false);
        sdf = new SimpleDateFormat("dd-MM-yyyy");
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

    public void setViewControl(AAController aThis) {
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
            viewControl.getLogic().setProducto(null);
        }
    }

    private void configurarComboIvas() {
        ObservableList ivas = FXCollections.observableArrayList("GENERAL", "REDUCIDO", "SUPERREDUCIDO");
        ivaCB.setItems(ivas);
        ivaCB.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) -> {
            if (producto != null && !nV) {
                producto.setTipoIva(ivaCB.getValue());
                aceptarCambios();
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
                    aceptarCambios();
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
                aceptarCambios();
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
                aceptarCambios();
            }
            if (nombreTXT.getText().isEmpty()) {
                nombreTXT.requestFocus();
            }
        });
    }

    private void configurarGraficos() {
        Font font = new Font("Arial", 12);
        comLocAx.setTickLabelFont(font);
        comCantAx.setTickLabelFont(font);
        evoDiaAx.setTickLabelFont(font);
        evoCanAx.setTickLabelFont(font);
        comLocAx.setTickLabelRotation(-45);
        evoDiaAx.setTickLabelRotation(-45);
        tartaPorcentaje.setStartAngle(110);
        configurarEvolutivo(viewControl.getLogic().getHibControl().getList(Constantes.HQLSentencia.PRODUCTOEVOLUTIVO.getSentencia(), producto.getCodProd()));
        configurarBarrasLocales(viewControl.getLogic().getHibControl().getList(Constantes.HQLSentencia.PRODUCTOLOCALES.getSentencia(), producto.getCodProd()));
        configurarTartas(viewControl.getLogic().getHibControl().getList(Constantes.HQLSentencia.PRODUCTOSUBTOTAL.getSentencia(), producto.getCodProd()),
                viewControl.getLogic().getHibControl().getList(Constantes.HQLSentencia.PRODUCTOTOTAL.getSentencia(), producto.getCodProd()));
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
                    cbElementos.getSelectionModel().selectFirst();
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
        configurarEvolutivo(viewControl.getLogic().getHibControl().getList(Constantes.HQLSentencia.PRODUCTOEVOLUTIVO.getSentencia(), producto.getCodProd()));
        configurarBarrasLocales(viewControl.getLogic().getHibControl().getList(Constantes.HQLSentencia.PRODUCTOLOCALES.getSentencia(), producto.getCodProd()));
        configurarTartas(viewControl.getLogic().getHibControl().getList(Constantes.HQLSentencia.PRODUCTOSUBTOTAL.getSentencia(), producto.getCodProd()),
                viewControl.getLogic().getHibControl().getList(Constantes.HQLSentencia.PRODUCTOTOTAL.getSentencia(), producto.getCodProd()));
    }

    private void configurarBase(ObservableList<Node> base) {
        base.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                actualizarProducto(producto);
                base.removeListener(this);
            }
        });
    }

    private void aceptarCambios() {
        if (!btnAceptarCambio.isVisible() && producto.comprobarCambios()) {
            viewControl.getLogic().aceptarCambiosBtn(btnAceptarCambio, producto);
        }
    }

    private void configurarEvolutivo(List<Object> evolutivo) {
        lineasEvolutivo.getData().clear();
        if (!evolutivo.isEmpty()) {
            XYChart.Series series = new XYChart.Series();
            evolutivo.stream().map((o) -> (Object[]) o).forEachOrdered((elem) -> {
                series.getData().add(new XYChart.Data<>(sdf.format(elem[1]), (Long) elem[0]));
            });
            lineasEvolutivo.getData().add(series);
            toolTipBar(lineasEvolutivo.getData());
        }
    }

    private void configurarBarrasLocales(List<Object> locales) {
        barrasComparativo.getData().clear();
        XYChart.Series data = new XYChart.Series();
        if (!locales.isEmpty()) {
            for (Object loc : locales) {
                Object[] elem = (Object[]) loc;
                data.getData().add(new XYChart.Data<>((String) elem[1], (Long) elem[0]));
            }
        }
        barrasComparativo.getData().add(data);
        toolTipBar(barrasComparativo.getData());
    }

    private void configurarTartas(List<Object> totalProducto, List<Object> restoProductos) {
        if (!totalProducto.isEmpty()) {
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
            data.add(new PieChart.Data(producto.getNombre(), ((Long) totalProducto.get(0)) == null ? 0 : (Long) totalProducto.get(0)));
            data.add(new PieChart.Data("RESTO", (Long) restoProductos.get(0)));
            tartaPorcentaje.setData(data);
            tartaPorcentaje.getData().forEach((t) -> {
                toolTipPie(t, ((Long) totalProducto.get(0)) == null ? 0 : (Long) totalProducto.get(0) + (Long) restoProductos.get(0));
            });
        } else {
            tartaPorcentaje.getData().clear();
        }
    }

    private void toolTipPie(PieChart.Data dato, Long tot) {
        Tooltip ttPie = new Tooltip("Cantidad: " + Math.round(dato.getPieValue())
                + System.lineSeparator() + "Porcentaje: " + Math.round((100 * dato.getPieValue()) / tot) + "%");
        ttPie.setStyle("-fx-font: 12 arial;-fx-background-color: black; -fx-text-fill: whitesmoke;");
        Tooltip.install(dato.getNode(), ttPie);
    }

    private void toolTipBar(ObservableList<XYChart.Series<Long, String>> datos) {
        datos.forEach((series) -> {
            series.getData().forEach((data) -> {
                Tooltip ttBar = new Tooltip();
                ttBar.setText("Cantidad: " + String.valueOf(data.getYValue()));
                ttBar.setStyle("-fx-font: 12 arial;-fx-background-color: black; -fx-text-fill: whitesmoke;");
                Tooltip.install(data.getNode(), ttBar);
            });
        });
    }
}//fin de clase
