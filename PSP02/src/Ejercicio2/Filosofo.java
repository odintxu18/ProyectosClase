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

public class Filosofo implements Runnable {
      private int id;
    private Semaphore[] palillo;
    private int izq, der;  // Índices de los palillos

    public Filosofo(int id, Semaphore[] palillo) {
        this.id = id;
        this.palillo = palillo;
        // Palillo a la izquierda es 'id', a la derecha '(id+1) % N'
        // Para evitar interbloqueo: coger primero el de menor índice
        izq = Math.min(id, (id + 1) % palillo.length);
        der = Math.max(id, (id + 1) % palillo.length);
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                hambriento();
                // Tomar palillo izquierdo
                palillo[izq].acquire();
                // Tomar palillo derecho
                palillo[der].acquire();

                comer();

                // Soltar palillos
                palillo[der].release();
                palillo[izq].release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " Pensando");
        Thread.sleep((long) (Math.random() * 600));
    }

    private void hambriento() {
        System.out.println("Filósofo " + id + " Hambriento");
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " Comiendo");
        Thread.sleep((long) (Math.random() * 600));
        System.out.println("Filósofo " + id + " Termina de comer");
    }
}


