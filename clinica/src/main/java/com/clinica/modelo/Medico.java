package com.clinica.modelo;

/**
 * Clase Medico que hereda de Persona.
 * Aplica HERENCIA.
 */
public class Medico extends Persona {

    private String especialidad;
    private String matricula;
    private double honorarios;

    // Constructor completo
    public Medico(int id, String nombre, String apellido, String dni,
                  String telefono, String especialidad, String matricula, double honorarios) {
        super(id, nombre, apellido, dni, telefono);
        this.especialidad = especialidad;
        this.matricula = matricula;
        this.honorarios = honorarios;
    }

    // Constructor sin id
    public Medico(String nombre, String apellido, String dni,
                  String telefono, String especialidad, String matricula, double honorarios) {
        this(0, nombre, apellido, dni, telefono, especialidad, matricula, honorarios);
    }

    @Override
    public String getRol() {
        return "MEDICO";
    }

    @Override
    public String getInfoDetallada() {
        return String.format("Dr/a. %s | Matrícula: %s | Especialidad: %s | Tel: %s",
                getNombreCompleto(), matricula, especialidad, getTelefono());
    }

    // Getters y Setters
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public double getHonorarios() { return honorarios; }
    public void setHonorarios(double honorarios) { this.honorarios = honorarios; }
}
