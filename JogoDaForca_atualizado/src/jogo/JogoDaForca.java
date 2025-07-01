package jogo;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class JogoDaForca extends Jogo {
    private Set<Character> letrasTentadas;

    @Override
    public void iniciar(List<String> palavras) {
        Random random = new Random();
        palavraSecreta = palavras.get(random.nextInt(palavras.size())).toLowerCase();
        letrasDescobertas = new char[palavraSecreta.length()];
        for (int i = 0; i < letrasDescobertas.length; i++) {
            letrasDescobertas[i] = '_';
        }
        letrasTentadas = new HashSet<>();
        erros = 0;
    }

    @Override
    public boolean tentarLetra(char letra) {
        letra = Character.toLowerCase(letra);
        if (letrasTentadas.contains(letra)) {
            return false;
        }
        letrasTentadas.add(letra);
        boolean acerto = false;
        for (int i = 0; i < palavraSecreta.length(); i++) {
            if (palavraSecreta.charAt(i) == letra) {
                letrasDescobertas[i] = letra;
                acerto = true;
            }
        }
        if (!acerto) {
            erros++;
        }
        return acerto;
    }

    @Override
    public boolean isVitoria() {
        for (char c : letrasDescobertas) {
            if (c == '_') return false;
        }
        return true;
    }

    @Override
    public boolean isDerrota() {
        return erros >= maxErros;
    }

    @Override
    public String getPalavraAtual() {
        return new String(letrasDescobertas);
    }

    @Override
    public int getErros() {
        return erros;
    }

    @Override
    public int getMaxErros() {
        return maxErros;
    }

    public Set<Character> getLetrasTentadas() {
        return letrasTentadas;
    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }
}
