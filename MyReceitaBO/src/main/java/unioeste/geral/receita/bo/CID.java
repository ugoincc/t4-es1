package unioeste.geral.receita.bo;
import java.io.Serializable;

public class CID implements Serializable {
    private String codigo; // Ex: A00.1
    private String descricao;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}