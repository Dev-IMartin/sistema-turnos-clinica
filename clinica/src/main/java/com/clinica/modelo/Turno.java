package com.clinica.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Turno que representa un turno medico.
 * Aplica ENCAPSULAMIENTO y composicion con Paciente y Medico.
 */
public class Turno implements Comparable<Turno> {

    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;
    private String observaciones;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Constructor completo
    public Turno(int id, Paciente paciente, Medico medico,
                 LocalDateTime fechaHora, EstadoTurno estado, String observaciones) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    // Constructor nuevo turno (sin id, estado PENDIENTE por defecto)
    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora, String observaciones) {
        this(0, paciente, medico, fechaHora, EstadoTurno.PENDIENTE, observaciones);
    }

    // Comparable: permite ordenar turnos por fecha/hora
    @Override
    public int compareTo(Turno otro) {
        return this.fechaHora.compareTo(otro.fechaHora);
    }

    public boolean esFuturo() {
        return fechaHora.isAfter(LocalDateTime.now());
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public EstadoTurno getEstado() { return estado; }
    public void setEstado(EstadoTurno estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return String.format("Turno #%d | %s | Dr/a. %s (%s) | Paciente: %s | Estado: %s",
                id,
                fechaHora.format(FORMATTER),
                medico.getNombreCompleto(),
                medico.getEspecialidad(),
                paciente.getNombreCompleto(),
                estado.getDescripcion());
    }
}
