package br.com.jogotabuleiro.modelo;
import java.util.Random;

public class Dado {
    private Random random;
    private int numeroDeLados;

    public Dado(){
        this.random = new Random();
        this.numeroDeLados = 6;
    }

    public int rolar(){
        return random.nextInt(numeroDeLados) + 1;
    }
}
