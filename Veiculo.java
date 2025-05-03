
import java.io.Serializable;
import java.util.Date;

public class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String placa;
    private String modelo;
    private String tipo;
    private String cidade;
    private String estado;
    private Date horarioEntrada;
    private Date horarioSaida;

    public Veiculo(String placa, String modelo, String tipo, String cidade, String estado) {
        this.placa = placa;
        this.modelo = modelo;
        this.tipo = tipo;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(Date horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Date getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(Date horarioSaida) {
        this.horarioSaida = horarioSaida;
    }
}
