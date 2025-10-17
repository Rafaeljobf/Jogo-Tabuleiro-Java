package br.com.jogotabuleiro.modelo;

import java.util.List;

public class CasaMagica extends Casa {

    @Override
    public String acao(Jogador jogadorAtual, Jogo jogo) {
        List<Jogador> jogadores = jogo.getJogadores();
        Jogador jogadorMaisAtras = null;
        int minPosicao = jogadorAtual.getPosicao();

        // Encontra o jogador na menor posição (que não seja o jogador atual)
        for (Jogador outroJogador : jogadores) {
            if (outroJogador != jogadorAtual && outroJogador.getPosicao() < minPosicao) {
                minPosicao = outroJogador.getPosicao();
                jogadorMaisAtras = outroJogador;
            }
        }

        if (jogadorMaisAtras != null) {
            // Se encontrou alguém, troca as posições
            int posJogadorAtual = jogadorAtual.getPosicao();
            jogadorAtual.setPosicao(jogadorMaisAtras.getPosicao());
            jogadorMaisAtras.setPosicao(posJogadorAtual);
            return "CASA MÁGICA! Você trocou de lugar com o jogador " + jogadorMaisAtras.getCor() + "!";
        } else {
            // Se não encontrou ninguém (jogador atual já é o último), nada acontece
            return "CASA MÁGICA! Você já é o último no jogo, então nada acontece.";
        }
    }
}
