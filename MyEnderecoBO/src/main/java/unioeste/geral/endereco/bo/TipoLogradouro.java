package unioeste.geral.endereco.bo;
import java.io.Serializable;

public class TipoLogradouro implements Serializable {
    private int cod;
    private String nome;

    public int getCod() { return cod; }
    public void setCod(int cod) { this.cod = cod; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}