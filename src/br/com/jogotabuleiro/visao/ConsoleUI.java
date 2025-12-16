package br.com.jogotabuleiro.visao;

import br.com.jogotabuleiro.modelo.Jogo;
import br.com.jogotabuleiro.modelo.Jogador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements InterfaceUsuario {

    private Jogo jogo;
    private Scanner scanner;

    public ConsoleUI(Jogo jogo) {
        this.jogo = jogo;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void iniciar() {
        while (!configurar()) {
            // Loop até configuração ser válida
        }

        while (!jogo.existeVencedor()) {
            executarRodada();
        }

        mostrarResultados();
    }

    public boolean configurar() {
        int qtdJogadores = lerEntradaInteira("Quantidade de jogadores: (1 a 6)", 1, 6);

        for (int i = 0; i < qtdJogadores; i++) {
            exibirMensagem("Digite a cor do jogador " + (i + 1) + ":");
            String corJogador = scanner.nextLine();

            exibirMensagem("Escolha o tipo do jogador " + (i + 1) + ":");
            exibirMensagem("1) Jogador Normal");
            exibirMensagem("2) Jogador Azarado");
            exibirMensagem("3) Jogador Sortudo");

            int opcaoTipo = lerEntradaInteira("Opção:", 1, 3);

            String tipoString = "";
            switch(opcaoTipo) {
                case 1:
                    tipoString = "Normal";
                    break;
                case 2:
                    tipoString = "Azarado";
                    break;
                case 3:
                    tipoString = "Sortudo";
                    break;
            }

            jogo.adicionarJogador(tipoString, corJogador);
            exibirMensagem("");
        }
        return true;
    }

    public boolean executarRodada() {
        desenharTabuleiro();
        Jogador jogador = jogo.getJogadorAtual();
        exibirCabecalhoJogador(jogador);

        if (!verificarSePodeJogar(jogador)) {
            return false;
        }

        boolean jogarNovamente;
        int rolagensNesseTurno = 0;

        do {
            rolagensNesseTurno++;
            jogarNovamente = realizarTurno(jogador, rolagensNesseTurno);
        } while (jogarNovamente && jogador.getPosicao() < 40);

        verificarVitoria(jogador);

        if (!jogo.existeVencedor()) {
            jogo.passarVez();
        }

        return true;
    }

    private void exibirCabecalhoJogador(Jogador jogador) {
        exibirMensagem("----------------------------------");
        exibirMensagem("VEZ DE: Jogador " + jogador.getCor());
        exibirMensagem("POSIÇÃO ATUAL: " + jogador.getPosicao());
        exibirMensagem("----------------------------------");
    }

    private boolean verificarSePodeJogar(Jogador jogador) {
        if (!jogador.podeJogar()) {
            exibirMensagem("Jogador " + jogador.getCor() + " está bloqueado e passa a vez.");
            jogador.desbloquear();
            jogo.passarVez();
            return false;
        }
        return true;
    }

    private boolean realizarTurno(Jogador jogador, int rolagensNesseTurno) {
        exibirMensagem("Pressione Enter para rolar os dados.");
        scanner.nextLine();

        String[] resultadoTurno = jogo.executarTurno();
        return processarDadosDoTurno(resultadoTurno, jogador, rolagensNesseTurno);
    }

    private boolean processarDadosDoTurno(String[] resultadoTurno, Jogador jogador, int rolagensNesseTurno) {
        String[] dados = resultadoTurno[0].split(",");
        int dado1 = Integer.parseInt(dados[0]);
        int dado2 = Integer.parseInt(dados[1]);
        String msgAcao = resultadoTurno[2];

        exibirMensagem("DADO 1: " + dado1);
        exibirMensagem("DADO 2: " + dado2);
        exibirMensagem("SOMA: " + resultadoTurno[1]);
        exibirMensagem("O jogador " + jogador.getCor() + " agora está na posição " + jogador.getPosicao());

        tratarAcaoEspecial(msgAcao);
        exibirMensagem("");

        boolean dadosIguais = (dado1 == dado2);
        if (dadosIguais) {
            tratarDadosIguais(rolagensNesseTurno, jogador);
        }

        return dadosIguais;
    }

    private void tratarAcaoEspecial(String msgAcao) {
        if ("ACTION:CHOOSE_PLAYER_TO_RESET".equals(msgAcao)) {
            consoleEscolherJogadorParaResetar();
        } else if (msgAcao != null && !msgAcao.isEmpty() && !msgAcao.equals("VENCEU")) {
            exibirMensagem("AÇÃO: " + msgAcao);
        }
    }

    private void tratarDadosIguais(int rolagensNesseTurno, Jogador jogador) {
        if (rolagensNesseTurno == 2) {
            exibirMensagem("DADOS IGUAIS PELA SEGUNDA VEZ! Fim da sua rodada.");
        } else if (jogador.getPosicao() < 40) {
            exibirMensagem("DADOS IGUAIS! Você pode rolar os dados mais uma vez.");
        }
    }

    private void verificarVitoria(Jogador jogador) {
        if (jogador.getPosicao() == 40) {
            exibirMensagem("O jogador " + jogador.getCor() + " atingiu a casa 40 e é o GRANDE VENCEDOR!");
            exibirMensagem("PARABÉNS!");
            exibirMensagem("");
        }
    }

    public void mostrarResultados() {
        exibirMensagem("--- FIM DE JOGO ---");
        exibirMensagem("Pressione Enter para mostrar os resultados.");
        scanner.nextLine();

        List<Jogador> podio = jogo.getPodio();
        exibirMensagem("\n--- PÓDIO FINAL ---");

        for (int i = 0; i < podio.size(); i++) {
            Jogador j = podio.get(i);
            exibirMensagem("--------------------------------");
            exibirMensagem((i + 1) + "° LUGAR: " + j.getCor());
            exibirMensagem("POSIÇÃO FINAL: " + j.getPosicao());
            exibirMensagem("JOGADAS: " + j.getQtdJogadas());
            exibirMensagem("--------------------------------");
        }
    }

    private void consoleEscolherJogadorParaResetar() {
        Jogador jogadorAtual = jogo.getJogadorAtual();
        List<Jogador> alvos = new ArrayList<>();

        for (Jogador j : jogo.getJogadores()) {
            if (j != jogadorAtual) alvos.add(j);
        }

        if (alvos.isEmpty()) {
            exibirMensagem("Não há outros jogadores para escolher.");
            return;
        }

        exibirMensagem("Escolha um jogador para enviar ao início:");
        for (int i = 0; i < alvos.size(); i++) {
            exibirMensagem((i + 1) + ") " + alvos.get(i).getCor());
        }

        int escolha = lerEntradaInteira("Opção:", 1, alvos.size());
        Jogador alvo = alvos.get(escolha - 1);

        jogo.resetarPosicaoJogador(alvo);
        exibirMensagem("O jogador " + alvo.getCor() + " foi enviado de volta para o início!");
    }

    private int lerEntradaInteira(String mensagem, int min, int max) {
        while (true) {
            exibirMensagem(mensagem);
            try {
                String input = scanner.nextLine();
                int valor = Integer.parseInt(input);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                exibirMensagem("Valor inválido! Digite entre " + min + " e " + max + ".");
            } catch (NumberFormatException e) {
                exibirMensagem("Entrada inválida. Por favor, digite um número.");
            }
        }
    }

    private void desenharTabuleiro() {
        List<Jogador> todosJogadores = jogo.getJogadores();
        System.out.println("-------- TABULEIRO --------");

        for (int linha = 0; linha < 4; linha++) {
            StringBuilder linhaDeCasas = new StringBuilder();
            StringBuilder linhaDeJogadores = new StringBuilder();

            for (int coluna = 0; coluna < 10; coluna++) {
                int numeroDaCasa = linha * 10 + coluna + 1;
                linhaDeCasas.append(String.format("[%02d]", numeroDaCasa));

                String pino = "";
                for (Jogador j : todosJogadores) {
                    if (j.getPosicao() == numeroDaCasa) {
                        pino += j.getCor().charAt(0);
                    }
                }
                linhaDeJogadores.append(String.format(" %-3s", pino));
            }
            System.out.println(linhaDeCasas);
            System.out.println(linhaDeJogadores);
            System.out.println();
        }
    }

    // debugMode removido ou simplificado para não inflar o exemplo,
    // mas se precisar dele, use a mesma lógica do executarRodada()
    public void debugMode() {
        // Implementação simplificada reutilizando lógica se necessário
    }

    @Override
    public void exibirMensagem(String mensagem) {
        System.out.println(">> " + mensagem);
    }

    @Override
    public void atualizarTela() {
    }
}