package br.com.educavalcante.cinemanager.models.DAOs;

import br.com.educavalcante.cinemanager.models.entities.Usuario;
import br.com.educavalcante.cinemanager.utils.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {    
    public Usuario criarUsuario(String nome, String email, String senha, Role role) {
        Usuario user = new Usuario(nome, email, senha, role);
        
        String sql = "INSERT INTO usuarios(nome, email, senha, role) VALUES (?, ?, ?, ?)";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user.getRole().name());
            
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
            
            return user;
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao criar usuário: " + e);
        }
    }
    
    public Usuario buscarPorEmail(String busca) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, busca);
            
            try(ResultSet rs = stmt.executeQuery()){                
                if(rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    String senha = rs.getString("senha");
                    Role role = Role.valueOf(rs.getString("role"));
                    
                    return new Usuario(id, nome, email, senha, role);
                }
                return null;
            }
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por e-mail: " + e.getMessage(), e);
        }
    }
}
