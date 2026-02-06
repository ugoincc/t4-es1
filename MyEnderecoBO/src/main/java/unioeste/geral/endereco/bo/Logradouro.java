package unioeste.geral.endereco.bo;
import java.io.Serializable;

public class Logradouro implements Serializable {
    private int idLogradouro;
    private String nome;
    private TipoLogradouro tipo; // Classe TipoLogradouro segue a mesma logica simples

    public int getIdLogradouro() { return idLogradouro; }
    public void setIdLogradouro(int idLogradouro) { this.idLogradouro = idLogradouro; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoLogradouro getTipo() { return tipo; }
    public void setTipo(TipoLogradouro tipo) { this.tipo = tipo; }
}