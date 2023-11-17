import java.util.*;
/**
 * Clase que representa un Grafo ponderado no dirigido. Utiliza listas de adyacencia para almacenar
 * las conexiones entre nodos y proporciona funcionalidades como la adición de aristas, la visualización
 * del grafo y la ejecución del algoritmo de Dijkstra para encontrar rutas más cortas.
 */
class Grafo {
    private List<List<Nodo>> listaAdyacencia;
    /**
     * Constructor que inicializa la lista de adyacencia del grafo con la cantidad especificada de vértices.
     *
     * @param vertices Cantidad de vértices en el grafo.
     */
    public Grafo(int vertices) {
        listaAdyacencia = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            listaAdyacencia.add(new LinkedList<>());
        }
    }
    /**
     * Método que devuelve la lista de adyacencia del grafo.
     *
     * @return Lista de adyacencia del grafo.
     */
    public List<List<Nodo>> getListaAdyacencia() {
        return listaAdyacencia;
    }
    /**
     * Método que agrega una arista entre dos nodos con el peso especificado.
     * La arista se agrega en ambas direcciones, ya que el grafo es no dirigido.
     *
     * @param src   Nodo de origen.
     * @param dest  Nodo de destino.
     * @param peso  Peso de la arista.
     */
    public void agregarArista(int src, int dest, int peso) {
        listaAdyacencia.get(src).add(new Nodo(dest, peso));
        listaAdyacencia.get(dest).add(new Nodo(src, peso)); // Grafo no dirigido
    }
    /**
     * Método que muestra la representación del grafo en consola.
     * Muestra la lista de adyacencia y sus respectivas aristas.
     */
    public void mostrarGrafo() {
        System.out.println("Grafo:");
        for (int i = 0; i < listaAdyacencia.size(); i++) {
            List<Nodo> lista = listaAdyacencia.get(i);
            System.out.print(i + " -> ");
            for (Nodo nodo : lista) {
                System.out.print(nodo.nodo + "(" + nodo.peso + ") ");
            }
            System.out.println();
        }
    }
    /**
     * Método que ejecuta el algoritmo de Dijkstra para encontrar la ruta más corta desde un nodo de origen
     * hasta un nodo de destino. Imprime la ruta y el costo en la consola.
     *
     * @param origen   Nodo de origen.
     * @param destino  Nodo de destino.
     * @return Lista de nodos que forman la ruta más corta desde el origen hasta el destino.
     */
    public List<Integer> dijkstra(int origen, int destino) {
        int vertices = listaAdyacencia.size();
        int[] distancias = new int[vertices];
        int[] predecesores = new int[vertices];
        boolean[] visitados = new boolean[vertices];

        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecesores, -1);
        distancias[origen] = 0;

        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(n -> n.peso));
        colaPrioridad.add(new Nodo(origen, 0));

        while (!colaPrioridad.isEmpty()) {
            int u = colaPrioridad.poll().nodo;
            if (!visitados[u]) {
                visitados[u] = true;
                for (Nodo vecino : listaAdyacencia.get(u)) {
                    int v = vecino.nodo;
                    int peso = vecino.peso;
                    if (!visitados[v] && distancias[u] + peso < distancias[v]) {
                        distancias[v] = distancias[u] + peso;
                        predecesores[v] = u;
                        colaPrioridad.add(new Nodo(v, distancias[v]));
                    }
                }
            }
        }

        List<Integer> camino = new ArrayList<>();
        for (int v = destino; v != -1; v = predecesores[v]) {
            camino.add(v);
        }
        Collections.reverse(camino);

        for (int i = 0; i < camino.size(); i++) {
            System.out.print(camino.get(i));
            if (i < camino.size() - 1) System.out.print(" -> ");
        }
        System.out.println("\nCosto: " + distancias[destino]);

        return camino;
    }

    /**
     * Clase interna que representa un nodo en el grafo con su respectivo peso.
     */
    static class Nodo {
        int nodo;
        int peso;

        /**
         * Constructor que inicializa un nodo con un identificador y un peso.
         *
         * @param nodo Identificador del nodo.
         * @param peso Peso asociado al nodo.
         */
        Nodo(int nodo, int peso) {
            this.nodo = nodo;
            this.peso = peso;
        }
    }
}