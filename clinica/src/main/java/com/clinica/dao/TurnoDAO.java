package com.clinica.dao;

import com.clinica.modelo.*;
import com.clinica.util.ConexionDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DAO para la entidad Turno.
 * Contiene algoritmos de busqueda y ordenacion.
 */
public class TurnoDAO {

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private MedicoDAO medicoDAO = new MedicoDAO();

    public boolean insertar(Turno turno) throws SQLException {
        String sql = "INSERT INTO turnos (paciente_id, medico_id, fecha_hora, estado, observaciones) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, turno.getPaciente().getId());
        ps.setInt(2, turno.getMedico().getId());
        ps.setTimestamp(3, Timestamp.valueOf(turno.getFechaHora()));
        ps.setString(4, turno.getEstado().name());
        ps.setString(5, turno.getObservaciones());

        int filas = ps.executeUpdate();
        if (filas > 0) {
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) turno.setId(keys.getInt(1));
            return true;
        }
        return false;
    }

    public Turno buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM turnos WHERE id = ?";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return mapearTurno(rs);
        return null;
    }

    public List<Turno> listarTodos() throws SQLException {
        String sql = "SELECT * FROM turnos ORDER BY fecha_hora";
        Connection conn = ConexionDB.getInstancia().getConexion();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Turno> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapearTurno(rs));
        return lista;
    }

    public List<Turno> listarPorPaciente(int pacienteId) throws SQLException {
        String sql = "SELECT * FROM turnos WHERE paciente_id = ? ORDER BY fecha_hora DESC";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, pacienteId);
        ResultSet rs = ps.executeQuery();
        List<Turno> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapearTurno(rs));
        return lista;
    }

    public List<Turno> listarPorMedico(int medicoId) throws SQLException {
        String sql = "SELECT * FROM turnos WHERE medico_id = ? AND estado NOT IN ('CANCELADO','ATENDIDO') ORDER BY fecha_hora";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, medicoId);
        ResultSet rs = ps.executeQuery();
        List<Turno> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapearTurno(rs));
        return lista;
    }

    public boolean actualizarEstado(int id, EstadoTurno nuevoEstado) throws SQLException {
        String sql = "UPDATE turnos SET estado = ? WHERE id = ?";
        Connection conn = ConexionDB.getInstancia().getConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nuevoEstado.name());
        ps.setInt(2, id);
        return ps.executeUpdate() > 0;
    }

    /**
     * Algoritmo de busqueda binaria sobre lista ordenada de turnos por fecha.
     * Retorna el indice del turno mas cercano a la fecha buscada.
     */
    public int busquedaBinariaPorFecha(List<Turno> turnosOrdenados, LocalDateTime fechaBuscada) {
        int izq = 0;
        int der = turnosOrdenados.size() - 1;

        while (izq <= der) {
            int medio = (izq + der) / 2;
            LocalDateTime fechaMedio = turnosOrdenados.get(medio).getFechaHora();

            int cmp = fechaMedio.compareTo(fechaBuscada);
            if (cmp == 0) return medio;
            else if (cmp < 0) izq = medio + 1;
            else der = medio - 1;
        }
        return -1; // No encontrado exacto
    }

    /**
     * Ordena una lista de turnos usando Collections.sort (que usa merge sort internamente).
     * Los turnos implementan Comparable por fecha.
     */
    public List<Turno> ordenarPorFecha(List<Turno> turnos) {
        List<Turno> copia = new ArrayList<>(turnos);
        Collections.sort(copia);
        return copia;
    }

    private Turno mapearTurno(ResultSet rs) throws SQLException {
        int pacienteId = rs.getInt("paciente_id");
        int medicoId = rs.getInt("medico_id");

        Paciente paciente = pacienteDAO.buscarPorId(pacienteId);
        Medico medico = medicoDAO.buscarPorId(medicoId);

        return new Turno(
                rs.getInt("id"),
                paciente,
                medico,
                rs.getTimestamp("fecha_hora").toLocalDateTime(),
                EstadoTurno.valueOf(rs.getString("estado")),
                rs.getString("observaciones")
        );
    }
}
