/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

/**
 *
 * @author odint
 */
public class Productor implements Runnable{
    private final Buffer buffer;
    private final int cantidad;

    public Productor(Buffer buffer, int cantidad) {
        this.buffer = buffer;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        try {
            // Genera caracteres (A, B, C... o aleatorios)
            // Aquí usaremos caracteres 'A' + i como ejemplo
            for (int i = 0; i < cantidad; i++) {
                char c = (char) ('A' + i);
                buffer.depositar(c);
                Thread.sleep(200); // Simula tiempo de producción
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}



    

