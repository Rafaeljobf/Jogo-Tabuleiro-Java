package br.com.jogotabuleiro.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Jogo {

    private Tabuleiro tabuleiro;
    private Dado dado1, dado2;
    private List<Jogador> jogadores;
    private int indiceJogadorAtual;
    private Jogador vencedor;

    public Jogo(){
        this.tabuleiro = new Tabuleiro();
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.jogadores = new ArrayList<>();
        this.indiceJogadorAtual = 0;
        this.vencedor = null;
    }

    public void adicionarJogador(String cor, int tipo){
        switch (tipo){
            case 1:
                jogadores.add(new JogadorNormal(cor.toUpperCase()));
                break;
            case 2:
                jogadores.add(new JogadorAzarado(cor.toUpperCase()));
                break;
            case 3:
                jogadores.add(new JogadorSortudo(cor.toUpperCase()));
                break;
        }
    }

    public void passarVez(){
        indiceJogadorAtual = (indiceJogadorAtual + 1) % jogadores.size();
    }

    public boolean existeVencedor(){
        for (Jogador jogador: jogadores){
            if (jogador.getPosicao() == 40){
                return true;
            }
        }
        return false;
    }

    public String[] executarTurno() {
        Jogador jogadorAtual = getJogadorAtual();
        int[] resultadoDados = jogadorAtual.rolarDados(dado1, dado2);
        int soma = resultadoDados[0] + resultadoDados[1];

        String msgDados = resultadoDados[0] + "," + resultadoDados[1];
        String msgSoma = String.valueOf(soma);

        jogadorAtual.mover(soma);

        String msgAcao = "";
        if (jogadorAtual.getPosicao() >= 40) {
            this.vencedor = jogadorAtual;
            // Sinal para a UI de que o jogo acabou nesta jogada
            msgAcao = "VENCEU";
        } else {
            Casa casaQueParou = this.tabuleiro.getCasa(jogadorAtual.getPosicao());
            msgAcao = casaQueParou.acao(jogadorAtual, this);
        }

        jogadorAtual.incrementarJogadas();

        return new String[]{msgDados, msgSoma, msgAcao};
    }

    public Jogador getJogadorAtual(){
        return jogadores.get(indiceJogadorAtual);
    }
    
    public List<Jogador> getPodio(){
        int n = jogadores.size();

        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n - i - 1; j++){

                Jogador jogadorAtual = jogadores.get(j);
                Jogador proximoJogador = jogadores.get(j + 1);

                if (jogadorAtual.getPosicao() < proximoJogador.getPosicao()){
                    Jogador temp = jogadorAtual;
                    jogadores.set(j, proximoJogador);
                    jogadores.set(j+1, temp);
                }
            }
        }

        return jogadores;
    }

    public List<Jogador> getJogadores(){
        return this.jogadores;
    }

    public String moverJogadorParaCasa(int numeroCasa) {
        Jogador jogadorAtual = getJogadorAtual();

        jogadorAtual.setPosicao(numeroCasa);
        System.out.println("DEBUG: Jogador " + jogadorAtual.getCor() + " movido para a casa " + jogadorAtual.getPosicao());

        String mensagemAcao = "";
        if (jogadorAtual.getPosicao() >= 40) {
            this.vencedor = jogadorAtual;
            mensagemAcao = "VENCEU";
        } else {
            Casa casaOndeParou = this.tabuleiro.getCasa(jogadorAtual.getPosicao());
            mensagemAcao = casaOndeParou.acao(jogadorAtual, this);
        }

        jogadorAtual.incrementarJogadas();

        return mensagemAcao;
    }

    public String transformarJogador(Jogador jogador) {
        int index = jogadores.indexOf(jogador);
        if (index == -1) return "Erro interno: jogador não encontrado.";

        int tipoAtual = 0;
        if (jogador instanceof JogadorNormal) tipoAtual = 1;
        if (jogador instanceof JogadorAzarado) tipoAtual = 2;
        if (jogador instanceof JogadorSortudo) tipoAtual = 3;


        int novoTipo;
        do {
            novoTipo = new Random().nextInt(3) + 1; // Sorteia 1, 2 ou 3
        } while (novoTipo == tipoAtual);

        Jogador novoJogador;
        String nomeNovoTipo = "";
        switch (novoTipo) {
            case 1:
                novoJogador = new JogadorNormal(jogador.getCor());
                nomeNovoTipo = "Normal";
                break;
            case 2:
                novoJogador = new JogadorAzarado(jogador.getCor());
                nomeNovoTipo = "Azarado";
                break;
            default: // case 3
                novoJogador = new JogadorSortudo(jogador.getCor());
                nomeNovoTipo = "Sortudo";
                break;
        }

        novoJogador.copiarEstado(jogador);

        jogadores.set(index, novoJogador);

        return "CASA SURPRESA! Você tirou uma carta e se transformou em um Jogador " + nomeNovoTipo + "!";
    }

    public void resetarPosicaoJogador(Jogador jogadorAlvo) {
        if (jogadorAlvo != null) {
            jogadorAlvo.setPosicao(0);
        }
    }
}
