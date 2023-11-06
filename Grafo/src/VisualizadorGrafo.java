import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisualizadorGrafo extends JFrame {
    private final Grafo grafo;
    private final List<Integer> camino;


    public VisualizadorGrafo(Grafo grafo, List<Integer> camino) {
        this.grafo = grafo;
        this.camino = camino;
        setTitle("Visualizador de Grafo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        List<List<Grafo.Nodo>> listaAdyacencia = grafo.getListaAdyacencia();

        int radio = 20;
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int radioCirculo = 200;

        int numVertices = listaAdyacencia.size();
        for (int i = 0; i < numVertices; i++) {
            double angulo = 2 * Math.PI * i / numVertices;
            int x1 = centroX + (int) (Math.cos(angulo) * radioCirculo) - radio / 2;
            int y1 = centroY + (int) (Math.sin(angulo) * radioCirculo) - radio / 2;

            // Dibujar aristas
            for (Grafo.Nodo nodo : listaAdyacencia.get(i)) {
                double anguloDestino = 2 * Math.PI * nodo.nodo / numVertices;
                int x2 = centroX + (int) (Math.cos(anguloDestino) * radioCirculo) - radio / 2;
                int y2 = centroY + (int) (Math.sin(anguloDestino) * radioCirculo) - radio / 2;

                // Revisar si la arista actual está en el camino más corto
                boolean enCamino = false;
                for (int j = 0; j < camino.size() - 1; j++) {
                    int u = camino.get(j);
                    int v = camino.get(j + 1);
                    if ((i == u && nodo.nodo == v) || (i == v && nodo.nodo == u)) {
                        enCamino = true;
                        break;
                    }
                }

                // Resaltar arista en camino
                if (enCamino) {
                    g.setColor(Color.RED);
                }
                g.drawLine(x1 + radio / 2, y1 + radio / 2, x2 + radio / 2, y2 + radio / 2);
                g.drawString(String.valueOf(nodo.peso), (x1 + x2) / 2, (y1 + y2) / 2);

                // Resetear color
                if (enCamino) {
                    g.setColor(Color.BLACK);
                }
            }

            // Dibujar nodo
            if (i == 0) {
                g.setColor(Color.RED); // Empresa
            }
            g.fillOval(x1, y1, radio, radio);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i), x1 + radio / 2, y1 + radio / 2);
        }
    }

}