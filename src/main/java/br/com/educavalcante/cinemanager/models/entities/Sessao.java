package br.com.educavalcante.cinemanager.models.entities;

import java.time.LocalDateTime;

public class Sessao {
    private int id;
    private int filmeId;
    private int salaId;
    private LocalDateTime dataHora;
    private double precoBase;

    public Sessao(int filmeId, int salaId, LocalDateTime dataHora, double precoBase) {
        this.filmeId = filmeId;
        this.salaId = salaId;
        this.dataHora = dataHora;
        this.precoBase = precoBase;
    }

    public Sessao(int id, int filmeId, int salaId, LocalDateTime dataHora, double precoBase) {
        this.id = id;
        this.filmeId = filmeId;
        this.salaId = salaId;
        this.dataHora = dataHora;
        this.precoBase = precoBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(int filmeId) {
        this.filmeId = filmeId;
    }

    public int getSalaId() {
        return salaId;
    }

    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }
    
    
}
