package br.com.jogotabuleiro.modelo;

public class JogadorAzarado extends Jogador {

    public JogadorAzarado(String cor){
        super(cor);
    }

    @Override
    public int[] rolarDados(Dado dado1, Dado dado2) {
        int resultadoDado1 = dado1.rolar();
        int resultadoDado2 = dado2.rolar();
        return new int[]{resultadoDado1, resultadoDado2};
    }
}
