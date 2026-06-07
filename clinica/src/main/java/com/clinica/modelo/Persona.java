package com.clinica.modelo;

/**
 * Clase abstracta que representa una persona en el sistema.
 * Aplica ABSTRACCION y es base para HERENCIA.
 */
public abstract class Persona {

    // Encapsulamiento: atributos privados
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;

    // Constructor
    public Persona(int id, String nombre, String apellido, String dni, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }

    // Constructor sin id (para inserciones nuevas)
    public Persona(String nombre, String apellido, String dni, String telefono) {
        this(0, nombre, apellido, dni, telefono);
    }

    // Metodo abstracto - obliga a subclases a implementarlo (POLIMORFISMO)
    public abstract String getRol();

    // Metodo abstracto para mostrar informacion
    public abstract String getInfoDetallada();

    // Getters y Setters (ENCAPSULAMIENTO)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getNombreCompleto() {
        return apellido + ", " + nombre;
    }

    @Override
    public String toString() {
        return "[" + getRol() + "] " + getNombreCompleto() + " - DNI: " + dni;
    }
}
