package br.com.jogotabuleiro.modelo;

public class CasaBloqueio extends Casa {

    @Override
    public String acao(Jogador jogador, Jogo jogo) {
        jogador.bloquear();
        return "CASA BLOQUEIO! O jogador " + jogador.getCor() + " foi bloqueado e perderá a próxima vez!";
    }
}
