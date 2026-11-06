package cl.nicolas.pokemon.model;

/**
 * Representa a cualquier Pokémon atendido en el Centro Pokémon.
 * Define los datos comunes y delega en cada tipo de Pokémon el cálculo
 * del costo de curación.
 */
public abstract class Pokemon {

    private String nombre;
    private int nivel;
    private double peso;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    protected Pokemon() {
    }

    public Pokemon(String nombre, int nivel, double peso) {
        setNombre(nombre);
        setNivel(nivel);
        setPeso(peso);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        if (nivel < 1 || nivel > 100) {
            throw new IllegalArgumentException("El nivel debe estar entre 1 y 100.");
        }
        this.nivel = nivel;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        this.peso = peso;
    }

    /**
     * Calcula el costo de curación según las reglas propias de cada tipo de Pokémon.
     *
     * @return costo de la curación en pesos
     */
    public abstract double calcularCostoCuracion();

    /**
     * Retorna el nombre del tipo de Pokémon (por ejemplo, "PokemonAgua" o "PokemonFuego").
     *
     * @return tipo de Pokémon
     */
    public abstract String getTipo();

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Nivel: " + nivel;
    }
}
