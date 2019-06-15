package Utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * @author Jorge Sempere Jimenez
 */
public class MetodosEstaticos {

    /**
     * @param stage necesario para gestionar la salida de la aplicacion
     */
    public static void SalirApp(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea salir?");
        alert.initOwner(stage);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * @param date la fecha a transformar
     * @return la fecha en tipo "LocalDate"
     */
    public static LocalDate ToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     *
     * @param date la fecha a transformar
     * @return la fecha en tipo "Date"
     */
    public static Date ToDate(LocalDate date) {
        return Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault())));
    }

    /**
     * @param date la fecha y hora a transformar
     * @return la fecha y hora en tipo "LocalDateTime"
     */
    public static LocalDateTime ToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * filtra que solo se acepten numeros decimales
     *
     * @return el filtro a aplicar.
     */
    public static TextFormatter soloDecimales() {
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#.00", simbolo);
        return new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);
            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        });
    }

    /**
     * filtra que solo se acepten numeros enteros
     *
     * @return el filtro a aplicar
     */
    public static TextFormatter soloNumeros() {
        DecimalFormat format = new DecimalFormat("##");
        return new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);
            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        });
    }

    /**
     * Limita el numero de caracteres que acepta el TextField
     *
     * @param campo es el TextField que se va a asignar el listener
     * @param longitud numero de caracteres que aceptará
     * @return el listener con la configuracion establecida
     */
    public static ChangeListener<Number> longMaxima(TextField campo, int longitud) {
        return (ObservableValue<? extends Number> o, Number oV, Number nV) -> {
            if (nV.intValue() > oV.intValue()) {
                if (campo.getText().length() >= longitud) {
                    campo.setText(campo.getText().substring(0, longitud));
                }
            }
        };
    }

}//fin de clase
