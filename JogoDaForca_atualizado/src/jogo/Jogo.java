package jogo;

import java.util.List;

public abstract class Jogo {
    protected String palavraSecreta;
    protected char[] letrasDescobertas;
    protected int erros;
    protected int maxErros = 6;

    public abstract void iniciar(List<String> palavras);
    public abstract boolean tentarLetra(char letra);
    public abstract boolean isVitoria();
    public abstract boolean isDerrota();
    public abstract String getPalavraAtual();
    public abstract int getErros();
    public abstract int getMaxErros();
}
