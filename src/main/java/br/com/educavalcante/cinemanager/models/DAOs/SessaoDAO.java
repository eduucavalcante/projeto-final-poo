package br.com.educavalcante.cinemanager.models.DAOs;

import br.com.educavalcante.cinemanager.models.entities.Sessao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessaoDAO {
    public List<Sessao> buscarSessoes() {
        List<Sessao> sessoes = new ArrayList<>();
        
        String sql = "SELECT * FROM sessoes";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                int id = rs.getInt("id");
                int filmeId = rs.getInt("filmeId");
                int salaId = rs.getInt("salaId");
                LocalDateTime dataHora = LocalDateTime.parse(rs.getString("dataHora"));
                double precoBase = rs.getDouble("precoBase");
                
                Sessao sessao = new Sessao(id, filmeId, salaId, dataHora, precoBase);
                sessoes.add(sessao);
            }
            
            return sessoes;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar sessoes: " + e.getMessage(), e);
        }
    }
    
    public List<Sessao> buscarSessoesPorFilme(int filmeId) {
        List<Sessao> sessoes = new ArrayList<>();
        
        String sql = "SELECT * FROM sessoes WHERE filmeId = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, filmeId);
            
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    int salaId = rs.getInt("salaId");
                    LocalDateTime dataHora = LocalDateTime.parse(rs.getString("dataHora"));
                    double precoBase = rs.getDouble("precoBase");

                    Sessao sessao = new Sessao(id, filmeId, salaId, dataHora, precoBase);
                    sessoes.add(sessao);
                }

                return sessoes;
            }
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar sessoes: " + e.getMessage(), e);
        }
    }
    
    public Sessao buscarSessaoPorId(int sessaoId) {
        String sql = "SELECT * FROM sessoes WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sessaoId);
            
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    int salaId = rs.getInt("salaId");
                    int filmeId = rs.getInt("filmeId");
                    LocalDateTime dataHora = LocalDateTime.parse(rs.getString("dataHora"));
                    double precoBase = rs.getDouble("precoBase");

                    return new Sessao(id, filmeId, salaId, dataHora, precoBase);
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar sessoes: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    public Sessao criarSessao(int filmeId, int salaId, LocalDateTime dataHora, double precoBase) {
        Sessao sessao = new Sessao(filmeId, salaId, dataHora, precoBase);
        
        String sql = "INSERT INTO sessoes (filmeId, salaId, dataHora, precoBase) VALUES (?, ?, ?, ?)";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sessao.getFilmeId());
            stmt.setInt(2, sessao.getSalaId());
            stmt.setString(3, sessao.getDataHora().toString());
            stmt.setDouble(4, precoBase);
            
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sessao.setId(rs.getInt(1));
                    return sessao;
                }
            }
            
            return null;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao cadastrar sessao: " + e);
        }
    }
    
    public void editarSessao(int id, LocalDateTime dataHora, double precoBase) {
        String sql = "UPDATE sessoes SET dataHora = ?, precoBase = ? WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, dataHora.toString());
            stmt.setDouble(2, precoBase);
            stmt.setInt(3, id);
            
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao editar sessao: " + e);
        }
    }
    
    public void excluirSessao(int id) {
        String sql = "DELETE FROM sessoes WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao excluir sessao: " + e);
        }
    }
}
