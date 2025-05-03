import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaEstacionamento extends JFrame {
    private Estacionamento estacionamento;
    private JTextField placaField, modeloField, cidadeField, estadoField;
    private JComboBox<String> tipoCombo;
    private JTextArea historicoArea;
    private JLabel vagasLabel;

    public TelaEstacionamento() {
        estacionamento = new Estacionamento();

        setTitle("Sistema de Estacionamento");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        panel.add(new JLabel("Placa:"));
        placaField = new JTextField();
        panel.add(placaField);

        panel.add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        panel.add(modeloField);

        panel.add(new JLabel("Tipo:"));
        tipoCombo = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhão"});
        panel.add(tipoCombo);

        panel.add(new JLabel("Cidade:"));
        cidadeField = new JTextField();
        panel.add(cidadeField);

        panel.add(new JLabel("Estado:"));
        estadoField = new JTextField();
        panel.add(estadoField);

        JButton adicionarBtn = new JButton("Adicionar Veículo");
        JButton removerBtn = new JButton("Remover Veículo");
        panel.add(adicionarBtn);
        panel.add(removerBtn);

        vagasLabel = new JLabel("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
        panel.add(vagasLabel);

        historicoArea = new JTextArea();
        historicoArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(historicoArea);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        adicionarBtn.addActionListener(e -> adicionarVeiculo());
        removerBtn.addActionListener(e -> removerVeiculo());

        atualizarHistorico();
    }

    private void adicionarVeiculo() {
        String placa = placaField.getText().trim();
        String modelo = modeloField.getText().trim();
        String tipo = (String) tipoCombo.getSelectedItem();
        String cidade = cidadeField.getText().trim();
        String estado = estadoField.getText().trim();

        if (placa.isEmpty() || modelo.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        Veiculo veiculo = new Veiculo(placa, modelo, tipo, cidade, estado);
        boolean sucesso = estacionamento.adicionarVeiculo(veiculo);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Veículo adicionado com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar. Verifique se a placa já está cadastrada ou se o limite foi atingido.");
        }
        atualizarHistorico();
    }

    private void removerVeiculo() {
        String placa = placaField.getText().trim();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a placa para remover o veículo.");
            return;
        }

        Veiculo removido = estacionamento.removerVeiculoPorPlaca(placa);
        if (removido != null) {
            JOptionPane.showMessageDialog(this, "Veículo removido com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Veículo não encontrado.");
        }
        atualizarHistorico();
    }

    private void atualizarHistorico() {
        historicoArea.setText(estacionamento.gerarHistoricoComData());
        vagasLabel.setText("Vagas disponíveis: " + estacionamento.getVagasDisponiveis());
    }
} 
