import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

    public class chatClient {

        public static void main(String[] args) {
            try {
                // Conectar al servidor en la IP local y puerto 1234
                Socket socket = new Socket("localhost", 1234);
                DataInputStream netIn = new DataInputStream(socket.getInputStream());
                DataOutputStream netOut = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);

                // Pide el nombre de usuario
                System.out.print("Ingresa tu nombre de usuario: ");
                String username = scanner.nextLine();
                netOut.writeUTF(username);  // Enviar el nombre de usuario al servidor

                // Thread para recibir mensajes del servidor
                new Thread(() -> {
                    try {
                        while (true) {
                            String mensaje = netIn.readUTF();
                            System.out.println(mensaje);
                        }
                    } catch (IOException ioe) {
                        System.out.println("Desconectado del servidor.");
                    }
                }).start();

                // Hilo para enviar mensajes al servidor
                while (true) {
                    System.out.print("> ");
                    String mensaje = scanner.nextLine();
                    netOut.writeUTF(mensaje);  // Envía el mensaje al servidor
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
