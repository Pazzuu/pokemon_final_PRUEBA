# Centro Pokémon (antes Clínica Veterinaria PataVerde)

Proyecto JavaFX + Maven adaptado a la temática Pokémon. Es el mismo trabajo de
la clínica veterinaria, con la jerarquía de clases renombrada y la interfaz retematizada.

## Mapeo de la conversión

| Original (Veterinaria) | Pokémon | Notas |
|---|---|---|
| `Mascota` (abstracta) | `Pokemon` (abstracta) | `edad` → `nivel` (1-100), `calcularCostoConsulta()` → `calcularCostoCuracion()` |
| `Gato extends Mascota` | `PokemonFuego extends Pokemon` | `exterior` → `shiny` (recargo 15% si es shiny) |
| `Perro extends Mascota implements Adoptable` | `PokemonAgua extends Pokemon implements Intercambiable` | `raza` → `especie`, `vacunado` → `entrenado` (recargo 30% si no está entrenado) |
| Interfaz `Adoptable` (adopción) | Interfaz `Intercambiable` (intercambio entre entrenadores) | `marcarDisponibleParaAdopcion()` → `marcarDisponibleParaIntercambio()` |
| `MascotaDAO` / `MascotaDAOFileImpl` | `PokemonDAO` / `PokemonDAOFileImpl` | Persistencia Gson en `pokemonAgua.json` y `pokemonFuego.json` |
| Clínica Veterinaria PataVerde | Centro Pokémon | Título, textos de la UI y paleta CSS (rojo/azul/amarillo Pokémon) |

## Estructura

```
src/main/java/cl/nicolas/pokemon/
├── Main.java                  # JavaFX Application
├── Launcher.java              # Entry point alternativa
├── module-info.java
├── controller/MainController.java
├── dao/PokemonDAO.java
├── dao/PokemonDAOFileImpl.java
└── model/
    ├── Pokemon.java           # ¡Clase padre abstracta!
    ├── PokemonAgua.java       # (antes Perro)
    ├── PokemonFuego.java      # (antes Gato)
    └── Intercambiable.java    # (antes Adoptable)
src/main/resources/cl/nicolas/pokemon/
├── view.fxml
└── css/styles.css
```

## Reglas de negocio (iguales a las originales, retematizadas)

- **PokemonFuego**: curación base $12.000; si es **shiny**, recargo del 15% ($13.800).
- **PokemonAgua**: curación base $15.000; si **no está entrenado**, recargo del 30% ($19.500).
- `Pokemon` es abstracta: no se puede instanciar directamente, solo sus subclases.

## Cómo ejecutar

```bash
./mvnw clean javafx:run
```

Requiere JDK 21. Los archivos `pokemonAgua.json` y `pokemonFuego.json` se crean/leen
en el directorio de trabajo desde donde se ejecuta la app.
