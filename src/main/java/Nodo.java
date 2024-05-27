public class Nodo {
    private final char vertice;
    private final int peso;
    private final int camino;
    private Nodo liga;

    public Nodo(char vertice, int peso, int camino, Nodo liga) {
        this.vertice = vertice;
        this.peso = peso;
        this.liga = liga;
        this.camino = camino;
    }

    public char getVertice() {
        return vertice;
    }

    public int getPeso() {
        return peso;
    }

    public int getCamino() {
        return camino;
    }

    public Nodo getLiga() {
        return liga;
    }

    public void setLiga(Nodo liga) {
        this.liga = liga;
    }
}