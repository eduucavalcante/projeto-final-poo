package br.com.educavalcante.cinemanager.controllers;

import br.com.educavalcante.cinemanager.models.entities.Usuario;
import br.com.educavalcante.cinemanager.models.DAOs.UsuarioDAO;
import br.com.educavalcante.cinemanager.utils.Role;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    public Usuario entrar(String email, String senhaPlana) {
        Usuario user = usuarioDAO.buscarPorEmail(email);
        
        if(user == null) {
            return null;
        }
        
        if(verificarSenha(senhaPlana, user)) {
            return user;
        }
        
        return null;
    }
    
    public Usuario criarConta(String nome, String email, String senha, Role role) {
        String hash = BCrypt.hashpw(senha, BCrypt.gensalt(10));
        return usuarioDAO.criarUsuario(nome, email, hash, role);
    }
    
    public boolean verificarSenha(String senhaPlana, Usuario usuario) {
        String hash = usuario.getSenha();
        return BCrypt.checkpw(senhaPlana, hash);
    }
}
