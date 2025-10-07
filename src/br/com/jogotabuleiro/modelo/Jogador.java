package br.com.jogotabuleiro.modelo;

public abstract class Jogador {
    private int posicao;
    private String cor;
    private int qtdJogadas;

    public Jogador(String cor){
        this.cor = cor;
        this.posicao = 0;
    }

    public int getPosicao() {
        return posicao;
    }

    public String getCor() {
        return cor;
    }

    public int getQtdJogadas() {
        return qtdJogadas;
    }

    public void incrementarJogadas(){
        this.qtdJogadas++;
    }

    public void mover(int resultadoDados){
        posicao += resultadoDados;
        if (posicao > 40) {
            posicao = 40;
        }
    }

    public boolean podeJogar(){
        return true; //TODO: podejogar
    }

    public abstract int[] rolarDados(Dado dado1, Dado dado2);
}
