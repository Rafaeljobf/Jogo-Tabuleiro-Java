package br.com.jogotabuleiro.modelo;

import java.util.ArrayList;
import java.util.List;
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

    public int[] rolarDadosEMover(){
        Jogador jogadorAtual = getJogadorAtual();
        int[] resultadoDados = jogadorAtual.rolarDados(dado1, dado2);
        int soma = resultadoDados[0] + resultadoDados[1];
        jogadorAtual.mover(soma);

        if (jogadorAtual.getPosicao() == 40){
            this.vencedor = jogadorAtual;
        }

        Casa casaQueParou = this.tabuleiro.getCasa(jogadorAtual.getPosicao());
        casaQueParou.acao(jogadorAtual);

        jogadorAtual.incrementarJogadas();
        return resultadoDados;
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
}
