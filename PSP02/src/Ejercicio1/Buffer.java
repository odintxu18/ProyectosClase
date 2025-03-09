/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio1;

/**
 *
 * @author odint
 */
public class Buffer {
      private final char[] buffer;
    private int count;       // Número de caracteres almacenados
    private int in, out;     // Punteros de inserción y extracción

    public Buffer(int capacidad) {
        buffer = new char[capacidad];
        count = 0;
        in = 0;
        out = 0;
    }

    public synchronized void depositar(char c) throws InterruptedException {
        // Espera mientras el buffer esté lleno
        while (count == buffer.length) {
            wait();
        }
        // Deposita el carácter
        buffer[in] = c;
        in = (in + 1) % buffer.length;
        count++;
        System.out.println("Depositado el carácter " + c + " en el buffer");
        // Notifica al consumidor que puede extraer
        notifyAll();
    }

    public synchronized char extraer() throws InterruptedException {
        // Espera mientras el buffer esté vacío
        while (count == 0) {
            wait();
        }
        // Extrae el carácter
        char c = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        System.out.println("Recogido el carácter " + c + " del buffer");
        // Notifica al productor que puede depositar
        notifyAll();
        return c;
    }

    
}
