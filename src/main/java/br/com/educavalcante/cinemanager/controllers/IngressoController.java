package br.com.educavalcante.cinemanager.controllers;

import br.com.educavalcante.cinemanager.models.DAOs.IngressoDAO;
import br.com.educavalcante.cinemanager.models.entities.Ingresso;
import br.com.educavalcante.cinemanager.utils.TipoIngresso;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class IngressoController {
    private final IngressoDAO ingressoDAO;

    public IngressoController(IngressoDAO ingressoDAO) {
        this.ingressoDAO = ingressoDAO;
    }
    
    public List<Ingresso> buscarIngressosPorUsuario(int usuarioId) {
        return ingressoDAO.buscarIngressosPorUsuario(usuarioId);
    }
    
    public Ingresso gerarIngresso(Connection conn, int[] assento, int sessaoId, int usuarioId, int numeroSala, String filme, LocalDateTime dataHora, double preco, TipoIngresso tipo) throws SQLException {
        return ingressoDAO.gerarIngresso(conn, assento, sessaoId, usuarioId, numeroSala, filme, dataHora, preco, tipo);
    }
    
    public double calcularTotalVendas() {
        return ingressoDAO.calcularTotalVendas();
    }
}
