package cl.nicolas.pokemon.dao;

import com.google.gson.*;
import cl.nicolas.pokemon.model.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAOFileImpl implements PokemonDAO {

    private static final String ARCHIVO_AGUA = "pokemonAgua.json";
    private static final String ARCHIVO_FUEGO = "pokemonFuego.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 1. ¿Por qué el <T> doble? (private <T> List<T> ...)
     * Aparecen dos <T> porque cada uno cumple una función completamente distinta:
     * El primer <T> (Declaración de Generacidad): Le dice al compilador de Java:
     * "Atención, este método no es un método común; es un método genérico que va a usar
     * una letra comodín llamada T". Sin este primer <T>, Java no sabría qué significa
     * la letra T en el resto de la firma.
     * El segundo List<T> (Tipo de Retorno): Especifica qué devuelve el método.
     * Si T resulta ser un PokemonAgua, este método devolverá una lista de Pokémon de agua
     * (List<PokemonAgua>). Si T resulta ser un PokemonFuego, devolverá una lista de
     * Pokémon de fuego (List<PokemonFuego>).
     * @return
     */

    //Java
    //      [1.Declaración]  [2.Retorno]
    //private     <T>          List<T>     leerLista(...)
    @Override
    public List<Pokemon> obtenerTodos() {
        List<Pokemon> lista = new ArrayList<>();
        lista.addAll(leerLista(ARCHIVO_AGUA, PokemonAgua[].class));
        lista.addAll(leerLista(ARCHIVO_FUEGO, PokemonFuego[].class));
        return lista;
    }

    /**
     * 2. ¿Qué es Class<T[]>?
     * Es la forma en que le pasas como parámetro a Java el tipo exacto de arreglo que debe leer del JSON.
     * T[]: Representa un arreglo del tipo genérico T (por ejemplo, PokemonAgua[] o PokemonFuego[]).
     * Class<...>: Gson no puede adivinar en tiempo de ejecución qué objeto debe
     * reconstruir desde el texto del JSON debido a un fenómeno de Java llamado
     * Type Erasure (borrado de tipos). Pasar Class<PokemonAgua[]> o Class<PokemonFuego[]>
     * como parámetro le entrega a Gson las "instrucciones de molde" para saber exactamente
     * en qué objeto transformar el texto.
     */
    private <T> List<T> leerLista(String archivo, Class<T[]> clazz) {
        File file = new File(archivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            T[] array = gson.fromJson(reader, clazz);
            return array != null ? new ArrayList<>(List.of(array)) : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean guardar(Pokemon pokemon) {
        if (pokemon instanceof PokemonAgua) {
            List<PokemonAgua> aguas = leerLista(ARCHIVO_AGUA, PokemonAgua[].class);
            aguas.add((PokemonAgua) pokemon);
            return escribir(ARCHIVO_AGUA, aguas);
        } else if (pokemon instanceof PokemonFuego) {
            List<PokemonFuego> fuegos = leerLista(ARCHIVO_FUEGO, PokemonFuego[].class);
            fuegos.add((PokemonFuego) pokemon);
            return escribir(ARCHIVO_FUEGO, fuegos);
        }
        return false;
    }

    @Override
    public boolean eliminar(Pokemon pokemon) {
        if (pokemon == null || pokemon.getNombre() == null) {
            return false;
        }

        String nombreBusqueda = pokemon.getNombre();

        List<PokemonAgua> aguas = leerLista(ARCHIVO_AGUA, PokemonAgua[].class);
        boolean aguaEliminada = aguas.removeIf(a -> a.getNombre().equalsIgnoreCase(nombreBusqueda));
        if (aguaEliminada) escribir(ARCHIVO_AGUA, aguas);

        List<PokemonFuego> fuegos = leerLista(ARCHIVO_FUEGO, PokemonFuego[].class);
        boolean fuegoEliminado = fuegos.removeIf(f -> f.getNombre().equalsIgnoreCase(nombreBusqueda));
        if (fuegoEliminado) escribir(ARCHIVO_FUEGO, fuegos);

        return aguaEliminada || fuegoEliminado;
    }

    private boolean escribir(String archivo, Object objeto) {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(objeto, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
