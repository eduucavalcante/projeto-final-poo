package br.com.educavalcante.cinemanager.controllers;

import br.com.educavalcante.cinemanager.models.DAOs.ConnectionFactory;
import br.com.educavalcante.cinemanager.models.DAOs.FilmeDAO;
import br.com.educavalcante.cinemanager.models.DAOs.ReservaDAO;
import br.com.educavalcante.cinemanager.models.DAOs.SalaDAO;
import br.com.educavalcante.cinemanager.models.DAOs.SessaoDAO;
import br.com.educavalcante.cinemanager.models.entities.Ingresso;
import br.com.educavalcante.cinemanager.models.entities.Sessao;
import br.com.educavalcante.cinemanager.utils.TipoIngresso;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class SessaoController {
    private final SessaoDAO sessaoDAO;
    private final IngressoController ingressoController;

    public SessaoController(SessaoDAO sessaoDAO, IngressoController ingressoController) {
        this.sessaoDAO = sessaoDAO;
        this.ingressoController = ingressoController;
    }
    
    public List<Sessao> listarSessoes() {
        return sessaoDAO.buscarSessoes();
    }
    
    public List<Sessao> listarSessoesPorFilme(int filmeId) {
        return sessaoDAO.buscarSessoesPorFilme(filmeId);
    }
    
    public Sessao buscarSessaoPorId(int sessaoid) {
        return sessaoDAO.buscarSessaoPorId(sessaoid);
    }
    
    public Sessao cadastrarSessao(int filmeId, int salaId, LocalDateTime dataHora, double precoBase) {
        return sessaoDAO.criarSessao(filmeId, salaId, dataHora, precoBase);
    }
    
    public void editarSessao(int id, LocalDateTime dataHora, double precoBase) {
        sessaoDAO.editarSessao(id, dataHora, precoBase);
    }
    
    public void excluirSessao(int id) {
        sessaoDAO.excluirSessao(id);
    }
    
    public boolean temAssentosDisponiveis(int sessaoId, int salaId) {
        return new ReservaDAO(sessaoDAO).verificarAssentos(sessaoId, salaId);
    }
    
    public List<int[]> buscarAssentosDisponiveis(int sessaoId) {
        return new ReservaDAO(sessaoDAO).buscarAssentosOcupados(sessaoId);
    }
    
    public Ingresso comprarIngresso(int sessaoId, int usuarioId, int[] assento, TipoIngresso tipo) {
        Sessao sessao = this.buscarSessaoPorId(sessaoId);
        int numeroSala = new SalaDAO().buscarSala(sessao.getSalaId()).getNumero();
        String filme = new FilmeDAO().buscarFilme(sessao.getFilmeId()).getTitulo();
        
        try (Connection conn = ConnectionFactory.createConnection()) {
            conn.setAutoCommit(false);
            try {
                new ReservaDAO(sessaoDAO).confirmarReserva(conn, sessaoId, usuarioId, assento);
                Ingresso ingresso = this.ingressoController.gerarIngresso(conn, assento, sessaoId, usuarioId, numeroSala, filme, sessao.getDataHora(), sessao.getPrecoBase(), tipo);
                
                conn.commit();
                return ingresso;
            } catch(SQLException e) {
                conn.rollback();
                throw new RuntimeException("Erro ao realizar compra, operação revertida: " + e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch(SQLException e) {
            throw new RuntimeException("Erro de conexão: " + e);
        }
    }
}
