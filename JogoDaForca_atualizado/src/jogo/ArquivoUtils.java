package jogo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtils {
    public static List<String> carregarPalavras(String caminhoArquivo) {
        List<String> palavras = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                palavras.add(linha.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palavras;
    }
}
