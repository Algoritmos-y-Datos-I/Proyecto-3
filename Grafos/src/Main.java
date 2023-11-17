import java.util.*;

/**
 * Clase principal que demuestra la generación aleatoria de un grafo, muestra el grafo,
 * y calcula y visualiza la ruta más rápida entre nodos utilizando el algoritmo de Dijkstra.
 */
public class Main {

    /**
     * Método principal que ejecuta la demostración de la generación aleatoria del grafo
     * y el cálculo de la ruta más rápida.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        int vertices = 30;
        Grafo grafo = new Grafo(vertices);
        Random random = new Random();

        // Genera aristas aleatorias en el grafo
        for (int i = 0; i < vertices; i++) {
            int numAristas = random.nextInt(vertices / 15) + 1; // Asegura al menos una arista
            Set<Integer> destinosConectados = new HashSet<>();

            for (int j = 0; j < numAristas; j++) {
                int destino;
                do {
                    destino = random.nextInt(vertices);
                } while (destino == i || destinosConectados.contains(destino)); // Asegura que no hay auto bucles y duplicados

                destinosConectados.add(destino);
                int peso = random.nextInt(10) + 1;
                grafo.agregarArista(i, destino, peso);
            }
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nodo de origen (entre 0 y 29): ");
        int origen = scanner.nextInt();

        System.out.print("Ingrese el nodo de destino (entre 0 y 29): ");
        int destino = scanner.nextInt();

        grafo.mostrarGrafo();

        // Ruta más rápida del nodo de origen al destino
        System.out.println("Ruta del origen al destino:");
        List<Integer> caminoOrigenDestino = grafo.dijkstra(origen, destino);

        // Ruta más rápida del destino a la empresa (nodo 0)
        System.out.println("Ruta del destino a la empresa (nodo 0):");
        List<Integer> caminoDestinoEmpresa = grafo.dijkstra(destino, 0);
        new VisualizadorGrafo(grafo, caminoOrigenDestino);
    }
}
