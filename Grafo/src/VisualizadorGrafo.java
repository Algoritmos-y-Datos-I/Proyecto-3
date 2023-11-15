import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class VisualizadorGrafo extends JFrame {
    private final Grafo grafo;
    private final List<Integer> camino;
    private final int[][] posicionesNodos; // Almacenará las posiciones de los nodos

    public VisualizadorGrafo(Grafo grafo, List<Integer> camino) {
        this.grafo = grafo;
        this.camino = camino;
        setTitle("Visualizador de Grafo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Generar posiciones aleatorias para los nodos antes de hacer visible el JFrame
        posicionesNodos = new int[grafo.getListaAdyacencia().size()][2];
        generarPosicionesAleatorias();

        setVisible(true);
    }

    private void generarPosicionesAleatorias() {
        Random random = new Random();
        Set<Point> ocupado = new HashSet<>();
        int padding = 50; // Espacio desde el borde del JFrame
        int radio = 20; // Radio del nodo

        for (int i = 0; i < grafo.getListaAdyacencia().size(); i++) {
            int x, y;
            do {
                x = padding + random.nextInt(getWidth() - 2 * padding - radio);
                y = padding + random.nextInt(getHeight() - 2 * padding - radio);
            } while (!ocupado.add(new Point(x, y))); // Asegura que no haya superposición
            posicionesNodos[i][0] = x;
            posicionesNodos[i][1] = y;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radio = 20; // Radio del nodo

        // Dibujar aristas
        for (int i = 0; i < posicionesNodos.length; i++) {
            int x1 = posicionesNodos[i][0] + radio / 2;
            int y1 = posicionesNodos[i][1] + radio / 2;

            for (Grafo.Nodo nodo : grafo.getListaAdyacencia().get(i)) {
                int x2 = posicionesNodos[nodo.nodo][0] + radio / 2;
                int y2 = posicionesNodos[nodo.nodo][1] + radio / 2;

                boolean enCamino = camino.contains(i) && camino.contains(nodo.nodo) &&
                        (camino.indexOf(i) == camino.indexOf(nodo.nodo) - 1 ||
                                camino.indexOf(i) == camino.indexOf(nodo.nodo) + 1);

                boolean caminoHaciaEmpresa = camino.contains(10) && (camino.contains(i) && i <= 10) && (camino.contains(nodo.nodo) && nodo.nodo <= 10);

                if (enCamino || caminoHaciaEmpresa) {
                    g2d.setStroke(new BasicStroke(3));
                    g2d.setColor(Color.RED);
                } else {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setColor(Color.BLACK);
                }

                g2d.drawLine(x1, y1, x2, y2);
                g2d.drawString(String.valueOf(nodo.peso), (x1 + x2) / 2, (y1 + y2) / 2);

                // Resetear color y grosor
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
            }
        }

        // Dibujar nodos
        for (int i = 0; i < posicionesNodos.length; i++) {
            int x = posicionesNodos[i][0];
            int y = posicionesNodos[i][1];

            if (i == 0) {
                g2d.setColor(Color.GREEN); // Nodo de inicio
            } else if (i == 10) {
                g2d.setColor(Color.ORANGE); // Nodo de la empresa
            } else if (camino.contains(i)) {
                g2d.setColor(Color.BLUE); // Nodos en el camino
            } else {
                g2d.setColor(Color.GRAY); // Nodos comunes
            }

            g2d.fillOval(x, y, radio, radio);
            g2d.setColor(Color.WHITE);
            g2d.drawString(String.valueOf(i), x + radio / 3, y + (radio / 2) + 5); // Centrar texto en el nodo
        }
    }
}