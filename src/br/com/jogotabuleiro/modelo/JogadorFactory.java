package br.com.jogotabuleiro.modelo;

public class JogadorFactory {

    public static Jogador criarJogador(String tipo, String cor) {
        switch (tipo) {
            case "Normal":
                return new JogadorNormal(cor);
            case "Azarado":
                return new JogadorAzarado(cor);
            case "Sortudo":
                return new JogadorSortudo(cor);
            default:
                throw new IllegalArgumentException("Tipo inválido: " + tipo);
        }
    }
}