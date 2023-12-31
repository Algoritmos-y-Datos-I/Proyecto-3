import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;


/**
 * Clase que representa un objeto de tipo Employee con interfaz gráfica de chat.
 */
public class Employee {
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
     * Constructor que inicializa la interfaz gráfica y establece la conexión con el servidor.
     */
    public Employee() {
        hacerInterfaz();
    }
    /**
     * Método que crea la interfaz gráfica del chat y establece la conexión con el servidor.
     */
    public void hacerInterfaz() {
        ventana_chat = new JFrame("EMPLOYEE");
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
     * Método que inicia un hilo para la lectura continua de mensajes del servidor.
     */
    public void leer() {
        Thread leer_hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true) {
                        String mensaje_recibido = lector.readLine();
                        JSONObject json = new JSONObject(mensaje_recibido);
                        JSONArray camino = json.getJSONArray("destino");
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
     * Método que inicia un hilo para la escritura de mensajes hacia el servidor.
     */
    public void escribir() {
        Thread escribir_hilo = new Thread(new Runnable() {
            public void run() {
                try {
                    escritor = new PrintWriter(socket.getOutputStream(), true);
                    btn_enviar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // El Driver envía el nodo de origen.
                            String origen = JOptionPane.showInputDialog("Ingrese el nodo destino:");
                            JSONObject json = new JSONObject();
                            json.put("destino", origen);
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
     * Método principal que crea un objeto Employee al ejecutar el programa.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        new Employee();

    }

}