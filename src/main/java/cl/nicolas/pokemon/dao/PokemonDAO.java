package cl.nicolas.pokemon.dao;

import cl.nicolas.pokemon.model.Pokemon;
import java.util.List;

public interface PokemonDAO {
    List<Pokemon> obtenerTodos();
    boolean guardar(Pokemon pokemon);
    boolean eliminar(Pokemon pokemon);
}
