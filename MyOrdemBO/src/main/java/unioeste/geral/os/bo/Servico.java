package unioeste.geral.os.bo;
import java.io.Serializable;

public class Servico implements Serializable {
    private int cod;
    private String tipoServico;
    private double valor;

    public int getCod() { return cod; }
    public void setCod(int cod) { this.cod = cod; }
    public String getTipoServico() { return tipoServico; }
    public void setTipoServico(String tipoServico) { this.tipoServico = tipoServico; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}