package br.com.educavalcante.cinemanager.models.DAOs;

import br.com.educavalcante.cinemanager.models.entities.Filme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {
    public List<Filme> buscarFilmes() {
        List<Filme> filmes = new ArrayList<>();
        
        String sql = "SELECT * FROM filmes";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String sinopse = rs.getString("sinopse");
                String genero = rs.getString("genero");
                int duracao = rs.getInt("duracao");
                boolean emCartaz = rs.getBoolean("em_cartaz");
                
                Filme filme = new Filme(id, titulo, sinopse, genero, duracao, emCartaz);
                filmes.add(filme);
            }
            
            return filmes;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar filmes: " + e.getMessage(), e);
        }
    }
    
    public List<Filme> buscarFilmesEmCartaz() {
        List<Filme> filmes = new ArrayList<>();
        
        String sql = "SELECT * FROM filmes WHERE em_cartaz = 1";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String sinopse = rs.getString("sinopse");
                String genero = rs.getString("genero");
                int duracao = rs.getInt("duracao");
                boolean emCartaz = rs.getBoolean("em_cartaz");
                
                Filme filme = new Filme(id, titulo, sinopse, genero, duracao, emCartaz);
                filmes.add(filme);
            }
            
            return filmes;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar filmes: " + e.getMessage(), e);
        }
    }
    
    public Filme buscarFilme(int id) {
        String sql = "SELECT * FROM filmes WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try(ResultSet rs = stmt.executeQuery()){                
                if(rs.next()) {
                    String titulo = rs.getString("titulo");
                    String sinopse = rs.getString("sinopse");
                    String genero = rs.getString("genero");
                    int duracao = rs.getInt("duracao");
                    boolean emCartaz = rs.getBoolean("em_cartaz");
                    
                    return new Filme(id, titulo, sinopse, genero, duracao, emCartaz);
                }
                return null;
            }
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar filme: " + e.getMessage(), e);
        }
    }
    
    public Filme cadastrarFilme(String titulo, String sinopse, String genero, int duracao) {
        Filme filme = new Filme(titulo, sinopse, genero, duracao);
        
        String sql = "INSERT INTO filmes (titulo, sinopse, genero, duracao, em_cartaz) VALUES (?, ?, ?, ?, 0)";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, filme.getTitulo());
            stmt.setString(2, filme.getSinopse());
            stmt.setString(3, filme.getGenero());
            stmt.setInt(4, filme.getDuracao());
            
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    filme.setId(rs.getInt(1));
                    return filme;
                }
            }
            return null;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao cadastrar filme: " + e);
        }
    }
    
    public void editarFilme(int id, String titulo, String sinopse, String genero, int duracao) {
        String sql = "UPDATE filmes SET titulo = ?, sinopse = ?, genero = ?, duracao = ? WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, titulo);
            stmt.setString(2, sinopse);
            stmt.setString(3, genero);
            stmt.setInt(4, duracao);
            stmt.setInt(5, id);
            
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao editar filme: " + e);
        }
    }
    
    public void editarFilme(int id, boolean emCartaz) {
        String sql = "UPDATE filmes SET em_cartaz = ? WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, emCartaz);
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao editar filme: " + e);
        }
    }
    
    public void excluirFilme(int id) {
        String sql = "DELETE FROM filmes WHERE id = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao excluir filme: " + e);
        }
    }
}
