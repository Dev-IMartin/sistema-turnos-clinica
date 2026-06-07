package com.clinica.util;

import com.clinica.modelo.Turno;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Estructura de datos COLA (Queue) para gestionar la atencion de turnos del dia.
 * Implementa una cola FIFO utilizando LinkedList.
 * Aplica el concepto de estructura de datos con cola enlazada.
 */
public class ColaTurnos {

    private final Queue<Turno> cola;

    public ColaTurnos() {
        this.cola = new LinkedList<>();
    }

    /**
     * Agrega un turno al final de la cola (enqueue).
     */
    public void encolar(Turno turno) {
        cola.offer(turno);
        System.out.println("Turno encolado: " + turno.getPaciente().getNombreCompleto());
    }

    /**
     * Atiende (retira) el primer turno de la cola (dequeue).
     */
    public Turno atender() {
        Turno turno = cola.poll();
        if (turno != null) {
            System.out.println("Atendiendo a: " + turno.getPaciente().getNombreCompleto());
        } else {
            System.out.println("No hay turnos en espera.");
        }
        return turno;
    }

    /**
     * Consulta el proximo turno sin retirarlo (peek).
     */
    public Turno verProximo() {
        return cola.peek();
    }

    public int tamanio() {
        return cola.size();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public void mostrarCola() {
        if (cola.isEmpty()) {
            System.out.println("  (La cola de espera está vacía)");
            return;
        }
        int pos = 1;
        for (Turno t : cola) {
            System.out.printf("  %d. %s - Dr/a. %s%n",
                    pos++,
                    t.getPaciente().getNombreCompleto(),
                    t.getMedico().getNombreCompleto());
        }
    }
}
