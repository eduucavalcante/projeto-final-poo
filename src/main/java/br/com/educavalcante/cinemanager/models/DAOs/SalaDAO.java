package br.com.educavalcante.cinemanager.models.DAOs;

import br.com.educavalcante.cinemanager.models.entities.Sala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {
    public List<Sala> buscarSalas() {
        List<Sala> salas = new ArrayList<>();
        
        String sql = "SELECT * FROM salas";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                int id = rs.getInt("id");
                int numero = rs.getInt("numero");
                int totalFileiras = rs.getInt("total_fileiras");
                int totalAssentosPorFileira = rs.getInt("total_assentos_pf");
                
                Sala sala = new Sala(id, numero, totalFileiras, totalAssentosPorFileira);
                salas.add(sala);
            }
            
            return salas;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar salas: " + e.getMessage(), e);
        }
    }
    
    public Sala buscarSala(int id) {        
        String sql = "SELECT * FROM salas WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    int numero = rs.getInt("numero");
                    int totalFileiras = rs.getInt("total_fileiras");
                    int totalAssentosPorFileira = rs.getInt("total_assentos_pf");

                    return new Sala(id, numero, totalFileiras, totalAssentosPorFileira);
                }
            }            
            return null;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar sala: " + e.getMessage(), e);
        }
    }
    
    public Sala buscarSalaPorNumero(int numero) {        
        String sql = "SELECT * FROM salas WHERE numero = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, numero);
            
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    int id = rs.getInt("id");
                    int totalFileiras = rs.getInt("total_fileiras");
                    int totalAssentosPorFileira = rs.getInt("total_assentos_pf");

                    return new Sala(id, numero, totalFileiras, totalAssentosPorFileira);
                }
            }            
            return null;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar sala: " + e.getMessage(), e);
        }
    }
    
    public Sala cadastrarSala(int numero, int totalFileiras, int totalAssentosPorFileira) {
        Sala sala = new Sala(numero, totalFileiras, totalAssentosPorFileira);
        
        String sql = "INSERT INTO salas (numero, total_fileiras, total_assentos_pf) VALUES (?, ?, ?)";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sala.getNumero());
            stmt.setInt(2, sala.getTotalFileiras());
            stmt.setInt(3, sala.getTotalAssentosPorFileira());
            
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sala.setId(rs.getInt(1));
                    return sala;
                }
            }
            
            return null;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao cadastrar sala: " + e);
        }
    }
    
    public void editarSala(int id, int numero, int totalFileiras, int totalAssentosPorFileira) {
        String sql = "UPDATE salas SET numero = ?, total_fileiras = ?, total_assentos_pf = ? WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, numero);
            stmt.setInt(2, totalFileiras);
            stmt.setInt(3, totalAssentosPorFileira);
            stmt.setInt(4, id);
            
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao editar sala: " + e);
        }
    }
    
    public void excluirSala(int id) {
        String sql = "DELETE FROM salas WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao excluir sala: " + e);
        }
    }
}
