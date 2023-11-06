import java.util.*;

public class Main {
    public static void main(String[] args) {
        int vertices = 30;
        Grafo grafo = new Grafo(vertices);
        Random random = new Random();

        for (int i = 0; i < vertices; i++) {
            int numAristas = random.nextInt(vertices / 15) + 1; // Asegura al menos una arista
            Set<Integer> destinosConectados = new HashSet<>();

            for (int j = 0; j < numAristas; j++) {
                int destino;
                do {
                    destino = random.nextInt(vertices);
                } while(destino == i || destinosConectados.contains(destino));  // Asegura que no hay auto bucles y duplicados

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
        List<Integer> camino = grafo.dijkstra(origen, destino);
        new VisualizadorGrafo(grafo, camino);
    }
}
