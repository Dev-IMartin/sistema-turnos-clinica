package com.clinica.modelo;

import java.time.LocalDate;

/**
 * Clase Paciente que hereda de Persona.
 * Aplica HERENCIA y ENCAPSULAMIENTO.
 */
public class Paciente extends Persona {

    private LocalDate fechaNacimiento;
    private String obraSocial;
    private String email;

    // Constructor completo
    public Paciente(int id, String nombre, String apellido, String dni,
                    String telefono, LocalDate fechaNacimiento, String obraSocial, String email) {
        super(id, nombre, apellido, dni, telefono);
        this.fechaNacimiento = fechaNacimiento;
        this.obraSocial = obraSocial;
        this.email = email;
    }

    // Constructor sin id
    public Paciente(String nombre, String apellido, String dni,
                    String telefono, LocalDate fechaNacimiento, String obraSocial, String email) {
        this(0, nombre, apellido, dni, telefono, fechaNacimiento, obraSocial, email);
    }

    // POLIMORFISMO: implementacion del metodo abstracto
    @Override
    public String getRol() {
        return "PACIENTE";
    }

    @Override
    public String getInfoDetallada() {
        return String.format("Paciente: %s | DNI: %s | Obra Social: %s | Tel: %s | Email: %s",
                getNombreCompleto(), getDni(), obraSocial, getTelefono(), email);
    }

    public int getEdad() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    // Getters y Setters
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getObraSocial() { return obraSocial; }
    public void setObraSocial(String obraSocial) { this.obraSocial = obraSocial; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
