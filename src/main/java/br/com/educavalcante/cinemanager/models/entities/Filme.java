package br.com.educavalcante.cinemanager.models.entities;

public class Filme {
    private int id;
    private String titulo;
    private String sinopse;
    private String genero;
    private int duracao;
    private boolean emCartaz;

    public Filme(String titulo, String sinopse, String genero, int duracao) {
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.genero = genero;
        this.duracao = duracao;
        this.emCartaz = false;
    }
    
    public Filme(int id, String titulo, String sinopse, String genero, int duracao, boolean emCartaz) {
        this.id = id;
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.genero = genero;
        this.duracao = duracao;
        this.emCartaz = emCartaz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public boolean isEmCartaz() {
        return emCartaz;
    }

    public void setEmCartaz(boolean emCartaz) {
        this.emCartaz = emCartaz;
    }
}
