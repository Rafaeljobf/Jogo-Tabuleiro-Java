package br.com.jogotabuleiro.visao;

import br.com.jogotabuleiro.modelo.Jogo;

import javax.swing.*;
import java.awt.*;

public class TelaConfiguracao extends JDialog {

    private Jogo jogo;
    private JComboBox<String> comboTipoJogador;
    private JComboBox<String> comboCorJogador;
    private DefaultListModel<String> listModel;
    private JButton btnAdicionar;
    private DefaultComboBoxModel<String> coresModel;

    public TelaConfiguracao(JFrame parent, Jogo jogo) {
        super(parent, "Configuração de Jogadores", true);
        this.jogo = jogo;

        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        String[] coresDisponiveis = {"AZUL", "VERMELHO", "VERDE", "AMARELO", "ROXO", "LARANJA"};
        coresModel = new DefaultComboBoxModel<>(coresDisponiveis);

        // Painel para adicionar jogadores
        JPanel painelAdicionar = new JPanel(new FlowLayout());
        comboCorJogador = new JComboBox<>(coresModel);
        comboTipoJogador = new JComboBox<>(new String[]{"Normal", "Azarado", "Sortudo"});
        btnAdicionar = new JButton("Adicionar");

        painelAdicionar.add(new JLabel("Cor:"));
        painelAdicionar.add(comboCorJogador);
        painelAdicionar.add(new JLabel("Tipo:"));
        painelAdicionar.add(comboTipoJogador);
        painelAdicionar.add(btnAdicionar);

        // Lista de jogadores adicionados
        listModel = new DefaultListModel<>();
        JList<String> listaJogadores = new JList<>(listModel);

        // Botão de Iniciar
        JButton btnIniciar = new JButton("Iniciar Jogo");

        add(painelAdicionar, BorderLayout.NORTH);
        add(new JScrollPane(listaJogadores), BorderLayout.CENTER);
        add(btnIniciar, BorderLayout.SOUTH);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> adicionarJogador());
        btnIniciar.addActionListener(e -> {
            if (jogo.getJogadores().size() < 2) {
                JOptionPane.showMessageDialog(this, "É necessário pelo menos 2 jogadores para iniciar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                dispose(); // Fecha a janela
            }
        });
    }

    private void adicionarJogador() {
        String cor = (String) comboCorJogador.getSelectedItem();
        if (cor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "A cor não pode ser vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tipo = comboTipoJogador.getSelectedIndex() + 1;
        jogo.adicionarJogador(cor, tipo);

        listModel.addElement(cor + " (" + comboTipoJogador.getSelectedItem() + ")");
        coresModel.removeElement(cor);

        if (jogo.getJogadores().size() >= 6) {
            btnAdicionar.setEnabled(false); // Desabilita o botão se chegar a 6 jogadores
        }
    }
}