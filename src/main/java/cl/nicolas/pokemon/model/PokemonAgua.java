package cl.nicolas.pokemon.model;

/**
 * Pokémon de tipo Agua atendido en el Centro Pokémon.
 * Participa en el programa de intercambio entre entrenadores.
 * Costo de curación: base $15.000; si no está entrenado, recargo del 30%.
 */
public class PokemonAgua extends Pokemon implements Intercambiable {

    private static final double COSTO_BASE = 15000;
    private static final double RECARGO_NO_ENTRENADO = 0.30;

    private String especie;
    private boolean entrenado;
    private boolean disponibleIntercambio;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    public PokemonAgua() {
    }

    public PokemonAgua(String nombre, int nivel, double peso, String especie, boolean entrenado) {
        super(nombre, nivel, peso);
        setEspecie(especie);
        setEntrenado(entrenado);
        this.disponibleIntercambio = false;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public boolean isEntrenado() {
        return entrenado;
    }

    public void setEntrenado(boolean entrenado) {
        this.entrenado = entrenado;
    }

    @Override
    public boolean estaDisponibleParaIntercambio() {
        return disponibleIntercambio;
    }

    @Override
    public void marcarDisponibleParaIntercambio() {
        this.disponibleIntercambio = true;
    }

    @Override
    public double calcularCostoCuracion() {
        if (entrenado) {
            return COSTO_BASE;
        }
        return COSTO_BASE + COSTO_BASE * RECARGO_NO_ENTRENADO;
    }

    @Override
    public String getTipo() {
        return "PokemonAgua";
    }
}
