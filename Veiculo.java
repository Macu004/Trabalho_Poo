public class Veiculo {
    private String placa;
    private String modelo;
    private String tipo;
    private String cidade;
    private String estado;

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

    public String getModelo() {
        return modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }
}
