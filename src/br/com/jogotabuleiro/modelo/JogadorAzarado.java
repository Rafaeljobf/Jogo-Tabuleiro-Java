package br.com.jogotabuleiro.modelo;

public class JogadorAzarado extends Jogador {

    public JogadorAzarado(String cor){
        super(cor);
    }

    @Override
    public int[] rolarDados(Dado dado1, Dado dado2) {
        int resultadoDado1;
        int resultadoDado2;

        do {
            resultadoDado1 = dado1.rolar();
            resultadoDado2 = dado2.rolar();
            int soma = resultadoDado1 + resultadoDado2;
        } while (resultadoDado1 + resultadoDado2 > 6);

        return new int[]{resultadoDado1, resultadoDado2};
    }
}
