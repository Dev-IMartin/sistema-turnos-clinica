package com.clinica.menu;

import com.clinica.dao.*;
import com.clinica.modelo.*;
import com.clinica.util.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Clase MenuPrincipal: maneja la interfaz de usuario por consola.
 * Implementa el menu de seleccion con estructuras condicionales y repetitivas.
 */
public class MenuPrincipal {

    private final Scanner scanner;
    private final PacienteDAO pacienteDAO;
    private final MedicoDAO medicoDAO;
    private final TurnoDAO turnoDAO;
    private final ColaTurnos colaTurnos;
    private final HistorialTurnos historialTurnos;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.pacienteDAO = new PacienteDAO();
        this.medicoDAO = new MedicoDAO();
        this.turnoDAO = new TurnoDAO();
        this.colaTurnos = new ColaTurnos();
        this.historialTurnos = new HistorialTurnos();
    }

    /**
     * Inicia el ciclo principal del menu.
     */
    public void iniciar() {
        boolean salir = false;

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTION DE TURNOS MEDICOS     ║");
        System.out.println("║         Clinica San Juan S.A.               ║");
        System.out.println("╚════════════════════════════════════════════╝");

        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = Validador.leerEntero(scanner, "Seleccione una opción: ", 0, 4);

            switch (opcion) {
                case 1:
                    menuPacientes();
                    break;
                case 2:
                    menuMedicos();
                    break;
                case 3:
                    menuTurnos();
                    break;
                case 4:
                    menuAtencion();
                    break;
                case 0:
                    salir = true;
                    System.out.println("\n¡Hasta luego! Cerrando sistema...");
                    ConexionDB.getInstancia() != null;
                    try { ConexionDB.getInstancia().cerrar(); } catch (SQLException e) { /* ignorar */ }
                    break;
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n╔═══════════════════════════╗");
        System.out.println("║       MENU PRINCIPAL       ║");
        System.out.println("╠═══════════════════════════╣");
        System.out.println("║  1. Gestión de Pacientes   ║");
        System.out.println("║  2. Gestión de Médicos     ║");
        System.out.println("║  3. Gestión de Turnos      ║");
        System.out.println("║  4. Sala de Espera (Cola)  ║");
        System.out.println("║  0. Salir                  ║");
        System.out.println("╚═══════════════════════════╝");
    }

    // ─────────────────────────────────────────────
    //  MENU PACIENTES
    // ─────────────────────────────────────────────
    private void menuPacientes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTION DE PACIENTES ---");
            System.out.println("1. Registrar nuevo paciente");
            System.out.println("2. Buscar por apellido");
            System.out.println("3. Buscar por DNI");
            System.out.println("4. Listar todos los pacientes");
            System.out.println("0. Volver al menú principal");

            int op = Validador.leerEntero(scanner, "Opción: ", 0, 4);
            switch (op) {
                case 1: registrarPaciente(); break;
                case 2: buscarPacientePorApellido(); break;
                case 3: buscarPacientePorDni(); break;
                case 4: listarPacientes(); break;
                case 0: volver = true; break;
            }
        }
    }

    private void registrarPaciente() {
        System.out.println("\n[ REGISTRAR PACIENTE ]");
        try {
            String nombre = Validador.leerTexto(scanner, "Nombre: ");
            String apellido = Validador.leerTexto(scanner, "Apellido: ");

            String dni;
            do {
                dni = Validador.leerTexto(scanner, "DNI (7-8 dígitos): ");
                if (!Validador.dniValido(dni)) System.out.println("  DNI inválido.");
            } while (!Validador.dniValido(dni));

            // Verificar que no exista ya
            if (pacienteDAO.buscarPorDni(dni) != null) {
                System.out.println("  Ya existe un paciente con ese DNI.");
                return;
            }

            String telefono = Validador.leerTexto(scanner, "Teléfono: ");
            LocalDate fechaNac = Validador.leerFecha(scanner, "Fecha de nacimiento");
            String obraSocial = Validador.leerTexto(scanner, "Obra social (o 'Particular'): ");

            String email;
            do {
                email = Validador.leerTexto(scanner, "Email: ");
                if (!Validador.emailValido(email)) System.out.println("  Email inválido.");
            } while (!Validador.emailValido(email));

            Paciente nuevo = new Paciente(nombre, apellido, dni, telefono, fechaNac, obraSocial, email);
            if (pacienteDAO.insertar(nuevo)) {
                System.out.println("✓ Paciente registrado con ID: " + nuevo.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }

    private void buscarPacientePorApellido() {
        String apellido = Validador.leerTexto(scanner, "Apellido a buscar: ");
        try {
            List<Paciente> resultados = pacienteDAO.buscarPorApellido(apellido);
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron pacientes con ese apellido.");
            } else {
                resultados.forEach(p -> System.out.println("  " + p.getInfoDetallada()));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarPacientePorDni() {
        String dni = Validador.leerTexto(scanner, "DNI: ");
        try {
            Paciente p = pacienteDAO.buscarPorDni(dni);
            if (p != null) System.out.println("  " + p.getInfoDetallada());
            else System.out.println("Paciente no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarPacientes() {
        try {
            List<Paciente> lista = pacienteDAO.listarTodos();
            if (lista.isEmpty()) {
                System.out.println("No hay pacientes registrados.");
            } else {
                System.out.printf("%n%-5s %-20s %-10s %-20s%n", "ID", "Paciente", "DNI", "Obra Social");
                System.out.println("-".repeat(60));
                // Recorrido con estructura repetitiva for-each
                for (Paciente p : lista) {
                    System.out.printf("%-5d %-20s %-10s %-20s%n",
                            p.getId(), p.getNombreCompleto(), p.getDni(), p.getObraSocial());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  MENU MEDICOS
    // ─────────────────────────────────────────────
    private void menuMedicos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTION DE MEDICOS ---");
            System.out.println("1. Registrar médico");
            System.out.println("2. Buscar por especialidad");
            System.out.println("3. Listar todos");
            System.out.println("0. Volver");

            int op = Validador.leerEntero(scanner, "Opción: ", 0, 3);
            switch (op) {
                case 1: registrarMedico(); break;
                case 2: buscarMedicoPorEspecialidad(); break;
                case 3: listarMedicos(); break;
                case 0: volver = true; break;
            }
        }
    }

    private void registrarMedico() {
        System.out.println("\n[ REGISTRAR MEDICO ]");
        try {
            String nombre = Validador.leerTexto(scanner, "Nombre: ");
            String apellido = Validador.leerTexto(scanner, "Apellido: ");
            String dni = Validador.leerTexto(scanner, "DNI: ");
            String telefono = Validador.leerTexto(scanner, "Teléfono: ");
            String especialidad = Validador.leerTexto(scanner, "Especialidad: ");
            String matricula = Validador.leerTexto(scanner, "Matrícula: ");
            double honorarios = Double.parseDouble(Validador.leerTexto(scanner, "Honorarios por consulta ($): "));

            Medico medico = new Medico(nombre, apellido, dni, telefono, especialidad, matricula, honorarios);
            if (medicoDAO.insertar(medico)) {
                System.out.println("✓ Médico registrado con ID: " + medico.getId());
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarMedicoPorEspecialidad() {
        String esp = Validador.leerTexto(scanner, "Especialidad: ");
        try {
            List<Medico> resultados = medicoDAO.buscarPorEspecialidad(esp);
            if (resultados.isEmpty()) System.out.println("No hay médicos con esa especialidad.");
            else resultados.forEach(m -> System.out.println("  " + m.getInfoDetallada()));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarMedicos() {
        try {
            List<Medico> lista = medicoDAO.listarTodos();
            System.out.printf("%n%-5s %-20s %-20s %-12s%n", "ID", "Médico", "Especialidad", "Matrícula");
            System.out.println("-".repeat(60));
            for (Medico m : lista) {
                System.out.printf("%-5d %-20s %-20s %-12s%n",
                        m.getId(), m.getNombreCompleto(), m.getEspecialidad(), m.getMatricula());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  MENU TURNOS
    // ─────────────────────────────────────────────
    private void menuTurnos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTION DE TURNOS ---");
            System.out.println("1. Nuevo turno");
            System.out.println("2. Ver turnos de un paciente");
            System.out.println("3. Ver agenda de un médico");
            System.out.println("4. Cambiar estado de turno");
            System.out.println("5. Listar todos los turnos (ordenados)");
            System.out.println("0. Volver");

            int op = Validador.leerEntero(scanner, "Opción: ", 0, 5);
            switch (op) {
                case 1: crearTurno(); break;
                case 2: verTurnosPaciente(); break;
                case 3: verAgendaMedico(); break;
                case 4: cambiarEstadoTurno(); break;
                case 5: listarTurnosOrdenados(); break;
                case 0: volver = true; break;
            }
        }
    }

    private void crearTurno() {
        System.out.println("\n[ NUEVO TURNO ]");
        try {
            int pacienteId = Validador.leerEnteroPositivo(scanner, "ID del paciente: ");
            Paciente paciente = pacienteDAO.buscarPorId(pacienteId);
            if (paciente == null) { System.out.println("Paciente no encontrado."); return; }

            int medicoId = Validador.leerEnteroPositivo(scanner, "ID del médico: ");
            Medico medico = medicoDAO.buscarPorId(medicoId);
            if (medico == null) { System.out.println("Médico no encontrado."); return; }

            LocalDateTime fechaHora = Validador.leerFechaHora(scanner, "Fecha y hora del turno");
            String obs = Validador.leerTexto(scanner, "Motivo/observaciones: ");

            Turno turno = new Turno(paciente, medico, fechaHora, obs);
            if (turnoDAO.insertar(turno)) {
                System.out.println("✓ Turno creado: " + turno);
                historialTurnos.registrar(turno);
                // Preguntamos si agregar a cola de espera
                System.out.print("¿Agregar a sala de espera ahora? (s/n): ");
                String resp = scanner.nextLine().trim().toLowerCase();
                if (resp.equals("s")) colaTurnos.encolar(turno);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verTurnosPaciente() {
        int id = Validador.leerEnteroPositivo(scanner, "ID del paciente: ");
        try {
            List<Turno> turnos = turnoDAO.listarPorPaciente(id);
            if (turnos.isEmpty()) System.out.println("No hay turnos para ese paciente.");
            else turnos.forEach(t -> System.out.println("  " + t));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verAgendaMedico() {
        int id = Validador.leerEnteroPositivo(scanner, "ID del médico: ");
        try {
            List<Turno> turnos = turnoDAO.listarPorMedico(id);
            System.out.println("Turnos pendientes:");
            if (turnos.isEmpty()) System.out.println("  (sin turnos pendientes)");
            else turnos.forEach(t -> System.out.println("  " + t));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoTurno() {
        int id = Validador.leerEnteroPositivo(scanner, "ID del turno: ");
        System.out.println("Estados: 1=CONFIRMADO, 2=ATENDIDO, 3=CANCELADO, 4=AUSENTE");
        int op = Validador.leerEntero(scanner, "Nuevo estado: ", 1, 4);

        EstadoTurno[] estados = {EstadoTurno.CONFIRMADO, EstadoTurno.ATENDIDO,
                                  EstadoTurno.CANCELADO, EstadoTurno.AUSENTE};
        try {
            if (turnoDAO.actualizarEstado(id, estados[op - 1])) {
                System.out.println("✓ Estado actualizado a: " + estados[op - 1]);
            } else {
                System.out.println("Turno no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarTurnosOrdenados() {
        try {
            List<Turno> turnos = turnoDAO.listarTodos();
            List<Turno> ordenados = turnoDAO.ordenarPorFecha(turnos);
            System.out.println("\nTurnos ordenados por fecha:");
            if (ordenados.isEmpty()) System.out.println("  (sin turnos registrados)");
            else ordenados.forEach(t -> System.out.println("  " + t));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  MENU ATENCION / COLA
    // ─────────────────────────────────────────────
    private void menuAtencion() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- SALA DE ESPERA (COLA FIFO) ---");
            System.out.printf("Pacientes en espera: %d%n", colaTurnos.tamanio());
            System.out.println("1. Ver cola de espera");
            System.out.println("2. Llamar siguiente paciente");
            System.out.println("3. Ver historial de turnos procesados");
            System.out.println("0. Volver");

            int op = Validador.leerEntero(scanner, "Opción: ", 0, 3);
            switch (op) {
                case 1:
                    System.out.println("Cola de espera actual:");
                    colaTurnos.mostrarCola();
                    break;
                case 2:
                    Turno siguiente = colaTurnos.atender();
                    if (siguiente != null) {
                        historialTurnos.registrar(siguiente);
                        try {
                            turnoDAO.actualizarEstado(siguiente.getId(), EstadoTurno.ATENDIDO);
                        } catch (SQLException e) {
                            System.out.println("Advertencia: no se pudo actualizar estado en BD.");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Historial (últimos procesados):");
                    historialTurnos.mostrarHistorial();
                    break;
                case 0:
                    volver = true;
                    break;
            }
        }
    }
}
