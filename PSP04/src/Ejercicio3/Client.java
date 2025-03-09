/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio3;

/**
 *
 * @author odint
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PUERTO = 1500;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor en " + HOST + ":" + PUERTO);

            // Autenticación
            System.out.print(entrada.readLine() + " "); // Usuario
            salida.println(scanner.nextLine());
            System.out.print(entrada.readLine() + " "); // Contraseña
            salida.println(scanner.nextLine());

            String respuesta = entrada.readLine();
            System.out.println(respuesta);
            if (respuesta.startsWith("ERROR")) return; // Si la autenticación falla, salimos

            // Menú interactivo
            while (true) {
                System.out.println(entrada.readLine()); // Menú
                String opcion = scanner.nextLine();
                salida.println(opcion);

                switch (opcion) {
                    case "1":
                        System.out.println(entrada.readLine());
                        String linea;
                        while ((linea = entrada.readLine()) != null && !linea.isEmpty()) {
                            System.out.println(linea);
                        }
                        break;
                    case "2":
                        System.out.print(entrada.readLine() + " ");
                        salida.println(scanner.nextLine());
                        String respuestaArchivo = entrada.readLine();
                        if ("OK".equals(respuestaArchivo)) {
                            while ((linea = entrada.readLine()) != null && !linea.isEmpty()) {
                                System.out.println(linea);
                            }
                        } else {
                            System.out.println(respuestaArchivo);
                        }
                        break;
                    case "3":
                        System.out.println(entrada.readLine());
                        return;
                    default:
                        System.out.println("Opción inválida.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

