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

public class Server {
    private static final int PUERTO = 1500;
    private static final String USUARIO_CORRECTO = "javier";
    private static final String CONTRASEÑA_CORRECTA = "secreta";

    public static void main(String[] args) {
        System.out.println("Servidor iniciado en el puerto " + PUERTO);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());

                // Crear un hilo para manejar la sesión del cliente
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

                // Autenticación
                salida.println("Introduce el usuario:");
                String usuario = entrada.readLine();
                salida.println("Introduce la contraseña:");
                String contraseña = entrada.readLine();

                if (USUARIO_CORRECTO.equals(usuario) && CONTRASEÑA_CORRECTA.equals(contraseña)) {
                    salida.println("Autenticación exitosa. Bienvenido, " + usuario + "!");
                } else {
                    salida.println("ERROR: Usuario o contraseña incorrectos. Desconectando...");
                    socket.close();
                    return;
                }

                // Menú de opciones
                while (true) {
                    salida.println("\n--- MENÚ ---\n1. Ver contenido del directorio\n2. Mostrar contenido de un archivo\n3. Salir\nElige una opción:");
                    String opcion = entrada.readLine();

                    switch (opcion) {
                        case "1":
                            listarArchivos(salida);
                            break;
                        case "2":
                            salida.println("Introduce el nombre del archivo:");
                            String nombreArchivo = entrada.readLine();
                            enviarArchivo(nombreArchivo, salida);
                            break;
                        case "3":
                            salida.println("Desconectando del servidor...");
                            System.out.println("Cliente desconectado.");
                            socket.close();
                            return;
                        default:
                            salida.println("Opción no válida.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void listarArchivos(PrintWriter salida) {
            File directorio = new File(".");
            File[] archivos = directorio.listFiles();

            if (archivos != null) {
                salida.println("Contenido del directorio actual:");
                for (File archivo : archivos) {
                    salida.println(archivo.getName());
                }
            } else {
                salida.println("No se pudo acceder al directorio.");
            }
        }

        private void enviarArchivo(String nombreArchivo, PrintWriter salida) {
            File archivo = new File(nombreArchivo);

            if (archivo.exists() && archivo.isFile()) {
                salida.println("OK");
                try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                    String linea;
                    while ((linea = lector.readLine()) != null) {
                        salida.println(linea);
                    }
                } catch (IOException e) {
                    salida.println("ERROR: No se pudo leer el archivo.");
                }
            } else {
                salida.println("ERROR: El archivo no existe.");
            }
        }
    }
}

