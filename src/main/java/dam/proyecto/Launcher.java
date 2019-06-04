package dam.proyecto;

import Controllers.AAController;
import Utils.MetodosEstaticos;
import com.sun.javafx.application.LauncherImpl;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Jorge Sempere Jimenez
 */
public class Launcher extends Application {

    private AAController viewController;
    private Scene scene;

    public static void main(String[] args) {
        LauncherImpl.launchApplication(Launcher.class, Splash.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.setTitle(null);
        primaryStage.setFullScreenExitHint(null);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        viewController.setPrimaryStage(primaryStage);
        primaryStage.show();
        primaryStage.setOnHiding((WindowEvent event) -> {
            MetodosEstaticos.SalirApp(primaryStage);
        });
    }

    @Override
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Inicio.fxml"));
            Parent root = loader.load();
            viewController = loader.<AAController>getController();
            scene = new Scene(root);
            scene.getStylesheets().add("/styles/Buttons.css");
            scene.getStylesheets().add("/styles/Base.css");
            new LogicController(viewController);
        } catch (IOException ex) {
            System.err.println("NO SE HA ENCONTRADO EL FXML");
        }
    }

}//Fin de clase 
