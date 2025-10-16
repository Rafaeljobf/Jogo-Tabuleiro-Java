package br.com.jogotabuleiro.modelo;

public abstract class Jogador {
    protected int posicao;
    protected String cor;
    protected int qtdJogadas;
    protected boolean bloqueado;

    public Jogador(String cor){
        this.cor = cor;
        this.posicao = 0;
        this.qtdJogadas = 0;
        this.bloqueado = false;
    }

    public abstract int[] rolarDados(Dado dado1, Dado dado2);

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
        if (bloqueado) return;

        posicao += resultadoDados;
        if (posicao > 40) {
            posicao = 40;
        }
    }

    public void bloquear(){
        this.bloqueado = true;
    }

    public void desbloquear(){
        this.bloqueado = false;
    }

    public boolean podeJogar(){
        return !bloqueado;
    }

}
