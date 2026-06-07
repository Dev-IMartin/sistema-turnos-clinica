package com.clinica.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Clase utilitaria para validacion de entradas del usuario.
 * Centraliza el manejo de errores de entrada.
 */
public class Validador {

    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_DATETIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Lee un entero con validacion de rango.
     */
    public static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        int valor = -1;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            try {
                String linea = sc.nextLine().trim();
                valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    valido = true;
                } else {
                    System.out.printf("  Error: ingrese un numero entre %d y %d.%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("  Error: ingrese solo números.");
            }
        }
        return valor;
    }

    /**
     * Lee un entero sin limite (solo positivos).
     */
    public static int leerEnteroPositivo(Scanner sc, String mensaje) {
        return leerEntero(sc, mensaje, 1, Integer.MAX_VALUE);
    }

    /**
     * Lee un String no vacio.
     */
    public static String leerTexto(Scanner sc, String mensaje) {
        String texto = "";
        while (texto.isEmpty()) {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("  Error: el campo no puede estar vacío.");
            }
        }
        return texto;
    }

    /**
     * Lee una fecha en formato dd/MM/yyyy.
     */
    public static LocalDate leerFecha(Scanner sc, String mensaje) {
        LocalDate fecha = null;
        while (fecha == null) {
            System.out.print(mensaje + " (dd/MM/yyyy): ");
            String entrada = sc.nextLine().trim();
            try {
                fecha = LocalDate.parse(entrada, FMT_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("  Error: formato inválido. Use dd/MM/yyyy.");
            }
        }
        return fecha;
    }

    /**
     * Lee una fecha y hora en formato dd/MM/yyyy HH:mm.
     */
    public static LocalDateTime leerFechaHora(Scanner sc, String mensaje) {
        LocalDateTime fechaHora = null;
        while (fechaHora == null) {
            System.out.print(mensaje + " (dd/MM/yyyy HH:mm): ");
            String entrada = sc.nextLine().trim();
            try {
                fechaHora = LocalDateTime.parse(entrada, FMT_DATETIME);
                if (fechaHora.isBefore(LocalDateTime.now())) {
                    System.out.println("  Error: la fecha/hora debe ser futura.");
                    fechaHora = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("  Error: formato inválido. Use dd/MM/yyyy HH:mm.");
            }
        }
        return fechaHora;
    }

    /**
     * Valida que un DNI tenga entre 7 y 8 digitos numericos.
     */
    public static boolean dniValido(String dni) {
        return dni.matches("\\d{7,8}");
    }

    /**
     * Valida formato basico de email.
     */
    public static boolean emailValido(String email) {
        return email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$");
    }
}
