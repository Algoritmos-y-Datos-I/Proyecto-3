
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;
import java.util.*;
import org.json.*;


/**
 * La clase Servidor implementa un servidor para gestionar un chat y la simulación de rutas en un grafo.
 */

public class Servidor {
    JFrame ventana_chat = null;
    JButton btn_enviar = null;
    JTextField txt_mensaje = null;
    JTextArea area_chat = null;
    JPanel contenedor_areachat = null;
    JPanel contenedor_btntxt = null;
    JScrollPane scroll = null;
    ServerSocket servidor = null;
    Socket socket = null;
    BufferedReader lector = null;
    PrintWriter escritor = null;
    Grafo grafo;

    /**
     * Constructor de la clase Servidor. Inicializa la interfaz y crea un grafo aleatorio.
     */

    public Servidor(){
        hacerInterfaz();
        grafo = new Grafo(30);
        generarGrafo();
    }
    /**
     * Configura la interfaz gráfica del servidor.
     */
    public void hacerInterfaz() {
        ventana_chat = new JFrame("Servidor");
        btn_enviar = new JButton("Enviar");
        txt_mensaje = new JTextField(4);
        area_chat = new JTextArea(10, 12);
        scroll = new JScrollPane(area_chat);
        contenedor_areachat = new JPanel();
        contenedor_areachat.setLayout(new GridLayout(1,1));
        contenedor_areachat.add(scroll);
        contenedor_btntxt = new JPanel();
        contenedor_btntxt.setLayout(new GridLayout(1,2));
        contenedor_btntxt.add(txt_mensaje);
        contenedor_btntxt.add(btn_enviar);
        ventana_chat.setLayout(new BorderLayout());
        ventana_chat.add(contenedor_areachat, BorderLayout.NORTH);
        ventana_chat.add(contenedor_btntxt, BorderLayout.SOUTH);
        ventana_chat.setSize(300, 220);
        ventana_chat.setVisible(true);
        ventana_chat.setResizable(false);
        ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread principal = new Thread(new Runnable() {
            public void run() {
                try {
                    servidor = new ServerSocket(1234);
                    while(true) {
                        socket = servidor.accept();
                        leer();
                        escribir();
                    }
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        principal.start();
    }
    /**
     * Crea un hilo secundario para leer mensajes del cliente.
     */
    public void leer() {
        Thread leer_hilo = new Thread(new Runnable() {
            /**
             * El hilo secundario encargado de la lectura continua de mensajes del cliente.
             */
            public void run() {
                try {
                    lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true) {
                        String mensaje_recibido = lector.readLine();
                        if(mensaje_recibido != null) {
                            procesarMensaje(mensaje_recibido);
                        }
                    }
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        leer_hilo.start();
    }

    int origen = -1;
    int destino = -1;
    /**
     * Procesa un mensaje JSON recibido del cliente y realiza acciones específicas,
     * como calcular rutas en el grafo y enviar información al cliente.
     *
     * @param mensaje El mensaje JSON recibido del cliente.
     */
    public void procesarMensaje(String mensaje) {
        JSONObject json = new JSONObject(mensaje);
        if(json.has("origen")) {
            origen = json.getInt("origen");
        }
        if(json.has("destino")) {
            destino = json.getInt("destino");
        }

        if(origen != -1 && destino != -1) {
            // Calcula la ruta del origen al destino
            List<Integer> rutaOrigenADestino = grafo.dijkstra(origen, destino);

            // Calcula la ruta del destino al nodo 0
            List<Integer> rutaDestinoANodo0 = grafo.dijkstra(destino, 0);

            // Combina las rutas
            List<Integer> rutaCombinada = new ArrayList<>(rutaOrigenADestino);
            rutaCombinada.addAll(rutaDestinoANodo0.subList(1, rutaDestinoANodo0.size()));

            // Envía la ruta combinada al cliente y muestra el grafo
            enviarRutaAlCliente(rutaCombinada);
            mostrarGrafo(rutaCombinada);

            origen = -1;
            destino = -1;
        }
    }


    /**
     * Crea un hilo secundario para enviar mensajes al cliente.
     */
    public void escribir() {
        Thread escribir_hilo = new Thread(new Runnable() {
            /**
             * El hilo de escritura envía mensajes al cliente cuando se presiona el botón
             * de enviar en la interfaz gráfica.
             */
            public void run() {
                try {
                    escritor = new PrintWriter(socket.getOutputStream(), true);
                    btn_enviar.addActionListener(new ActionListener() {
                        /**
                         * Este ActionListener se activa cuando se presiona el botón de enviar.
                         * Envía el contenido del campo de texto al cliente y lo borra después
                         * de enviarlo.
                         */
                        public void actionPerformed(ActionEvent e) {
                            String enviar_mensaje = txt_mensaje.getText();
                            escritor.println(enviar_mensaje);
                            txt_mensaje.setText("");
                        }
                    });
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        escribir_hilo.start();

    }
    /**
     * Envía la ruta combinada al cliente en formato JSON.
     *
     * @param camino La ruta combinada a enviar al cliente.
     */

    public void enviarRutaAlCliente(List<Integer> camino) {
        if (escritor != null) {
            JSONObject json = new JSONObject();
            json.put("camino", new JSONArray(camino));
            escritor.println(json.toString());
            escritor.flush();
        }
    }

    /**
     * Genera un grafo con aristas aleatorias y pesos asignados de manera aleatoria.
     */
    private void generarGrafo() {
        Random rand = new Random();
        // Crear un grafo con aristas aleatorias
        for (int i = 0; i < 30; i++) {
            // Asegurar al menos una arista por vértice
            int destino = rand.nextInt(30);
            int peso = rand.nextInt(10) + 1; // Peso entre 1 y 10
            grafo.agregarArista(i, destino, peso);

            // Añadir una segunda arista con 50% de probabilidad
            if (rand.nextBoolean()) {
                destino = rand.nextInt(30);
                peso = rand.nextInt(10) + 1; // Peso entre 1 y 10
                grafo.agregarArista(i, destino, peso);
            }
        }
    }

    /**
     * Muestra el grafo en una ventana gráfica utilizando Swing.
     *
     * @param camino La ruta a resaltar en el grafo.
     */
    public void mostrarGrafo(List<Integer> camino) {
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Muestra el grafo en una ventana gráfica utilizando Swing. Se resalta la ruta
             * proporcionada en el grafo.
             */
            @Override
            public void run() {
                new VisualizadorGrafo(grafo, camino);
            }
        });
    }
    /**
     * Método principal que crea una instancia de la clase Servidor al ejecutar la aplicación.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */

    public static void main(String[] args) {
        new Servidor();

    }

}