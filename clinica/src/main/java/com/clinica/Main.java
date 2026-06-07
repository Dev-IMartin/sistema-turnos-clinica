package com.clinica;

import com.clinica.menu.MenuPrincipal;
import com.clinica.util.ConexionDB;

/**
 * Clase principal del sistema de gestion de turnos medicos.
 * Clinica San Juan S.A.
 *
 * @author Ivan [Apellido]
 * @version 1.0 - TP3 Seminario de Practica de Informatica
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando sistema...");

        try {
            // Verificar conexion a la base de datos
            ConexionDB.getInstancia();
            System.out.println("✓ Conexión a base de datos establecida.");

            // Lanzar menu principal
            MenuPrincipal menu = new MenuPrincipal();
            menu.iniciar();

        } catch (Exception e) {
            System.err.println("Error critico al iniciar el sistema: " + e.getMessage());
            System.err.println("Verifique que MySQL este activo y la base de datos 'clinica_db' exista.");
            e.printStackTrace();
        }
    }
}
