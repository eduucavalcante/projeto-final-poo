package br.com.educavalcante.cinemanager.utils;

import br.com.educavalcante.cinemanager.models.entities.Ingresso;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorIngressos {
    public static void exportarTXT(List<Ingresso> ingressos, String caminho) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));

        writer.write("=========== INGRESSOS ===========");
        writer.newLine();
        writer.newLine();

        for (Ingresso ingresso : ingressos) {
            writer.write("Filme: " + ingresso.getFilme());
            writer.newLine();

            writer.write("Sala: " + ingresso.getNumeroSala());
            writer.newLine();

            writer.write("Data/Hora: " + ingresso.getDataHora());
            writer.newLine();

            writer.write("Assento: Fila "
                    + ingresso.getNumeroFileira()
                    + " Posição"
                    + ingresso.getNumeroAssento() + " na fila");

            writer.newLine();

            writer.write("Preço: R$ "
                    + String.format("%.2f", ingresso.getPreco()));

            writer.newLine();
            writer.write("--------------------------------");
            writer.newLine();
        }

        writer.close();
    }
}