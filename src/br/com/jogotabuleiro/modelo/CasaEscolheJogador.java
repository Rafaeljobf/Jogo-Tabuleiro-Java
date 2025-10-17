package br.com.jogotabuleiro.modelo;

public class CasaEscolheJogador extends Casa {

    @Override
    public String acao(Jogador jogador, Jogo jogo) {
        return "ACTION:CHOOSE_PLAYER_TO_RESET";
    }
}
