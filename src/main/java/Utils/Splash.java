package Utils;

import java.io.IOException;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Sempere
 */
public class Splash extends Preloader {

    private static Stage stage;

    private Scene createPreloaderScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Splash.fxml"));
        } catch (IOException ex) {
            System.err.println("NO SE HA ENCONTRADO EL FXML");
        }
        Scene scene = new Scene(root);
        return scene;
    }

    @Override
    public void start(Stage stage) {
        Splash.stage = new Stage();
        Splash.stage.setTitle(null);
        Scene preloaderScene = createPreloaderScene();
        Splash.stage.setScene(preloaderScene);
        Splash.stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        Splash.stage.setAlwaysOnTop(true);
        Splash.stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }

    public static void hide() {
        if (Splash.stage != null) {
            Splash.stage.close();
        }
    }
}//fin de la clase
