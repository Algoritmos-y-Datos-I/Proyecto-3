import org.json.*;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
/**
 * Clase que representa el controlador (Driver) para el sistema de Carpooling. Permite a los conductores
 * comunicarse con el servidor para enviar su posición (nodo de origen) y recibir las rutas óptimas
 * para recoger y llevar a los pasajeros.
 */
public class Driver {
    JFrame ventana_chat = null;
    JButton btn_enviar = null;
    JTextField txt_mensaje = null;
    JTextArea area_chat = null;
    JPanel contenedor_areachat = null;
    JPanel contenedor_btntxt = null;
    JScrollPane scroll = null;
    Socket socket = null;
    BufferedReader lector = null;
    PrintWriter escritor = null;
    /**
     * Constructor de la clase Driver. Inicializa la interfaz gráfica del conductor.
     */
    public Driver() {
        hacerInterfaz();
    }
    /**
     * Configura y muestra la interfaz gráfica del conductor.
     */
    public void hacerInterfaz() {
        ventana_chat = new JFrame("DRIVER");
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
                    socket = new Socket("127.0.0.1", 1234);
                    leer();
                    escribir();
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        principal.start();
    }
    /**
     * Método que crea un hilo para la comunicación de lectura con el servidor.
     * Este hilo escucha continuamente los mensajes del servidor, procesa las rutas y actualiza la interfaz.
     */
    public void leer() {
        Thread leer_hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true) {
                        String mensaje_recibido = lector.readLine();
                        JSONObject json = new JSONObject(mensaje_recibido);
                        JSONArray camino = json.getJSONArray("camino");
                        // Procesar el JSONArray camino...
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        leer_hilo.start();
    }

    /**
     * Método que crea un hilo para la comunicación de escritura con el servidor.
     * Permite al conductor enviar su posición (nodo de origen) al servidor.
     */

    public void escribir() {
        Thread escribir_hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    escritor = new PrintWriter(socket.getOutputStream(), true);
                    btn_enviar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // El Driver envía el nodo de origen.
                            String origen = JOptionPane.showInputDialog("Ingrese el nodo de origen:");
                            JSONObject json = new JSONObject();
                            json.put("origen", origen);
                            escritor.println(json.toString());
                            txt_mensaje.setText("");
                        }
                    });

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        escribir_hilo.start();
    }

    /**
     * Método principal que inicia la aplicación del conductor.
     *
     * @param args Argumentos de la línea de comandos (no utilizado).
     */

    public static void main(String[] args) {
        new Driver();

    }

}