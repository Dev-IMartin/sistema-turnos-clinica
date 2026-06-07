package com.clinica.modelo;

/**
 * Enumeracion de estados posibles para un turno.
 */
public enum EstadoTurno {
    PENDIENTE("Pendiente"),
    CONFIRMADO("Confirmado"),
    ATENDIDO("Atendido"),
    CANCELADO("Cancelado"),
    AUSENTE("Ausente");

    private final String descripcion;

    EstadoTurno(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
