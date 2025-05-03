import java.awt.*;
import javax.swing.*;

public class TelaEstacionamento extends JFrame {
    private Estacionamento estacionamento;
    private JTextField placaField, modeloField, cidadeField, estadoField;
    private JComboBox<String> tipoCombo;
    private JTextArea historicoArea;
    private JLabel vagasLabel;

    public TelaEstacionamento() {
        estacionamento = new Estacionamento();
        setTitle("Monitoramento de Entrada e Saída de Veículos");
        setSize(650, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 30, 40));

        JPanel painelDados = new JPanel(new GridBagLayout());
        painelDados.setBackground(new Color(45, 45, 60));
        painelDados.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                "Dados do Veículo", 0, 0,
                new Font("SansSerif", Font.BOLD, 14), Color.WHITE));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        placaField = new JTextField();
        modeloField = new JTextField();
        cidadeField = new JTextField();
        estadoField = new JTextField();
        tipoCombo = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhão"});

        JLabel lblPlaca = criarLabel("Placa:");
        JLabel lblModelo = criarLabel("Modelo:");
        JLabel lblTipo = criarLabel("Tipo:");
        JLabel lblEstado = criarLabel("Estado:");
        JLabel lblCidade = criarLabel("Cidade:");

        gbc.gridx = 0; gbc.gridy = 0;
        painelDados.add(lblPlaca, gbc);
        gbc.gridx = 1;
        painelDados.add(placaField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelDados.add(lblModelo, gbc);
        gbc.gridx = 1;
        painelDados.add(modeloField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelDados.add(lblTipo, gbc);
        gbc.gridx = 1;
        painelDados.add(tipoCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painelDados.add(lblEstado, gbc);
        gbc.gridx = 1;
        painelDados.add(estadoField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        painelDados.add(lblCidade, gbc);
        gbc.gridx = 1;
        painelDados.add(cidadeField, gbc);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnRemover = new JButton("Remover");

        gbc.gridx = 0; gbc.gridy = 5;
        painelDados.add(btnAdicionar, gbc);
        gbc.gridx = 1;
        painelDados.add(btnRemover, gbc);

        historicoArea = new JTextArea(10, 40);
        historicoArea.setEditable(false);
        historicoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historicoArea.setBackground(new Color(35, 35, 45));
        historicoArea.setForeground(Color.WHITE);

        JScrollPane scrollHistorico = new JScrollPane(historicoArea);
        scrollHistorico.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                "Histórico de Veículos", 0, 0,
                new Font("SansSerif", Font.BOLD, 14), Color.WHITE));
        scrollHistorico.getViewport().setBackground(new Color(35, 35, 45));

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rodape.setBackground(new Color(30, 30, 40));
        vagasLabel = new JLabel("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
        vagasLabel.setForeground(Color.WHITE);
        rodape.add(vagasLabel);

        add(painelDados, BorderLayout.NORTH);
        add(scrollHistorico, BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> adicionarVeiculo());
        btnRemover.addActionListener(e -> removerVeiculo());

        atualizarHistorico();
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        return label;
    }

    private void adicionarVeiculo() {
        String placa = placaField.getText().trim();
        String modelo = modeloField.getText().trim();
        String tipo = (String) tipoCombo.getSelectedItem();
        String cidade = cidadeField.getText().trim();
        String estado = estadoField.getText().trim();

        if (placa.isEmpty() || modelo.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        Veiculo veiculo = new Veiculo(placa, modelo, tipo, cidade, estado);
        boolean sucesso = estacionamento.adicionarVeiculo(veiculo);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Veículo adicionado com sucesso!");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível adicionar o veículo.");
        }

        atualizarHistorico();
    }

    private void removerVeiculo() {
        String placa = placaField.getText().trim();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a placa para remover.");
            return;
        }

        Veiculo removido = estacionamento.removerVeiculoPorPlaca(placa);
        if (removido != null) {
            JOptionPane.showMessageDialog(this, "Veículo removido.");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Veículo não encontrado.");
        }

        atualizarHistorico();
    }

    private void atualizarHistorico() {
        historicoArea.setText(estacionamento.gerarHistoricoComData());
        vagasLabel.setText("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
    }

    private void limparCampos() {
        placaField.setText("");
        modeloField.setText("");
        cidadeField.setText("");
        estadoField.setText("");
        tipoCombo.setSelectedIndex(0);
    }
}
