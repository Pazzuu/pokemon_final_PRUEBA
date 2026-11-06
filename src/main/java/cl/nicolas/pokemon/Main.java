package cl.nicolas.pokemon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));

        String css = Objects.requireNonNull(Main.class.getResource("css/styles.css")).toExternalForm();

        Scene scene = new Scene(fxmlLoader.load(), 650, 600);

        scene.getStylesheets().add(css);

        stage.setTitle("Centro Pokémon - Gestión de Pokémon");
        stage.setScene(scene);
        stage.show();
    }
}
