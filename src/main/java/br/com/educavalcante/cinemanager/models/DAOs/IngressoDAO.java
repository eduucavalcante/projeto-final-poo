package br.com.educavalcante.cinemanager.models.DAOs;

import br.com.educavalcante.cinemanager.models.entities.Ingresso;
import br.com.educavalcante.cinemanager.models.entities.IngressoInteira;
import br.com.educavalcante.cinemanager.models.entities.IngressoMeia;
import br.com.educavalcante.cinemanager.utils.TipoIngresso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IngressoDAO {
    public List<Ingresso> buscarIngressosPorUsuario(int usuarioId) {
        List<Ingresso> ingressos = new ArrayList<>();
        
        String sql = "SELECT * FROM ingressos WHERE usuarioId = ? ORDER BY id DESC";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    int sessaoId = rs.getInt("sessaoId");
                    int numeroSala = rs.getInt("numero_sala");
                    int numeroAssento = rs.getInt("numero_assento");
                    int numeroFileira = rs.getInt("numero_fileira");
                    String filme = rs.getString("filme");
                    LocalDateTime dataHora = LocalDateTime.parse(rs.getString("dataHora"));
                    double preco = rs.getDouble("preco");
                    String tipo = rs.getString("tipo");
                    
                    Ingresso ingresso;
                    
                    if(tipo.equals("MEIA")) {
                        ingresso = new IngressoMeia(id, usuarioId, sessaoId, numeroSala, numeroAssento, numeroFileira, filme, dataHora, preco);
                    } else {
                        ingresso = new IngressoInteira(id, usuarioId, sessaoId, numeroSala, numeroAssento, numeroFileira, filme, dataHora, preco);
                    }
                    ingressos.add(ingresso);
                }

                return ingressos;
            }
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar ingressos: " + e.getMessage(), e);
        }
    }
    
    public Ingresso gerarIngresso(Connection conn, int[] assento, int sessaoId, int usuarioId, int numeroSala, String filme, LocalDateTime dataHora, double preco, TipoIngresso tipo) throws SQLException {
        String sql = "INSERT INTO ingressos (usuarioId, sessaoId, numero_sala, numero_assento, numero_fileira, filme, dataHora, preco, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Ingresso ingresso;
        
        if(tipo == TipoIngresso.MEIA) {
            ingresso = new IngressoMeia(usuarioId, sessaoId, numeroSala, assento[1], assento[0], filme, dataHora, preco);
        } else {
            ingresso = new IngressoInteira(usuarioId, sessaoId, numeroSala, assento[1], assento[0], filme, dataHora, preco);
        }
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ingresso.getUsuarioId());
            stmt.setInt(2, ingresso.getSessaoId());
            stmt.setInt(3, ingresso.getNumeroSala());
            stmt.setInt(4, ingresso.getNumeroAssento());
            stmt.setInt(5, ingresso.getNumeroFileira());
            stmt.setString(6, ingresso.getFilme());
            stmt.setString(7, ingresso.getDataHora().toString());
            stmt.setDouble(8, ingresso.calcularPreco());
            stmt.setString(9, tipo.name());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ingresso.setId(rs.getInt(1));
                    return ingresso;
                }
            }
            return null;
        }
    }
    
    public double calcularTotalVendas() {
        String sql = "SELECT COALESCE(SUM(preco), 0) AS total_vendas FROM ingressos";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble("total_vendas");
            }            
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao excluir sala: " + e);
        }
        
        return 0;
    }
}
