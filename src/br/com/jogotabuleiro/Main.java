// Em: br.com.jogotabuleiro.Main.java

package br.com.jogotabuleiro;

import br.com.jogotabuleiro.modelo.Jogo;
import br.com.jogotabuleiro.visao.ConsoleUI;
import br.com.jogotabuleiro.visao.GraficaUI;
import javax.swing.SwingUtilities;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner teclado = new Scanner(System.in);
        Jogo novoJogo = new Jogo();

        System.out.println("BEM VINDO AO JOGO DE TABULEIRO. SELECIONE O MODO QUE DESEJA UTILIZAR:");
        System.out.println("1) Jogo Normal (Console)");
        System.out.println("2) Modo DEBUG (Console)");
        System.out.println("3) Jogo Gráfico (GUI)");
        String modo = teclado.nextLine();

        switch (modo){
            case "1":
                ConsoleUI uiConsole = new ConsoleUI(novoJogo);
                uiConsole.iniciar();
                break;

            case "2":
                ConsoleUI uiDebug = new ConsoleUI(novoJogo);
                uiDebug.debugMode();
                break;

            case "3":
                System.out.println("Iniciando a interface gráfica...");

                GraficaUI janela = new GraficaUI(novoJogo);
                janela.setVisible(true);
                janela.iniciarConfiguracao();

                break;

            default:
                System.out.println("Opção inválida. O programa será encerrado.");
                break;
        }
    }
}