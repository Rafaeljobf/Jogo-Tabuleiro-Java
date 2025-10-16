package br.com.jogotabuleiro;

import br.com.jogotabuleiro.modelo.Jogo;
import br.com.jogotabuleiro.visao.ConsoleUI;
import br.com.jogotabuleiro.visao.InterfaceUsuario;


import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner teclado = new Scanner(System.in);
        Jogo novoJogo = new Jogo();
        InterfaceUsuario ui = new ConsoleUI(novoJogo);

        System.out.println("BEM VINDO. SELECIONE O MODO QUE DESEJA UTILIZAR: ");
        System.out.println("1) Jogo Normal");
        System.out.println("2) Modo DEBUG");
        String modo = teclado.nextLine();

        switch (modo){
            case "1":
                ui.iniciar();
                break;
            case "2":
                ui.debugMode();
                break;
        }
    }
}
