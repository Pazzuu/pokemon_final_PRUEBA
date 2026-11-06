package cl.nicolas.pokemon.model;

/**
 * Pokémon de tipo Fuego atendido en el Centro Pokémon.
 * Costo de curación: base $12.000; si es shiny (variante de color especial),
 * recargo del 15%.
 */
public class PokemonFuego extends Pokemon {

    private static final double COSTO_BASE = 12000;
    private static final double RECARGO_SHINY = 0.15;

    private boolean shiny;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    public PokemonFuego() {
    }

    public PokemonFuego(String nombre, int nivel, double peso, boolean shiny) {
        super(nombre, nivel, peso);
        setShiny(shiny);
    }

    public boolean isShiny() {
        return shiny;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
    }

    @Override
    public double calcularCostoCuracion() {
        if (shiny) {
            return COSTO_BASE + COSTO_BASE * RECARGO_SHINY;
        }
        return COSTO_BASE;
    }

    @Override
    public String getTipo() {
        return "PokemonFuego";
    }
}
