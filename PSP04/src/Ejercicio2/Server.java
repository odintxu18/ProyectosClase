/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio2;

/**
 *
 * @author odint
 */
import java.io.*;
import java.net.*;

public class Server {
    private static final int PUERTO = 1500;

    public static void main(String[] args) {
        System.out.println("Servidor iniciado en el puerto " + PUERTO);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());

                // Crear un hilo para manejar la solicitud del cliente
                new Thread(new ManejadorCliente(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ManejadorCliente implements Runnable {
        private Socket socket;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

                // Leer el nombre del archivo solicitado por el cliente
                String nombreArchivo = entrada.readLine();
                System.out.println("Cliente solicita el archivo: " + nombreArchivo);

                File archivo = new File(nombreArchivo);

                if (archivo.exists() && archivo.isFile()) {
                    salida.println("OK"); // Confirmación de que el archivo existe

                    // Enviar el archivo línea por línea
                    try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(archivo))) {
                        String linea;
                        while ((linea = lectorArchivo.readLine()) != null) {
                            salida.println(linea);
                        }
                    }
                    System.out.println("Archivo enviado con éxito.");
                } else {
                    salida.println("ERROR: El archivo no existe.");
                    System.out.println("Archivo no encontrado.");
                }
                System.out.println("Cerrando conexión con el cliente...");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

