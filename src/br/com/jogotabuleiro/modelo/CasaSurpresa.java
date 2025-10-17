package br.com.jogotabuleiro.modelo;

public class CasaSurpresa extends Casa {

    @Override
    public String acao(Jogador jogador, Jogo jogo) {
        return jogo.transformarJogador(jogador);
    }
}
