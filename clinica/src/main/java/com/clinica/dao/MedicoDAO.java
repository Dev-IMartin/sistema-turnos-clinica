package com.clinica.dao;

import com.clinica.modelo.Medico;
import com.clinica.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Medico.
 */
public class MedicoDAO {

    public boolean insertar(Medico medico) throws SQLException {
        String sql = "INSERT INTO medicos (nombre, apellido, dni, telefono, especialidad, matricula, honorarios) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, medico.getNombre());
        ps.setString(2, medico.getApellido());
        ps.setString(3, medico.getDni());
        ps.setString(4, medico.getTelefono());
        ps.setString(5, medico.getEspecialidad());
        ps.setString(6, medico.getMatricula());
        ps.setDouble(7, medico.getHonorarios());

        int filas = ps.executeUpdate();
        if (filas > 0) {
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) medico.setId(keys.getInt(1));
            return true;
        }
        return false;
    }

    public Medico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM medicos WHERE id = ?";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return mapearMedico(rs);
        return null;
    }

    public List<Medico> buscarPorEspecialidad(String especialidad) throws SQLException {
        String sql = "SELECT * FROM medicos WHERE especialidad LIKE ? ORDER BY apellido";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + especialidad + "%");
        ResultSet rs = ps.executeQuery();
        List<Medico> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapearMedico(rs));
        return lista;
    }

    public List<Medico> listarTodos() throws SQLException {
        String sql = "SELECT * FROM medicos ORDER BY apellido, nombre";
        Connection conn = ConexionDB.getInstancia().getConexion();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Medico> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapearMedico(rs));
        return lista;
    }

    private Medico mapearMedico(ResultSet rs) throws SQLException {
        return new Medico(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getString("telefono"),
                rs.getString("especialidad"),
                rs.getString("matricula"),
                rs.getDouble("honorarios")
        );
    }
}
