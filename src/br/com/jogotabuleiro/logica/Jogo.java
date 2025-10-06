package br.com.jogotabuleiro.logica;

import br.com.jogotabuleiro.modelo.Dado;
import br.com.jogotabuleiro.modelo.Jogador;
import br.com.jogotabuleiro.modelo.JogadorNormal;
import br.com.jogotabuleiro.modelo.Tabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogo {

    private Tabuleiro tabuleiro;
    private Dado dado1, dado2;
    private List<Jogador> jogadores = new ArrayList<>();
    private int indiceJogadorAtual = 0;
    private Scanner scanner;

    public Jogo(){
        this.tabuleiro = new Tabuleiro();
        this.scanner = new Scanner(System.in);
        this.dado1 = new Dado();
        this.dado2 = new Dado();
    }

    public static void main(String[] args){
        Jogo novoJogo = new Jogo();
        novoJogo.iniciar();
    }

    public void iniciar(){
        configurar();

        while (!existeVencedor()){
            executarRodada();
        }

        mostrarResultados();
    }

    public void configurar(){
        // TODO: IMPLEMENTAR CRIAÇÃO DE JOGADORES DINÂMICA (USUÁRIO)
    }

    public boolean existeVencedor(){
        for (Jogador jogador: jogadores){
            if (jogador.getPosicao() == 40){
                return true;
            }
        }
        return false;
    }

    public boolean executarRodada(){
        Jogador jogadorAtual = jogadores.get(indiceJogadorAtual);
        System.out.println("----------------------------------");
        System.out.println("VEZ DE: Jogador "+ jogadorAtual.getCor());
        System.out.println("POSIÇÃO ATUAL: "+ jogadorAtual.getPosicao());
        System.out.println("----------------------------------");

        if (!jogadorAtual.podeJogar()){
            return false;
        }

        System.out.println("Pressione Enter para rolar os dados.");
        scanner.nextLine();

        int[] resultadoDados = jogadorAtual.rolarDados(dado1, dado2);
        int resultadoDado1 = resultadoDados[0];
        int resultadoDado2 = resultadoDados[1];

        System.out.println("DADO 1: "+ resultadoDado1);
        System.out.println("DADO 2: "+ resultadoDado2);
        int somaDados = resultadoDado1 + resultadoDado2;
        System.out.println("SOMA DOS DADOS: "+ somaDados);
        jogadorAtual.mover(somaDados);
        System.out.println("O jogador "+ jogadorAtual.getCor() +" agora está na posição "+ jogadorAtual.getPosicao());

        if (jogadorAtual.getPosicao() <= 40) {
            if (resultadoDado1 == resultadoDado2) {
                System.out.println("DADOS IGUAIS! ROLE OS DADOS NOVAMENTE!");
                System.out.println("Pressione Enter para rolar os dados.");
                scanner.nextLine();
                int[] resultadoDados2 = jogadorAtual.rolarDados(dado1, dado2);
                System.out.println("DADO 1: " + resultadoDado1);
                System.out.println("DADO 2: " + resultadoDado2);
                int somaDados2 = resultadoDado1 + resultadoDado2;
                System.out.println("SOMA DOS DADOS: " + somaDados2);
                jogadorAtual.mover(somaDados);
                System.out.println("O jogador " + jogadorAtual.getCor() + " agora está na posição " + jogadorAtual.getPosicao());
            } else {
                indiceJogadorAtual = (indiceJogadorAtual + 1) % jogadores.size();
            }
        }


        return true;
    }

    public void mostrarResultados(){

    }
}
