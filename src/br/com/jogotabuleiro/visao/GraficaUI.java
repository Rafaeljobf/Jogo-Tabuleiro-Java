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

    private final Jogo jogo;
    // Componentes da UI
    private JTextArea areaDeLog;
    private JLabel labelJogadorAtual;
    private JButton botaoRolarDados;
    private JPanel painelTabuleiro;
    private final List<JLabel> casasLabels;

    public GraficaUI(Jogo jogo) {
        this.jogo = jogo;
        this.casasLabels = new ArrayList<>();
        configurarJanela();
        criarComponentes();
        atualizarTela();
    }

    private void configurarJanela() {
        setTitle("Jogo de Tabuleiro POO");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    private void criarComponentes() {
        criarPainelTabuleiro();
        criarPainelControles();
    }

    private void criarPainelTabuleiro() {
        painelTabuleiro = new JPanel(new GridLayout(4, 10, 5, 5));
        painelTabuleiro.setBorder(BorderFactory.createTitledBorder("Tabuleiro"));

        for (int i = 1; i <= 40; i++) {
            JLabel casaLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            estilizarCasa(casaLabel);
            casasLabels.add(casaLabel);
            painelTabuleiro.add(casaLabel);
        }
        add(painelTabuleiro, BorderLayout.CENTER);
    }

    private void estilizarCasa(JLabel label) {
        label.setOpaque(true);
        label.setBorder(new LineBorder(Color.BLACK));
        label.setVerticalAlignment(SwingConstants.TOP);
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
        botaoRolarDados.setEnabled(false);

        painelSul.add(labelJogadorAtual, BorderLayout.CENTER);
        painelSul.add(botaoRolarDados, BorderLayout.SOUTH);
        add(painelSul, BorderLayout.SOUTH);

        botaoRolarDados.addActionListener(e -> executarTurnoGrafico());
    }

    private void executarTurnoGrafico() {
        if (jogo.existeVencedor()) return;

        Jogador jogadorAtual = jogo.getJogadorAtual();
        adicionarLog("--- Vez do Jogador " + jogadorAtual.getCor() + " ---");

        if (verificarBloqueio(jogadorAtual)) return;

        processarTurno(jogadorAtual);
    }

    private boolean verificarBloqueio(Jogador jogador) {
        if (!jogador.podeJogar()) {
            adicionarLog("Jogador BLOQUEADO! Perde a vez.");
            jogador.desbloquear();
            jogo.passarVez();
            atualizarTela();
            return true;
        }
        return false;
    }

    private void processarTurno(Jogador jogador) {
        String[] resultado = jogo.executarTurno();
        exibirResultadoDados(resultado, jogador);

        String acao = resultado[2];
        tratarAcaoEspecial(acao);

        gerenciarFluxoTurno(resultado, jogador);
        atualizarTela();
    }

    private void exibirResultadoDados(String[] resultado, Jogador jogador) {
        String[] dados = resultado[0].split(",");
        adicionarLog("Dados: [" + dados[0] + ", " + dados[1] + "] | Soma: " + resultado[1]);
        adicionarLog("O jogador " + jogador.getCor() + " moveu para a casa " + jogador.getPosicao());
    }

    private void tratarAcaoEspecial(String acao) {
        if ("ACTION:CHOOSE_PLAYER_TO_RESET".equals(acao)) {
            adicionarLog("CASA ESPECIAL! Escolha um jogador para voltar ao início.");
            escolherJogadorParaResetar();
        } else if (acao != null && !acao.isEmpty() && !"VENCEU".equals(acao)) {
            adicionarLog("AÇÃO: " + acao);
        }
    }

    private void gerenciarFluxoTurno(String[] resultado, Jogador jogador) {
        String[] dados = resultado[0].split(",");
        boolean dadosIguais = dados[0].equals(dados[1]);
        String acao = resultado[2];

        if (dadosIguais) {
            adicionarLog("DADOS IGUAIS! Jogue novamente!");
        } else {
            jogo.passarVez();
        }

        if (jogo.existeVencedor() || "VENCEU".equals(acao)) {
            finalizarJogo(jogador);
        }
    }

    private void finalizarJogo(Jogador vencedor) {
        adicionarLog("FIM DE JOGO! O vencedor é " + vencedor.getCor() + "!");
        JOptionPane.showMessageDialog(this, "O jogador " + vencedor.getCor() + " é o vencedor!");
        botaoRolarDados.setEnabled(false);
    }

    private void atualizarTela() {
        if (jogo.getJogadores().isEmpty()) return;

        Jogador atual = jogo.getJogadorAtual();
        labelJogadorAtual.setText("Vez de: " + atual.getCor() + " | Posição: " + atual.getPosicao());

        Map<Integer, List<Jogador>> mapaJogadores = agruparJogadoresPorCasa();
        atualizarLabelsTabuleiro(mapaJogadores);
    }

    private Map<Integer, List<Jogador>> agruparJogadoresPorCasa() {
        Map<Integer, List<Jogador>> mapa = new HashMap<>();
        for (Jogador p : jogo.getJogadores()) {
            if (p.getPosicao() > 0) {
                mapa.computeIfAbsent(p.getPosicao(), k -> new ArrayList<>()).add(p);
            }
        }
        return mapa;
    }

    private void atualizarLabelsTabuleiro(Map<Integer, List<Jogador>> mapaJogadores) {
        for (int i = 0; i < casasLabels.size(); i++) {
            int numeroCasa = i + 1;
            String html = gerarHtmlCasa(numeroCasa, mapaJogadores.get(numeroCasa));
            casasLabels.get(i).setText(html);
        }
    }

    private String gerarHtmlCasa(int numero, List<Jogador> jogadores) {
        StringBuilder html = new StringBuilder("<html><div style='text-align:center;'>" + numero + "<br/>");

        if (jogadores != null) {
            for (Jogador p : jogadores) {
                html.append(gerarTokenJogador(p));
            }
        }
        return html.append("</div></html>").toString();
    }

    private String gerarTokenJogador(Jogador p) {
        String bg = getColorHexFromString(p.getCor());
        String fg = getFontColorForBackground(bg);
        return String.format(
                "<span style='background-color:%s; color:%s; font-family:sans-serif; font-weight:bold; " +
                        "border-radius:4px; padding: 1px 3px; border:1px solid black;'>%s</span> ",
                bg, fg, p.getCor().charAt(0)
        );
    }

    private void adicionarLog(String mensagem) {
        areaDeLog.append(">> " + mensagem + "\n");
        areaDeLog.setCaretPosition(areaDeLog.getDocument().getLength());
    }

    private void escolherJogadorParaResetar() {
        List<Jogador> alvos = obterAlvosParaReset();
        if (alvos.isEmpty()) {
            adicionarLog("Não há outros jogadores para escolher.");
            return;
        }

        String corEscolhida = mostrarDialogoEscolha(alvos);
        if (corEscolhida != null) {
            aplicarReset(alvos, corEscolhida);
        }
    }

    private List<Jogador> obterAlvosParaReset() {
        Jogador atual = jogo.getJogadorAtual();
        List<Jogador> alvos = new ArrayList<>();
        for (Jogador j : jogo.getJogadores()) {
            if (j != atual) alvos.add(j);
        }
        return alvos;
    }

    private String mostrarDialogoEscolha(List<Jogador> alvos) {
        String[] opcoes = alvos.stream().map(Jogador::getCor).toArray(String[]::new);
        return (String) JOptionPane.showInputDialog(
                this, "Escolha um jogador para enviar ao início:",
                "Casa Escolhe Jogador", JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]
        );
    }

    private void aplicarReset(List<Jogador> alvos, String corEscolhida) {
        for (Jogador alvo : alvos) {
            if (alvo.getCor().equals(corEscolhida)) {
                jogo.resetarPosicaoJogador(alvo);
                adicionarLog("O jogador " + alvo.getCor() + " foi enviado para o início!");
                atualizarTela();
                break;
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
            atualizarTela();
        } else {
            tratarConfiguracaoInvalida();
        }
    }

    private void tratarConfiguracaoInvalida() {
        adicionarLog("Configuração cancelada.");
        JOptionPane.showMessageDialog(this,
                "O jogo precisa de no mínimo 2 jogadores.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        dispose();
    }

    private String getColorHexFromString(String cor) {
        switch (cor.toUpperCase()) {
            case "VERMELHO": return "#DC143C";
            case "AZUL":     return "#1E90FF";
            case "VERDE":    return "#228B22";
            case "AMARELO":  return "#FFD700";
            case "ROXO":     return "#8A2BE2";
            case "LARANJA":  return "#FF8C00";
            default:         return "#000000";
        }
    }

    private String getFontColorForBackground(String hex) {
        return (hex.equals("#FFD700") || hex.equals("#FF8C00")) ? "black" : "white";
    }
}