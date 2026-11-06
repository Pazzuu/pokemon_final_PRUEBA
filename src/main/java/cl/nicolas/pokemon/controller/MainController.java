package cl.nicolas.pokemon.controller;

import cl.nicolas.pokemon.dao.PokemonDAO;
import cl.nicolas.pokemon.dao.PokemonDAOFileImpl;
import cl.nicolas.pokemon.model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtNivel;
    @FXML private TextField txtPeso;
    @FXML private TextField txtEspecie;
    @FXML private CheckBox chkEspecifico;
    @FXML private CheckBox chkIntercambio;

    @FXML private TableView<Pokemon> tblPokemones;
    @FXML private TableColumn<Pokemon, String> colTipo;
    @FXML private TableColumn<Pokemon, String> colNombre;
    @FXML private TableColumn<Pokemon, Integer> colNivel;
    @FXML private TableColumn<Pokemon, Double> colPeso;
    @FXML private TableColumn<Pokemon, String> colCosto; // String para aplicar formato $

    private final PokemonDAO pokemonDAO = new PokemonDAOFileImpl();
    private final ObservableList<Pokemon> listaPokemones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cmbTipo.setItems(FXCollections.observableArrayList("PokemonAgua", "PokemonFuego"));
        cmbTipo.getSelectionModel().selectFirst();

        colTipo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getClass().getSimpleName()));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colNivel.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getNivel()).asObject());
        colPeso.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getPeso()).asObject());

        // Muestra el costo con formato de moneda (ej: $15.000)
        colCosto.setCellValueFactory(cell ->
                new SimpleStringProperty(String.format("$%,.0f", cell.getValue().calcularCostoCuracion()))
        );

        cmbTipo.setOnAction(e -> actualizarCamposSegunTipo());
        actualizarCamposSegunTipo();

        cargarDatos();
    }

    private void actualizarCamposSegunTipo() {
        boolean esAgua = "PokemonAgua".equals(cmbTipo.getValue());
        if (esAgua) {
            txtEspecie.setVisible(true);
            txtEspecie.setManaged(true);
            chkEspecifico.setText("¿Está Entrenado?");
            chkIntercambio.setVisible(true);
            chkIntercambio.setManaged(true);
        } else {
            txtEspecie.setVisible(false);
            txtEspecie.setManaged(false);
            chkEspecifico.setText("¿Es Shiny?");
            chkIntercambio.setVisible(false);
            chkIntercambio.setManaged(false);
        }
    }

    private void cargarDatos() {
        listaPokemones.setAll(pokemonDAO.obtenerTodos());
        tblPokemones.setItems(listaPokemones);
    }

    @FXML
    private void handleRegistrar() {
        try {
            String nombre = txtNombre.getText().trim();
            int nivel = Integer.parseInt(txtNivel.getText().trim());
            double peso = Double.parseDouble(txtPeso.getText().trim());
            boolean especifico = chkEspecifico.isSelected();

            Pokemon nuevoPokemon;
            if ("PokemonAgua".equals(cmbTipo.getValue())) {
                String especie = txtEspecie.getText().trim();

                // Instanciación según los 5 parámetros de la clase PokemonAgua
                PokemonAgua agua = new PokemonAgua(nombre, nivel, peso, especie, especifico);

                // Usa el método de la interfaz Intercambiable
                if (chkIntercambio.isSelected()) {
                    agua.marcarDisponibleParaIntercambio();
                }
                nuevoPokemon = agua;
            } else {
                nuevoPokemon = new PokemonFuego(nombre, nivel, peso, especifico);
            }

            if (pokemonDAO.guardar(nuevoPokemon)) {
                cargarDatos();
                limpiarFormulario();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Pokémon registrado correctamente.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Datos", "El nivel y el peso deben ser valores numéricos válidos.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Validación de Modelo", e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {
        Pokemon seleccionado = tblPokemones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Selecciona un Pokémon de la tabla para eliminar.");
            return;
        }

        if (pokemonDAO.eliminar(seleccionado)) {
            cargarDatos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Pokémon eliminado correctamente.");
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el Pokémon.");
        }
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtNivel.clear();
        txtPeso.clear();
        txtEspecie.clear();
        chkEspecifico.setSelected(false);
        chkIntercambio.setSelected(false);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
