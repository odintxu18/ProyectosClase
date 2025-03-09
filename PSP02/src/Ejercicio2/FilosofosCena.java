/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio2;

/**
 *
 * @author odint
 */
import java.util.concurrent.Semaphore;

public class FilosofosCena {
    public static void main(String[] args) {
        final int NUM_FILOSOFOS = 5;

        // Semáforos para palillos, uno por cada posición
        Semaphore[] palillo = new Semaphore[NUM_FILOSOFOS];
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            palillo[i] = new Semaphore(1);
        }

        // Crear e iniciar los hilos (filósofos)
        Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            filosofos[i] = new Filosofo(i, palillo);
            new Thread(filosofos[i], "Filosofo " + i).start();
        }
    }

    
}
