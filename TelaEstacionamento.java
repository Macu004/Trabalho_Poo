import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEstacionamento extends JFrame {
    private Estacionamento estacionamento = new Estacionamento();

    private JTextField campoPlaca = new JTextField(10);
    private JTextField campoModelo = new JTextField(10);
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhão"});

    private JLabel lblTotalVagas = new JLabel("Total de vagas: 100");
    private JLabel lblVagasOcupadas = new JLabel("Vagas ocupadas: 0");
    private JLabel lblVagasDisponiveis = new JLabel("Vagas disponíveis: 100");
    private JTextArea historico = new JTextArea(5, 20);

    public TelaEstacionamento() {
        setTitle("Estacionamento Shopping");
        setSize(320, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        add(new JLabel("Vagas"));
        add(lblTotalVagas);
        add(lblVagasOcupadas);
        add(lblVagasDisponiveis);

        add(new JLabel("Registrar Veículo"));
        add(new JLabel("Placa:"));
        add(campoPlaca);
        add(new JLabel("Modelo:"));
        add(campoModelo);
        add(new JLabel("Tipo:"));
        add(comboTipo);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnSair = new JButton("Sair");
        add(btnEntrar);
        add(btnSair);

        add(new JLabel("Histórico"));
        historico.setEditable(false);
        add(historico);

        // Ação botão Entrar
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = campoPlaca.getText().trim().toUpperCase();
                String modelo = campoModelo.getText().trim();
                String tipo = comboTipo.getSelectedItem().toString();

                if (placa.isEmpty() || modelo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                    return;
                }

                Veiculo veiculo = new Veiculo(placa, modelo, tipo);
                if (estacionamento.entrarVeiculo(veiculo)) {
                    atualizarContagem();
                    historico.append("Veículo " + placa + " entrou\n");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro: Vaga indisponível ou veículo já registrado.");
                }
            }
        });

        // Ação botão Sair
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = campoPlaca.getText().trim().toUpperCase();
                if (placa.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite a placa para saída.");
                    return;
                }

                if (estacionamento.sairVeiculo(placa)) {
                    atualizarContagem();
                    historico.append("Veículo " + placa + " saiu\n");
                } else {
                    JOptionPane.showMessageDialog(null, "Veículo não encontrado.");
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
