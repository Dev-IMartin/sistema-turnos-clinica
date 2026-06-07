package com.clinica.util;

import com.clinica.modelo.Turno;

import java.util.Stack;

/**
 * Estructura de datos PILA (Stack) para el historial de acciones sobre turnos.
 * Permite deshacer la ultima accion realizada (LIFO: Last In, First Out).
 */
public class HistorialTurnos {

    private final Stack<Turno> historial;

    public HistorialTurnos() {
        this.historial = new Stack<>();
    }

    /**
     * Registra un turno en el historial (push).
     */
    public void registrar(Turno turno) {
        historial.push(turno);
    }

    /**
     * Retira y retorna el ultimo turno registrado (pop).
     */
    public Turno deshacer() {
        if (!historial.isEmpty()) {
            return historial.pop();
        }
        System.out.println("No hay acciones en el historial.");
        return null;
    }

    /**
     * Consulta el ultimo turno registrado sin retirarlo (peek).
     */
    public Turno verUltimo() {
        if (!historial.isEmpty()) {
            return historial.peek();
        }
        return null;
    }

    public int tamanio() {
        return historial.size();
    }

    public boolean estaVacia() {
        return historial.isEmpty();
    }

    public void mostrarHistorial() {
        if (historial.isEmpty()) {
            System.out.println("  (Historial vacío)");
            return;
        }
        // Mostrar en orden inverso (mas reciente primero)
        Stack<Turno> copia = new Stack<>();
        copia.addAll(historial);
        int i = copia.size();
        while (!copia.isEmpty()) {
            Turno t = copia.pop();
            System.out.printf("  %d. [%s] %s -> %s%n",
                    i--, t.getEstado(), t.getPaciente().getNombreCompleto(), t.getMedico().getNombreCompleto());
        }
    }
}
