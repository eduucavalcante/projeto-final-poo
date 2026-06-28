package br.com.educavalcante.cinemanager.controllers;

import br.com.educavalcante.cinemanager.models.DAOs.SalaDAO;
import br.com.educavalcante.cinemanager.models.entities.Sala;
import java.util.List;

public class SalaController {
    private SalaDAO salaDAO;

    public SalaController(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }
    
    public List<Sala> listarSalas() {
        return salaDAO.buscarSalas();
    }
    
    public Sala buscarSalaPorId(int id) {
        return salaDAO.buscarSala(id);
    }
    
    public Sala buscarSalaPorNumero(int numero) {
        return salaDAO.buscarSalaPorNumero(numero);
    }
    
    public Sala cadastrarSala(int numero, int totalFileiras, int totalAssentosPorFileira) {
        return salaDAO.cadastrarSala(numero, totalFileiras, totalAssentosPorFileira);
    }
    
    public void editarSala(int id, int numero, int totalFileiras, int totalAssentosPorFileira) {
        salaDAO.editarSala(id, numero, totalFileiras, totalAssentosPorFileira);
    }
    
    public void removerSala(int id) {
        salaDAO.excluirSala(id);
    }
}
