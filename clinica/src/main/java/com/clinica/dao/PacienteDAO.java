package com.clinica.dao;

import com.clinica.modelo.Paciente;
import com.clinica.util.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Paciente.
 * Maneja todas las operaciones CRUD con la base de datos MySQL.
 */
public class PacienteDAO {

    /**
     * Inserta un nuevo paciente en la base de datos.
     */
    public boolean insertar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO pacientes (nombre, apellido, dni, telefono, fecha_nacimiento, obra_social, email) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, paciente.getNombre());
        ps.setString(2, paciente.getApellido());
        ps.setString(3, paciente.getDni());
        ps.setString(4, paciente.getTelefono());
        ps.setDate(5, Date.valueOf(paciente.getFechaNacimiento()));
        ps.setString(6, paciente.getObraSocial());
        ps.setString(7, paciente.getEmail());

        int filas = ps.executeUpdate();

        if (filas > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                paciente.setId(generatedKeys.getInt(1));
            }
            return true;
        }
        return false;
    }

    /**
     * Busca un paciente por su ID.
     */
    public Paciente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapearPaciente(rs);
        }
        return null;
    }

    /**
     * Busca pacientes por apellido (busqueda parcial, no sensible a mayusculas).
     */
    public List<Paciente> buscarPorApellido(String apellido) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE apellido LIKE ? ORDER BY apellido, nombre";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + apellido + "%");

        ResultSet rs = ps.executeQuery();
        List<Paciente> resultado = new ArrayList<>();
        while (rs.next()) {
            resultado.add(mapearPaciente(rs));
        }
        return resultado;
    }

    /**
     * Busca un paciente por DNI.
     */
    public Paciente buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE dni = ?";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, dni);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapearPaciente(rs);
        }
        return null;
    }

    /**
     * Retorna todos los pacientes ordenados por apellido.
     */
    public List<Paciente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM pacientes ORDER BY apellido, nombre";
        Connection conn = ConexionDB.getInstancia().getConexion();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Paciente> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(mapearPaciente(rs));
        }
        return lista;
    }

    /**
     * Actualiza los datos de un paciente existente.
     */
    public boolean actualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE pacientes SET nombre=?, apellido=?, telefono=?, obra_social=?, email=? WHERE id=?";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, paciente.getNombre());
        ps.setString(2, paciente.getApellido());
        ps.setString(3, paciente.getTelefono());
        ps.setString(4, paciente.getObraSocial());
        ps.setString(5, paciente.getEmail());
        ps.setInt(6, paciente.getId());

        return ps.executeUpdate() > 0;
    }

    /**
     * Elimina un paciente por su ID.
     */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM pacientes WHERE id = ?";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    // Mapeo de ResultSet a objeto Paciente
    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        return new Paciente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getString("telefono"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getString("obra_social"),
                rs.getString("email")
        );
    }
}
