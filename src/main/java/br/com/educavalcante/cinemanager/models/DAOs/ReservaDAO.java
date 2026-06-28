package br.com.educavalcante.cinemanager.models.DAOs;

import br.com.educavalcante.cinemanager.models.entities.Sala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private SalaDAO salaDAO;
    private SessaoDAO sessaoDAO;
    
    public ReservaDAO(SessaoDAO sessaoDAO) {
        this.salaDAO = new SalaDAO();
        this.sessaoDAO = sessaoDAO;
    }
    
    public boolean verificarAssentos(int sessaoId, int salaId) {
        Sala sala = salaDAO.buscarSala(salaId);
        int capacidadeTotal = sala.getTotalFileiras() * sala.getTotalAssentosPorFileira();

        String sql = "SELECT COUNT(*) FROM reservas WHERE sessaoId = ?";
        try (Connection conn = ConnectionFactory.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sessaoId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) < capacidadeTotal;
            }
            
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar disponibilidade: " + e);
        }
    }
    
    public List<int[]> buscarAssentosOcupados(int sessaoId) {
        List<int[]> ocupados = new ArrayList<>();
        String sql = "SELECT fileira, numero FROM reservas WHERE sessaoId = ?";

        try (Connection conn = ConnectionFactory.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sessaoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ocupados.add(new int[]{rs.getInt("fileira"), rs.getInt("numero")});
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar assentos ocupados: " + e);
        }
        return ocupados;
    }
    
    public void confirmarReserva(Connection conn, int sessaoId, int usuarioId, int[] assento) throws SQLException {
        String sql = "INSERT INTO reservas (sessaoId, fileira, numero, usuarioId) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sessaoId);
            stmt.setInt(2, assento[0]);
            stmt.setInt(3, assento[1]);
            stmt.setInt(4, usuarioId);
            
            stmt.executeUpdate();
        }
    }
}
