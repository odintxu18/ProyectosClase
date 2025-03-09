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
import java.util.Scanner;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PUERTO = 1500;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor en " + HOST + ":" + PUERTO);
            
            // Pedir al usuario el nombre del archivo a solicitar
            System.out.print("Introduce el nombre del archivo a solicitar: ");
            String nombreArchivo = scanner.nextLine();
            salida.println(nombreArchivo); // Enviar nombre al servidor

            // Esperar respuesta del servidor
            String respuesta = entrada.readLine();
            if ("OK".equals(respuesta)) {
                System.out.println("\nContenido del archivo recibido:");
                String linea;
                while ((linea = entrada.readLine()) != null) {
                    System.out.println(linea);
                }
            } else {
                System.out.println("Servidor: " + respuesta);
            }

            System.out.println("Conexión finalizada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

