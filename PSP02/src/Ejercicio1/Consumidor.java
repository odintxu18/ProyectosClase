/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

/**
 *
 * @author odint
 */
public class Consumidor implements Runnable{
       private final Buffer buffer;
    private final int cantidad;

    public Consumidor(Buffer buffer, int cantidad) {
        this.buffer = buffer;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < cantidad; i++) {
                char c = buffer.extraer();
                Thread.sleep(300); // Simula tiempo de consumo
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}



    

