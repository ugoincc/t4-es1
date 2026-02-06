package unioeste.geral.endereco.bo;
import java.io.Serializable;

public class Bairro implements Serializable {
    private int idBairro;
    private String nome;

    public int getIdBairro() { return idBairro; }
    public void setIdBairro(int idBairro) { this.idBairro = idBairro; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}