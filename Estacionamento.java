import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private final int totalVagas = 100;
    private List<Veiculo> vagasOcupadas = new ArrayList<>();

    public boolean entrarVeiculo(Veiculo v) {
        if (vagasOcupadas.size() >= totalVagas) return false;
        for (Veiculo veic : vagasOcupadas) {
            if (veic.getPlaca().equalsIgnoreCase(v.getPlaca()) &&
                veic.getTipo().equalsIgnoreCase(v.getTipo())) {
                return false;
            }
        }
        vagasOcupadas.add(v);
        return true;
    }

    public boolean sairVeiculo(String placa, String tipo) {
        return vagasOcupadas.removeIf(v -> 
            v.getPlaca().equalsIgnoreCase(placa) && 
            v.getTipo().equalsIgnoreCase(tipo)
        );
    }

    public int getVagasOcupadas() {
        return vagasOcupadas.size();
    }

    public int getTotalVagas() {
        return totalVagas;
    }
}
