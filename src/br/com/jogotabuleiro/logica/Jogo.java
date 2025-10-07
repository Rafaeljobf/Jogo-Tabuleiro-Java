package br.com.jogotabuleiro.logica;

import br.com.jogotabuleiro.modelo.*;

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
        System.out.println("BEM VINDO AO JOGO DE TABULEIRO EM JAVA!");
        while (!configurar()){

        }
        System.out.println("CONFIGURAÇÃO CONCLUÍDA, O JOGO VAI COMEÇAR!");

        while (!existeVencedor()){
            executarRodada();
        }

        mostrarResultados();
    }

    public boolean configurar(){
        System.out.println("Quantidade de jogadores: (1 a 6)");
        String qtdInput = scanner.nextLine();
        int qtdJogadores = Integer.parseInt(qtdInput);

        if (qtdJogadores < 1 || qtdJogadores > 6){
            System.out.println("A quantidade de jogadores não é válida!");
            return false;
        }

        for(int i = 0; i < qtdJogadores; i++) {
            System.out.println("Digite a cor do jogador: " + (i + 1));
            String corJogador = scanner.nextLine();

            System.out.println("Escolha o tipo do jogador " + (i + 1) + ":");
            System.out.println("1) Jogador Normal");
            System.out.println("2) Jogador Azarado");
            System.out.println("3) Jogador Sortudo");
            String tipoInput = scanner.nextLine();
            int tipoJogador = Integer.parseInt(tipoInput);

            switch (tipoJogador){
                case 1:
                    jogadores.add(new JogadorNormal(corJogador.toUpperCase()));
                    break;
                case 2:
                    jogadores.add(new JogadorAzarado(corJogador.toUpperCase()));
                    break;
                case 3:
                    jogadores.add(new JogadorSortudo(corJogador.toUpperCase()));
                    break;
                default:
                    System.out.println("Insira um número válido!");
                    return false;
            }
            System.out.println();
        }
        return true;
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

        boolean dadosIguais;
        int rolagensNesseTurno = 0;
        do {
            System.out.println("Pressione Enter para rolar os dados.");
            scanner.nextLine();
            rolagensNesseTurno++;

            int[] resultadoDados = jogadorAtual.rolarDados(dado1, dado2);
            int resultadoDado1 = resultadoDados[0];
            int resultadoDado2 = resultadoDados[1];

            System.out.println("DADO 1: "+ resultadoDado1);
            System.out.println("DADO 2: "+ resultadoDado2);
            int somaDados = resultadoDado1 + resultadoDado2;
            System.out.println("SOMA DOS DADOS: "+ somaDados);
            jogadorAtual.mover(somaDados);
            System.out.println("O jogador "+ jogadorAtual.getCor() +" agora está na posição "+ jogadorAtual.getPosicao());
            System.out.println();

            dadosIguais = (resultadoDado1 == resultadoDado2);

            if (dadosIguais){
                if (rolagensNesseTurno == 2){
                    System.out.println("DADOS IGUAIS PELA SEGUNDA VEZ! Fim da sua rodada.");
                } else if (jogadorAtual.getPosicao() < 40){
                    System.out.println("DADOS IGUAIS! Você pode rolas os dados mais uma vez.");
                }
            }
        } while (dadosIguais && jogadorAtual.getPosicao() < 40);

        if (jogadorAtual.getPosicao() == 40){
            System.out.println("O jogador "+ jogadorAtual.getCor() +" atingiu a casa 40 e é o GRANDE VENCEDOR!");
            System.out.println("PARABÉNS!");
            System.out.println();
        } else {
            indiceJogadorAtual = (indiceJogadorAtual + 1) % jogadores.size();
        }

        jogadorAtual.incrementarJogadas();
        return true;
    }

    public void mostrarResultados(){
        System.out.println("--- FIM DE JOGO ---");
        System.out.println("Calculando pódio...");
        System.out.println();
        System.out.println("Pressione Enter para mostrar os resultados.");
        scanner.nextLine();

        // Ordenar o vetor de jogadores do maior para o menor em relação a posição e colocá-los em formato de pódio

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
        System.out.println();
        System.out.println("--- PÓDIO FINAL ---");
        System.out.println();
        for (int i = 0; i < n; i++){
            Jogador jogador = jogadores.get(i);
            System.out.println("--------------------------------");
            System.out.println("JOGADOR "+ jogador.getCor());
            System.out.println((i+1)+ "° LUGAR");
            System.out.println("POSIÇÃO FINAL: "+ jogador.getPosicao());
            System.out.println("QUANTIDADE DE JOGADAS: "+ jogador.getQtdJogadas());
            System.out.println("--------------------------------");
        }
    }
}
