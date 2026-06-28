package br.com.educavalcante.cinemanager;

import br.com.educavalcante.cinemanager.views.*;
import br.com.educavalcante.cinemanager.models.DAOs.ConnectionFactory;
import br.com.educavalcante.cinemanager.models.DAOs.UsuarioDAO;
import br.com.educavalcante.cinemanager.controllers.UsuarioController;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.EventQueue;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static void main(String[] args) {
        // Configuração inicial
        ConnectionFactory.setupTables();
        final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaAdmin.class.getName());
        
        // Injeção de dependências
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioController usuarioController = new UsuarioController(usuarioDAO);
        
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        // Iniciar janelas
        EventQueue.invokeLater(() -> new TelaLogin(usuarioController).setVisible(true));
    }
}
