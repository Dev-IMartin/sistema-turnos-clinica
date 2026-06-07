package com.clinica.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexion a la base de datos MySQL.
 * Implementa el patron Singleton para reutilizar la conexion.
 */
public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/clinica_db?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires";
    private static final String USUARIO = "root";
    private static final String CLAVE = "admin123";

    private static ConexionDB instancia;
    private Connection conexion;

    // Constructor privado - patron Singleton
    private ConexionDB() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
        }
    }

    /**
     * Retorna la instancia unica de ConexionDB (Singleton).
     */
    public static ConexionDB getInstancia() throws SQLException {
        if (instancia == null || instancia.conexion.isClosed()) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    /**
     * Retorna el objeto Connection para ejecutar sentencias SQL.
     */
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
        }
        return conexion;
    }

    /**
     * Cierra la conexion activa.
     */
    public void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexion: " + e.getMessage());
        }
    }
}
