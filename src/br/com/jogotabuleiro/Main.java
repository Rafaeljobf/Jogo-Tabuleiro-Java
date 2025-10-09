package br.com.jogotabuleiro;

import br.com.jogotabuleiro.modelo.Jogo;
import br.com.jogotabuleiro.visao.ConsoleUI;
import br.com.jogotabuleiro.visao.InterfaceUsuario;

public class Main {
    public static void main(String[] args){
        Jogo novoJogo = new Jogo();

        InterfaceUsuario ui = new ConsoleUI(novoJogo);
        ui.iniciar();
    }
}
