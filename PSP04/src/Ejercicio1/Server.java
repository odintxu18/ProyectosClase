/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

/**
 *
 * @author odint
 */
import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private static final int PUERTO = 2000;
    private static final int NUMERO_SECRETO = new Random().nextInt(101); // Número entre 0 y 100

    public static void main(String[] args) {
        System.out.println("Servidor iniciado en el puerto " + PUERTO);
        System.out.println("Número secreto generado: " + NUMERO_SECRETO);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());
                
                // Creamos un hilo para manejar al cliente
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

                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    int numeroCliente;
                    try {
                        numeroCliente = Integer.parseInt(mensaje);
                    } catch (NumberFormatException e) {
                        salida.println("Debes ingresar un número válido.");
                        continue;
                    }

                    if (numeroCliente < NUMERO_SECRETO) {
                        salida.println("El número es mayor.");
                    } else if (numeroCliente > NUMERO_SECRETO) {
                        salida.println("El número es menor.");
                    } else {
                        salida.println("¡Felicidades! Has acertado el número secreto.");
                        break;
                    }
                }
                System.out.println("Cliente desconectado.");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


