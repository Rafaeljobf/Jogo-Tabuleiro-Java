package br.com.jogotabuleiro.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private List<Casa> casasTabuleiro;
    private final int QTD_CASAS = 41;

    public Tabuleiro() {
        this.casasTabuleiro = new ArrayList<>(QTD_CASAS);
        inicializarTabuleiro();
    }

    public void inicializarTabuleiro() {
        for (int i = 0; i < QTD_CASAS; i++) {
            casasTabuleiro.add(new CasaNormal());
        }

        // Substitui todas as casas normais pelas casas especiais nas posições especificadas no trabalho

        // Casa Bloqueio
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
        if (posicao >= 0 && posicao < QTD_CASAS) {
            return casasTabuleiro.get(posicao);
        }
        return casasTabuleiro.get(QTD_CASAS - 1);
    }

    public int getTamanho() {
        return this.casasTabuleiro.size();
    }
}

