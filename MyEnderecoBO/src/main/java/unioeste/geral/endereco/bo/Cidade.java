package unioeste.geral.endereco.bo;
import java.io.Serializable;

public class Cidade implements Serializable {
    private int idCidade;
    private String nome;
    private UnidadeFederacao uf;

    public int getIdCidade() { return idCidade; }
    public void setIdCidade(int idCidade) { this.idCidade = idCidade; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public UnidadeFederacao getUf() { return uf; }
    public void setUf(UnidadeFederacao uf) { this.uf = uf; }
}