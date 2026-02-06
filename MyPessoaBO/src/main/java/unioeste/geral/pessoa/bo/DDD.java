package unioeste.geral.pessoa.bo;

import java.io.Serializable;

public class DDD implements Serializable {
    public static final long serialVersionUID = 1L;

    private int idDDD;
    private String codigo;
    private DDI ddi;
    private String regiao;

    public DDD() {}

    public DDD(int idDDD, String codigo, DDI ddi, String regiao) {
        this.idDDD = idDDD;
        this.codigo = codigo;
        this.ddi = ddi;
        this.regiao = regiao;
    }

    public int getIdDDD() {
        return idDDD;
    }

    public void setIdDDD(int idDDD) {
        this.idDDD = idDDD;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public DDI getDdi() {
        return ddi;
    }

    public void setDdi(DDI ddi) {
        this.ddi = ddi;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
}
