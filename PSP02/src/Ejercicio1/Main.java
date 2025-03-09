/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

/**
 *
 * @author odint
 */
public class Main {
     public static void main(String[] args) {
        // Capacidad del buffer = 6
        Buffer buffer = new Buffer(6);

        // Queremos 15 caracteres en total
        int numCaracteres = 15;

        // Crear hilos productor y consumidor
        Thread productor = new Thread(new Productor(buffer, numCaracteres), "Productor");
        Thread consumidor = new Thread(new Consumidor(buffer, numCaracteres), "Consumidor");

        // Iniciar hilos
        productor.start();
        consumidor.start();

        try {
            // Esperar a que terminen
            productor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Fin de la ejecución.");
    }

    
}
