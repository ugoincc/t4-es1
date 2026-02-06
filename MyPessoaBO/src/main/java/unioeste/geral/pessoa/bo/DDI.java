package unioeste.geral.pessoa.bo;

import java.io.Serializable;

public class DDI implements Serializable {
    public static final long serialVersionUID = 1L;

    private int idDDI;
    private String codigo;
    private String pais;

    public DDI() {}

    public DDI(int idDDI, String codigo, String pais) {
        this.idDDI = idDDI;
        this.codigo = codigo;
        this.pais = pais;
    }

    public int getIdDDI() {
        return idDDI;
    }

    public void setIdDDI(int idDDI) {
        this.idDDI = idDDI;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
