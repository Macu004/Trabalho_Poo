import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Estacionamento {
    private List<Veiculo> veiculos;
    private final int LIMITE_VAGAS = 100;
    private final String ARQUIVO = "dados_estacionamento.dat";

    public Estacionamento() {
        veiculos = new ArrayList<>();
        carregarDados();
    }

    public boolean adicionarVeiculo(Veiculo veiculo) {
        if (veiculos.size() >= LIMITE_VAGAS) return false;
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(veiculo.getPlaca())) return false;
        }
        veiculo.setHorarioEntrada(new Date());
        veiculos.add(veiculo);
        salvarDados();
        return true;
    }

    public Veiculo removerVeiculoPorPlaca(String placa) {
        Iterator<Veiculo> it = veiculos.iterator();
        while (it.hasNext()) {
            Veiculo v = it.next();
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                v.setHorarioSaida(new Date());
                it.remove();
                salvarDados();
                return v;
            }
        }
        return null;
    }

    public int getVagasDisponiveis() {
        return LIMITE_VAGAS - veiculos.size();
    }

    public String gerarHistoricoComData() {
        SimpleDateFormat sdfDia = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
        StringBuilder sb = new StringBuilder();
        for (Veiculo v : veiculos) {
            if (v.getHorarioEntrada() != null &&
                v.getPlaca() != null && v.getModelo() != null &&
                v.getTipo() != null && v.getCidade() != null && v.getEstado() != null) {

                String linha = String.format("ENTROU: %s, %s, %s, %s-%s, Dia: %s às %s\n",
                    v.getPlaca(), v.getModelo(), v.getTipo(),
                    v.getCidade(), v.getEstado(),
                    sdfDia.format(v.getHorarioEntrada()),
                    sdfHora.format(v.getHorarioEntrada()));

                sb.append(linha);
            } else {
                sb.append("[Dados incompletos para veículo]\n");
            }
        }
        return sb.toString();
    }

    private void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            out.writeObject(veiculos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            veiculos = new ArrayList<>();
            System.out.println("Nenhum arquivo de dados encontrado. Iniciando lista vazia.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                veiculos = (List<Veiculo>) obj;
            } else {
                System.err.println("Erro: formato de dados inválido no arquivo.");
                veiculos = new ArrayList<>();
            }
        } catch (InvalidClassException ice) {
            System.err.println("Erro de compatibilidade entre classes: " + ice.getMessage());
            veiculos = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados (detalhes): ");
            e.printStackTrace();
            veiculos = new ArrayList<>();
        }
    }
}
