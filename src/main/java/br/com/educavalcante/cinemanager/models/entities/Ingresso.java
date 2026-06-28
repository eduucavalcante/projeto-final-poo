package br.com.educavalcante.cinemanager.models.entities;

import java.time.LocalDateTime;

public abstract class Ingresso {
    private int id;
    private int usuarioId;
    private int sessaoId;
    private int numeroSala, numeroAssento, numeroFileira;
    private String filme;
    private LocalDateTime dataHora;
    private double preco;

    public Ingresso(
            int usuarioId,
            int sessaoId,
            int numeroSala,
            int numeroAssento,
            int numeroFileira,
            String filme,
            LocalDateTime dataHora,
            double preco) {
        this.usuarioId = usuarioId;
        this.sessaoId = sessaoId;
        this.numeroSala = numeroSala;
        this.numeroAssento = numeroAssento;
        this.numeroFileira = numeroFileira;
        this.filme = filme;
        this.dataHora = dataHora;
        this.preco = preco;
    }
    
    public Ingresso(int id,
            int usuarioId,
            int sessaoId,
            int numeroSala,
            int numeroAssento,
            int numeroFileira,
            String filme,
            LocalDateTime dataHora,
            double preco) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.sessaoId = sessaoId;
        this.numeroSala = numeroSala;
        this.numeroAssento = numeroAssento;
        this.numeroFileira = numeroFileira;
        this.filme = filme;
        this.dataHora = dataHora;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(int sessaoId) {
        this.sessaoId = sessaoId;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(int numeroSala) {
        this.numeroSala = numeroSala;
    }

    public int getNumeroAssento() {
        return numeroAssento;
    }

    public void setNumeroAssento(int numeroAssento) {
        this.numeroAssento = numeroAssento;
    }

    public int getNumeroFileira() {
        return numeroFileira;
    }

    public void setNumeroFileira(int numeroFileira) {
        this.numeroFileira = numeroFileira;
    }
    
    public String getFilme() {
        return filme;
    }

    public void setFilme(String filme) {
        this.filme = filme;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public abstract double calcularPreco();
}
