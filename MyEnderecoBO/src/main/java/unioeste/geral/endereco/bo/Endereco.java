package unioeste.geral.endereco.bo;
import java.io.Serializable;

public class Endereco implements Serializable {
    private int idEndereco;
    private String cep;
    private Logradouro logradouro;
    private Bairro bairro;
    private Cidade cidade;

    public int getIdEndereco() { return idEndereco; }
    public void setIdEndereco(int idEndereco) { this.idEndereco = idEndereco; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public Logradouro getLogradouro() { return logradouro; }
    public void setLogradouro(Logradouro logradouro) { this.logradouro = logradouro; }
    public Bairro getBairro() { return bairro; }
    public void setBairro(Bairro bairro) { this.bairro = bairro; }
    public Cidade getCidade() { return cidade; }
    public void setCidade(Cidade cidade) { this.cidade = cidade; }
}