module cl.nicolas.pokemon {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens cl.nicolas.pokemon.controller to javafx.fxml;
    opens cl.nicolas.pokemon.model to com.google.gson, javafx.base;
    exports cl.nicolas.pokemon;
}
