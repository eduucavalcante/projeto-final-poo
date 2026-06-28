package br.com.educavalcante.cinemanager.models.entities;

public class Sala {
    private int id;
    private int numero;
    private int totalFileiras;
    private int totalAssentosPorFileira;

    public Sala(int numero, int totalFileiras, int totalAssentosPorFileira) {
        this.numero = numero;
        this.totalFileiras = totalFileiras;
        this.totalAssentosPorFileira = totalAssentosPorFileira;
    }
    
    public Sala(int id, int numero, int totalFileiras, int totalAssentosPorFileira) {
        this.id = id;
        this.numero = numero;
        this.totalFileiras = totalFileiras;
        this.totalAssentosPorFileira = totalAssentosPorFileira;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTotalFileiras() {
        return totalFileiras;
    }

    public void setTotalFileiras(int totalFileiras) {
        this.totalFileiras = totalFileiras;
    }

    public int getTotalAssentosPorFileira() {
        return totalAssentosPorFileira;
    }

    public void setTotalAssentosPorFileira(int totalAssentosPorFileira) {
        this.totalAssentosPorFileira = totalAssentosPorFileira;
    }
}
