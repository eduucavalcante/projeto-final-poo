package br.com.educavalcante.cinemanager.controllers;

import br.com.educavalcante.cinemanager.models.DAOs.FilmeDAO;
import br.com.educavalcante.cinemanager.models.entities.Filme;
import java.util.List;

public class FilmeController {
    private final FilmeDAO filmeDAO;

    public FilmeController(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }
    
    public List<Filme> listarFilmes() {
        List<Filme> filmes = filmeDAO.buscarFilmes();
        
        return filmes;
    }
    
    public List<Filme> listarFilmesEmCartaz() {
        return filmeDAO.buscarFilmesEmCartaz();
    }
    
    public Filme buscarFilmePorId(int id) {
        return filmeDAO.buscarFilme(id);
    }
    
    public Filme cadastrarFilme(String titulo, String sinopse, String genero, int duracao) {
        return filmeDAO.cadastrarFilme(titulo, sinopse, genero, duracao);
    }
    
    public void colocarEmCartaz(int id) {
        filmeDAO.editarFilme(id, true);
    }
    
    public void removerDoCartaz(int id) {
        filmeDAO.editarFilme(id, false);        
    }
    
    public void editarFilme(int id, String titulo, String sinopse, String genero, int duracao) {
        filmeDAO.editarFilme(id, titulo, sinopse, genero, duracao);
    }
    
    public void removerFilme(int id) {
        filmeDAO.excluirFilme(id);
    }
}
