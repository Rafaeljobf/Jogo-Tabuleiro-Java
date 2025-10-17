package br.com.jogotabuleiro.visao;

import br.com.jogotabuleiro.modelo.Jogo;
import br.com.jogotabuleiro.modelo.Jogador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements InterfaceUsuario{

    private Jogo jogo;
    private Scanner scanner;

    public ConsoleUI(Jogo jogo){
        this.jogo = jogo;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void iniciar() {
        while (!configurar()) {

        }

        while (!jogo.existeVencedor()){
            executarRodada();
        }

        mostrarResultados();
    }

    public boolean configurar(){
        exibirMensagem("Quantidade de jogadores: (1 a 6)");
        String qtdInput = scanner.nextLine();
        int qtdJogadores = Integer.parseInt(qtdInput);

        if (qtdJogadores < 1 || qtdJogadores > 6){
            exibirMensagem("A quantidade de jogadores não é válida!");
            return false;
        }

        for(int i = 0; i < qtdJogadores; i++) {
            exibirMensagem("Digite a cor do jogador: " + (i + 1));
            String corJogador = scanner.nextLine();

            exibirMensagem("Escolha o tipo do jogador " + (i + 1) + ":");
            exibirMensagem("1) Jogador Normal");
            exibirMensagem("2) Jogador Azarado");
            exibirMensagem("3) Jogador Sortudo");
            String tipoInput = scanner.nextLine();
            int tipoJogador = Integer.parseInt(tipoInput);

            jogo.adicionarJogador(corJogador, tipoJogador);
            exibirMensagem("");
        }
        return true;
    }

    public boolean executarRodada(){
        desenharTabuleiro();

        Jogador jogador = jogo.getJogadorAtual();
        exibirMensagem("----------------------------------");
        exibirMensagem("VEZ DE: Jogador "+ jogador.getCor());
        exibirMensagem("POSIÇÃO ATUAL: "+ jogador.getPosicao());
        exibirMensagem("----------------------------------");

        if (!jogador.podeJogar()){
            exibirMensagem("Jogador " + jogador.getCor() + " está bloqueado e passa a vez.");
            jogador.desbloquear();
            jogo.passarVez();
            return false;
        }

        boolean dadosIguais;
        int rolagensNesseTurno = 0;
        do {
            exibirMensagem("Pressione Enter para rolar os dados.");
            scanner.nextLine();
            rolagensNesseTurno++;

            String[] resultadoTurno = jogo.executarTurno();
            String[] dados = resultadoTurno[0].split(",");
            int resultadoDado1 = Integer.parseInt(dados[0]);
            int resultadoDado2 = Integer.parseInt(dados[1]);
            String somaDadosStr = resultadoTurno[1];
            String msgAcao = resultadoTurno[2];

            exibirMensagem("DADO 1: " + resultadoDado1);
            exibirMensagem("DADO 2: " + resultadoDado2);
            exibirMensagem("SOMA DOS DADOS: " + somaDadosStr);
            exibirMensagem("O jogador " + jogador.getCor() + " agora está na posição " + jogador.getPosicao());

            // Processa a mensagem de ação da casa
            if ("ACTION:CHOOSE_PLAYER_TO_RESET".equals(msgAcao)) {
                consoleEscolherJogadorParaResetar();
            } else if (msgAcao != null && !msgAcao.isEmpty() && !msgAcao.equals("VENCEU")) {
                exibirMensagem("AÇÃO: " + msgAcao);
            }

            exibirMensagem("");
            dadosIguais = (resultadoDado1 == resultadoDado2);

            if (dadosIguais){
                if (rolagensNesseTurno == 2){
                    exibirMensagem("DADOS IGUAIS PELA SEGUNDA VEZ! Fim da sua rodada.");
                } else if (jogador.getPosicao() < 40){
                    exibirMensagem("DADOS IGUAIS! Você pode rolar os dados mais uma vez.");
                }
            }
        } while (dadosIguais && jogador.getPosicao() < 40);

        if (jogador.getPosicao() == 40){
            exibirMensagem("O jogador "+ jogador.getCor() +" atingiu a casa 40 e é o GRANDE VENCEDOR!");
            exibirMensagem("PARABÉNS!");
            exibirMensagem("");
        }

        if (!jogo.existeVencedor()){
            jogo.passarVez();
        }

        return true;
    }

    public void mostrarResultados(){
        exibirMensagem("--- FIM DE JOGO ---");
        exibirMensagem("Calculando pódio...");
        exibirMensagem("");
        exibirMensagem("Pressione Enter para mostrar os resultados.");
        scanner.nextLine();

        List<Jogador> podio = jogo.getPodio();

        exibirMensagem("");
        exibirMensagem("--- PÓDIO FINAL ---");
        exibirMensagem("");
        for (int i = 0; i < podio.size(); i++){
            Jogador jogador = podio.get(i);
            exibirMensagem("--------------------------------");
            exibirMensagem("JOGADOR "+ jogador.getCor());
            exibirMensagem((i+1)+ "° LUGAR");
            exibirMensagem("POSIÇÃO FINAL: "+ jogador.getPosicao());
            exibirMensagem("QUANTIDADE DE JOGADAS: "+ jogador.getQtdJogadas());
            exibirMensagem("--------------------------------");
        }
    }

    private void desenharTabuleiro(){
        List<Jogador> todosJogadores = jogo.getJogadores();

        System.out.println("-------- TABULEIRO --------");
        for (int linha = 0; linha < 4; linha++){
            StringBuilder linhaDeCasas = new StringBuilder();
            StringBuilder linhaDeJogadores = new StringBuilder();

            for (int coluna = 0; coluna < 10; coluna++){
                int numeroDaCasa = linha * 10 + coluna + 1;

                linhaDeCasas.append(String.format("[%02d]", numeroDaCasa));

                String jogadoresNaCasa = "";
                for (Jogador j : todosJogadores){
                    if (j.getPosicao() == numeroDaCasa){
                        jogadoresNaCasa += j.getCor().charAt(0);
                    }
                }
                linhaDeJogadores.append(String.format(" %-3s", jogadoresNaCasa));
            }
            System.out.println(linhaDeCasas);
            System.out.println(linhaDeJogadores);
            System.out.println();
        }
    }

    public void debugMode(){
        while (!configurar()) {

        }
        exibirMensagem("--- MODO DEBUG ATIVADO ---");

        while (!jogo.existeVencedor()) {
            desenharTabuleiro();

            Jogador jogador = jogo.getJogadorAtual();
            exibirMensagem("----------------------------------");
            exibirMensagem("VEZ DE: Jogador " + jogador.getCor());
            exibirMensagem("POSIÇÃO ATUAL: " + jogador.getPosicao());
            exibirMensagem("----------------------------------");

            if (!jogador.podeJogar()) {
                exibirMensagem("Jogador " + jogador.getCor() + " está bloqueado e passa a vez.");
                jogador.desbloquear();
                jogo.passarVez();
                continue;
            }

            int casaDesejada = -1;
            while (casaDesejada < 1 || casaDesejada > 40) {
                exibirMensagem("Digite o número da casa de destino (1 a 40):");
                try {
                    String input = scanner.nextLine();
                    casaDesejada = Integer.parseInt(input);
                    if (casaDesejada < 1 || casaDesejada > 40) {
                        exibirMensagem("Número inválido. Por favor, digite um valor entre 1 e 40.");
                    }
                } catch (NumberFormatException e) {
                    exibirMensagem("Entrada inválida. Por favor, digite um número.");
                }
            }

            String msgAcao = jogo.moverJogadorParaCasa(casaDesejada);
            if ("ACTION:CHOOSE_PLAYER_TO_RESET".equals(msgAcao)) {
                consoleEscolherJogadorParaResetar();
            } else if (msgAcao != null && !msgAcao.isEmpty() && !msgAcao.equals("VENCEU")) {
                exibirMensagem("AÇÃO: " + msgAcao);
            }

            if (!jogo.existeVencedor()) {
                jogo.passarVez();
                exibirMensagem("Pressione Enter para continuar para o próximo jogador");
                scanner.nextLine();
            }
        }
        mostrarResultados();
    }

    private void consoleEscolherJogadorParaResetar() {
        Jogador jogadorAtual = jogo.getJogadorAtual();
        List<Jogador> outrosJogadores = new ArrayList<>();

        for (Jogador j : jogo.getJogadores()) {
            if (j != jogadorAtual) {
                outrosJogadores.add(j);
            }
        }

        if (outrosJogadores.isEmpty()) {
            exibirMensagem("Não há outros jogadores para escolher.");
            return;
        }

        exibirMensagem("Escolha um jogador para enviar ao início:");
        for (int i = 0; i < outrosJogadores.size(); i++) {
            exibirMensagem((i + 1) + ") " + outrosJogadores.get(i).getCor());
        }

        int escolha = -1;
        while (escolha < 1 || escolha > outrosJogadores.size()) {
            try {
                String input = scanner.nextLine();
                escolha = Integer.parseInt(input);
                if (escolha < 1 || escolha > outrosJogadores.size()) {
                    exibirMensagem("Escolha inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                exibirMensagem("Entrada inválida. Digite um número.");
            }
        }

        Jogador jogadorAlvo = outrosJogadores.get(escolha - 1);
        jogo.resetarPosicaoJogador(jogadorAlvo);
        exibirMensagem("O jogador " + jogadorAlvo.getCor() + " foi enviado de volta para o início!");
    }

    // estilização da mensagem
    @Override
    public void exibirMensagem(String mensagem){
        System.out.println(">> " + mensagem);
    }

    // implementado pela interface
    @Override
    public void atualizarTela(){

    }
}
