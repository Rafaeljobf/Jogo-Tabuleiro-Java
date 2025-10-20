package br.com.jogotabuleiro.visao;

import br.com.jogotabuleiro.modelo.*;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraficaUI extends JFrame {

    private Jogo jogo;

    // Componentes da UI
    private JTextArea areaDeLog;
    private JLabel labelJogadorAtual;
    private JButton botaoRolarDados;
    private JPanel painelTabuleiro;
    private List<JLabel> casasLabels;

    public GraficaUI(Jogo jogo) {
        this.jogo = jogo;
        this.casasLabels = new ArrayList<>();

        setTitle("Jogo de Tabuleiro POO");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        criarPainelTabuleiro();
        criarPainelControles();

        atualizarTela();
    }

    private void criarPainelTabuleiro() {
        painelTabuleiro = new JPanel(new GridLayout(4, 10, 5, 5));
        painelTabuleiro.setBorder(BorderFactory.createTitledBorder("Tabuleiro"));

        for (int i = 1; i <= 40; i++) {
            JLabel casaLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            casaLabel.setOpaque(true);
            casaLabel.setBorder(new LineBorder(Color.BLACK));
            casaLabel.setVerticalAlignment(SwingConstants.TOP);
            casasLabels.add(casaLabel);
            painelTabuleiro.add(casaLabel);
        }
        add(painelTabuleiro, BorderLayout.CENTER);
    }

    private void criarPainelControles() {
        areaDeLog = new JTextArea();
        areaDeLog.setEditable(false);
        areaDeLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(areaDeLog);
        scrollPane.setPreferredSize(new Dimension(300, 0));
        add(scrollPane, BorderLayout.EAST);
        adicionarLog("Bem-vindo! Configure os jogadores para começar.");

        JPanel painelSul = new JPanel(new BorderLayout(10, 10));
        labelJogadorAtual = new JLabel(" ", SwingConstants.CENTER);
        labelJogadorAtual.setFont(new Font("Arial", Font.BOLD, 16));

        botaoRolarDados = new JButton("Rolar os Dados");
        botaoRolarDados.setFont(new Font("Arial", Font.BOLD, 18));
        botaoRolarDados.setEnabled(false); // Desabilitado até o jogo começar

        painelSul.add(labelJogadorAtual, BorderLayout.CENTER);
        painelSul.add(botaoRolarDados, BorderLayout.SOUTH);
        add(painelSul, BorderLayout.SOUTH);

        botaoRolarDados.addActionListener(e -> executarTurnoGrafico());
    }

    private void executarTurnoGrafico() {
        if (jogo.existeVencedor()) return;

        Jogador jogadorAtual = jogo.getJogadorAtual();

        if (!jogadorAtual.podeJogar()) {
            adicionarLog("--- Vez do Jogador " + jogadorAtual.getCor() + " ---");
            adicionarLog("Jogador BLOQUEADO! Perde a vez.");
            jogadorAtual.desbloquear();
            jogo.passarVez();
            atualizarTela();
            return;
        }

        adicionarLog("--- Vez do Jogador " + jogadorAtual.getCor() + " ---");

        String[] resultadoTurno = jogo.executarTurno();

        String[] dadosArray = resultadoTurno[0].split(","); // Separa "d1,d2"
        int d1 = Integer.parseInt(dadosArray[0]);
        int d2 = Integer.parseInt(dadosArray[1]);
        String soma = resultadoTurno[1];
        String mensagemAcao = resultadoTurno[2];

        adicionarLog("Dados: [" + d1 + ", " + d2 + "] | Soma: " + soma);
        adicionarLog("O jogador " + jogadorAtual.getCor() + " moveu para a casa " + jogadorAtual.getPosicao());

        if ("ACTION:CHOOSE_PLAYER_TO_RESET".equals(mensagemAcao)) {
            adicionarLog("CASA ESPECIAL! Escolha um jogador para voltar ao início.");
            escolherJogadorParaResetar();
        } else if (mensagemAcao != null && !mensagemAcao.isEmpty() && !mensagemAcao.equals("VENCEU")) {
            adicionarLog("AÇÃO: " + mensagemAcao);
        }

        if (d1 == d2) {
            adicionarLog("DADOS IGUAIS! Jogue novamente!");
        } else {
            jogo.passarVez();
        }

        if (jogo.existeVencedor() || "VENCEU".equals(mensagemAcao)) {
            Jogador vencedor = jogo.getJogadorAtual();
            adicionarLog("FIM DE JOGO! O vencedor é " + vencedor.getCor() + "!");
            JOptionPane.showMessageDialog(this, "O jogador " + vencedor.getCor() + " é o vencedor!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
            botaoRolarDados.setEnabled(false);
        }

        atualizarTela();
    }

    private void atualizarTela() {
        if (jogo.getJogadores().isEmpty()) return;

        Jogador jogadorAtual = jogo.getJogadorAtual();
        labelJogadorAtual.setText("Vez de: " + jogadorAtual.getCor() + " | Posição: " + jogadorAtual.getPosicao());

        // Agrupa jogadores por posição para facilitar a renderização
        Map<Integer, List<Jogador>> jogadoresPorCasa = new HashMap<>();
        for (Jogador p : jogo.getJogadores()) {
            if (p.getPosicao() > 0) {
                // computeIfAbsent garante que a lista seja criada se ainda não existir
                jogadoresPorCasa.computeIfAbsent(p.getPosicao(), k -> new ArrayList<>()).add(p);
            }
        }

        // Atualiza cada label do tabuleiro
        for (int i = 0; i < casasLabels.size(); i++) {
            int numeroCasa = i + 1;
            JLabel casaLabel = casasLabels.get(i);
            // Inicia a construção do conteúdo HTML da casa
            StringBuilder htmlContent = new StringBuilder("<html><div style='text-align:center;'>" + numeroCasa + "<br/>");

            if (jogadoresPorCasa.containsKey(numeroCasa)) {
                for (Jogador p : jogadoresPorCasa.get(numeroCasa)) {
                    String bgColor = getColorHexFromString(p.getCor());
                    String fontColor = getFontColorForBackground(bgColor);

                    // Adiciona um "token" colorido com a inicial do jogador
                    htmlContent.append("<span style='background-color:")
                            .append(bgColor)
                            .append("; color:")
                            .append(fontColor)
                            .append("; font-family:sans-serif; font-weight:bold; border-radius:4px; padding: 1px 3px; border:1px solid black;'>")
                            .append(p.getCor().charAt(0))
                            .append("</span> ");
                }
            }

            htmlContent.append("</div></html>");
            casaLabel.setText(htmlContent.toString());
        }
    }

    private void adicionarLog(String mensagem) {
        areaDeLog.append(">> " + mensagem + "\n");
        areaDeLog.setCaretPosition(areaDeLog.getDocument().getLength());
    }

    private void escolherJogadorParaResetar() {
        Jogador jogadorAtual = jogo.getJogadorAtual();
        List<Jogador> outrosJogadores = new ArrayList<>();

        for (Jogador j : jogo.getJogadores()) {
            if (j != jogadorAtual) {
                outrosJogadores.add(j);
            }
        }

        if (outrosJogadores.isEmpty()) {
            adicionarLog("Não há outros jogadores para escolher.");
            return;
        }

        String[] opcoes = new String[outrosJogadores.size()];
        for (int i = 0; i < outrosJogadores.size(); i++) {
            opcoes[i] = outrosJogadores.get(i).getCor();
        }

        String corEscolhida = (String) JOptionPane.showInputDialog(
                this,
                "Escolha um jogador para enviar ao início:",
                "Casa Escolhe Jogador",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (corEscolhida != null) {
            for (Jogador alvo : outrosJogadores) {
                if (alvo.getCor().equals(corEscolhida)) {
                    jogo.resetarPosicaoJogador(alvo);
                    adicionarLog("O jogador " + alvo.getCor() + " foi enviado para o início!");
                    atualizarTela(); // Atualiza a UI para mostrar a mudança
                    break;
                }
            }
        }
    }

    public void iniciarConfiguracao() {
        TelaConfiguracao configDialog = new TelaConfiguracao(this, jogo);

        configDialog.setLocationRelativeTo(null);
        configDialog.setVisible(true);

        if (jogo.getJogadores().size() >= 2) {
            adicionarLog("Jogo configurado com " + jogo.getJogadores().size() + " jogadores.");
            botaoRolarDados.setEnabled(true);
            atualizarTela(); // Atualiza a tela para mostrar o primeiro jogador
        } else {
            adicionarLog("A configuração foi cancelada ou não há jogadores suficientes (mínimo 2).");
            JOptionPane.showMessageDialog(this,
                    "O jogo não pode iniciar sem pelo menos 2 jogadores.\nO programa será encerrado.",
                    "Configuração Incompleta",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private String getColorHexFromString(String cor) {
        switch (cor.toUpperCase()) {
            case "VERMELHO": return "#DC143C"; // Vermelho mais escuro
            case "AZUL": return "#1E90FF";     // Azul mais vivo
            case "VERDE": return "#228B22";    // Verde floresta
            case "AMARELO": return "#FFD700";  // Amarelo dourado
            case "ROXO": return "#8A2BE2";      // Roxo azulado
            case "LARANJA": return "#FF8C00"; // Laranja escuro
            default: return "#000000";         // Preto como padrão
        }
    }

    private String getFontColorForBackground(String hexBgColor) {
        // Cores claras recebem fonte preta para melhor legibilidade
        if (hexBgColor.equals("#FFD700") || hexBgColor.equals("#FF8C00")) {
            return "black";
        }
        // Cores escuras recebem fonte branca
        return "white";
    }
}