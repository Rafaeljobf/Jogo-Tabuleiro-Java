package br.com.jogotabuleiro.modelo;

public class CasaSorte extends Casa {

    @Override
    public String acao(Jogador jogador, Jogo jogo) {
        if (jogador instanceof JogadorAzarado) {
            return "CASA SORTE! O jogador " + jogador.getCor() + " é Azarado e não ganhou o bônus.";
        } else {
            jogador.mover(3);
            return "CASA SORTE! O jogador " + jogador.getCor() + " avançou 3 casas extras!";
        }
    }
}
