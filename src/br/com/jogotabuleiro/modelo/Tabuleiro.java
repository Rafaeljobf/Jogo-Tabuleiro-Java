package br.com.jogotabuleiro.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private List<Casa> casasTabuleiro;
    private int qtdCasas = 41;
    private static Tabuleiro instanciaUnica;

    private Tabuleiro() {
        this.casasTabuleiro = new ArrayList<>(qtdCasas);
        inicializarTabuleiro();
    }

    public static synchronized Tabuleiro getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new Tabuleiro();
        }
        return instanciaUnica;
    }

    public void configurarTamanho(int novoTamanho) {
        if (novoTamanho < 20) novoTamanho = 20; // Tamanho mínimo de segurança
        this.qtdCasas = novoTamanho + 1;

        inicializarTabuleiro();
    }

    public void inicializarTabuleiro() {
        for (int i = 0; i < qtdCasas; i++) {
            casasTabuleiro.add(CasaFactory.criarCasa("Normal"));
        }


        casasTabuleiro.set(10, new CasaBloqueio());
        casasTabuleiro.set(25, new CasaBloqueio());
        casasTabuleiro.set(38, new CasaBloqueio());

        // Casa Surpresa
        casasTabuleiro.set(13, new CasaSurpresa());

        // Casas Sorte
        casasTabuleiro.set(5, new CasaSorte());
        casasTabuleiro.set(15, new CasaSorte());
        casasTabuleiro.set(30, new CasaSorte());

        // Casas Escolhe Jogador
        casasTabuleiro.set(17, new CasaEscolheJogador());
        casasTabuleiro.set(27, new CasaEscolheJogador());

        // Casas mágicas
        casasTabuleiro.set(20, new CasaMagica());
        casasTabuleiro.set(35, new CasaMagica());
    }

    public Casa getCasa(int posicao) {
        if (posicao >= 0 && posicao < qtdCasas) {
            return casasTabuleiro.get(posicao);
        }
        return casasTabuleiro.get(qtdCasas - 1);
    }

    public int getTamanho() {
        return this.casasTabuleiro.size();
    }
}

