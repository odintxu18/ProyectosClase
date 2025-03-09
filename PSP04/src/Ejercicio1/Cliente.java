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
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 2000;

        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor en " + HOST + ":" + PUERTO);
            String respuesta;

            do {
                System.out.print("Introduce un número (0-100): ");
                String numero = scanner.nextLine();
                salida.println(numero); // Enviar número al servidor

                respuesta = entrada.readLine(); // Leer respuesta del servidor
                System.out.println("Servidor: " + respuesta);
            } while (!respuesta.contains("Felicidades"));

            System.out.println("Juego terminado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

