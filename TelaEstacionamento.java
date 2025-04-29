import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEstacionamento extends JFrame {
    private Estacionamento estacionamento = new Estacionamento();

    private JTextField campoPlaca = new JTextField(15);
    private JTextField campoModelo = new JTextField(15);
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhão"});
    private JTextField campoCidade = new JTextField(15);
    private JTextField campoEstado = new JTextField(15);

    private JLabel lblTotalVagas = new JLabel("Total de vagas: 100");
    private JLabel lblVagasOcupadas = new JLabel("Vagas ocupadas: 0");
    private JLabel lblVagasDisponiveis = new JLabel("Vagas disponíveis: 100");
    private JTextArea historico = new JTextArea(8, 25);

    public TelaEstacionamento() {
        setTitle("Estacionamento Shopping");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Total de vagas:"), gbc);
        gbc.gridx = 1; add(lblTotalVagas, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Vagas ocupadas:"), gbc);
        gbc.gridx = 1; add(lblVagasOcupadas, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Vagas disponíveis:"), gbc);
        gbc.gridx = 1; add(lblVagasDisponiveis, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        add(new JLabel("Registrar Veículo"), gbc);

        gbc.gridy++;
        add(new JLabel("Placa:"), gbc);
        gbc.gridy++;
        add(campoPlaca, gbc);

        gbc.gridy++;
        add(new JLabel("Modelo:"), gbc);
        gbc.gridy++;
        add(campoModelo, gbc);

        gbc.gridy++;
        add(new JLabel("Tipo:"), gbc);
        gbc.gridy++;
        add(comboTipo, gbc);

        gbc.gridy++;
        add(new JLabel("Cidade:"), gbc);
        gbc.gridy++;
        add(campoCidade, gbc);

        gbc.gridy++;
        add(new JLabel("Estado:"), gbc);
        gbc.gridy++;
        add(campoEstado, gbc);

        gbc.gridy++;
        JPanel botoes = new JPanel();
        JButton btnEntrar = new JButton("Entrar");
        JButton btnSair = new JButton("Sair");
        botoes.add(btnEntrar);
        botoes.add(btnSair);
        add(botoes, gbc);

        gbc.gridy++;
        add(new JLabel("Histórico"), gbc);

        gbc.gridy++;
        historico.setEditable(false);
        JScrollPane scroll = new JScrollPane(historico);
        add(scroll, gbc);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = campoPlaca.getText().trim().toUpperCase();
                String modelo = campoModelo.getText().trim();
                String tipo = comboTipo.getSelectedItem().toString();
                String cidade = campoCidade.getText().trim();
                String estado = campoEstado.getText().trim();

                if (placa.isEmpty() || modelo.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                    return;
                }

                Veiculo veiculo = new Veiculo(placa, modelo, tipo, cidade, estado);
                if (estacionamento.entrarVeiculo(veiculo)) {
                    atualizarContagem();
                    historico.append("Veículo " + tipo + " " + placa + " de " + cidade + "/" + estado + " entrou\n");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro: Vaga indisponível ou veículo já registrado.");
                }
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = campoPlaca.getText().trim().toUpperCase();
                String tipo = comboTipo.getSelectedItem().toString();
                if (placa.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite a placa para saída.");
                    return;
                }

                if (estacionamento.sairVeiculo(placa, tipo)) {
                    atualizarContagem();
                    historico.append("Veículo " + tipo + " " + placa + " saiu\n");
                } else {
                    JOptionPane.showMessageDialog(null, "Veículo não encontrado ou tipo incorreto.");
                }
            }
        });

        setVisible(true);
    }

    private void atualizarContagem() {
        int ocupadas = estacionamento.getVagasOcupadas();
        int disponiveis = estacionamento.getTotalVagas() - ocupadas;

        lblVagasOcupadas.setText("Vagas ocupadas: " + ocupadas);
        lblVagasDisponiveis.setText("Vagas disponíveis: " + disponiveis);
    }
}
