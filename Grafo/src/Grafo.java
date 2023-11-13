import java.util.*;

class Grafo {
    private List<List<Nodo>> listaAdyacencia;

    public Grafo(int vertices) {
        listaAdyacencia = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            listaAdyacencia.add(new LinkedList<>());
        }
    }
    public List<List<Nodo>> getListaAdyacencia() {
        return listaAdyacencia;
    }

    public void agregarArista(int src, int dest, int peso) {
        listaAdyacencia.get(src).add(new Nodo(dest, peso));
        listaAdyacencia.get(dest).add(new Nodo(src, peso)); // Grafo no dirigido
    }

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


    static class Nodo {
        int nodo;
        int peso;

        Nodo(int nodo, int peso) {
            this.nodo = nodo;
            this.peso = peso;
        }
    }
}