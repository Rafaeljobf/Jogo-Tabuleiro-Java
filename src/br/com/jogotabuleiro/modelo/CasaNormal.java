package br.com.jogotabuleiro.modelo;

public class CasaNormal extends Casa {

    @Override
    public String acao(Jogador jogador, Jogo jogo) {
        return "Casa normal. Nada acontece.";
    }
}
