package br.com.educavalcante.cinemanager.models.entities;

import java.time.LocalDateTime;

public class IngressoInteira extends Ingresso {

    public IngressoInteira(
            int usuarioId,
            int sessaoId,
            int numeroSala,
            int numeroAssento,
            int numeroFileira,
            String filme,
            LocalDateTime dataHora,
            double preco) {
        super(usuarioId, sessaoId, numeroSala, numeroAssento, numeroFileira, filme, dataHora, preco);
    }
    
    public IngressoInteira(int id,
            int usuarioId,
            int sessaoId,
            int numeroSala,
            int numeroAssento,
            int numeroFileira,
            String filme,
            LocalDateTime dataHora,
            double preco) {
        super(id, usuarioId, sessaoId, numeroSala, numeroAssento, numeroFileira, filme, dataHora, preco);
    }
    
    @Override
    public double calcularPreco() {
        return this.getPreco();
    }
}
