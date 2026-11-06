package cl.nicolas.pokemon.model;

/**
 * Contrato que cumplen los Pokémon que participan en el programa de
 * intercambio entre entrenadores del Centro Pokémon.
 */
public interface Intercambiable {

    /**
     * Indica si el Pokémon se encuentra disponible para ser intercambiado.
     *
     * @return true si está disponible para intercambio
     */
    boolean estaDisponibleParaIntercambio();

    /**
     * Registra que el Pokémon está disponible para intercambio.
     */
    void marcarDisponibleParaIntercambio();
}
