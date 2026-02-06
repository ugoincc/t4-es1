package unioeste.geral.pessoa.bo;

import java.io.Serializable;

public class Telefone implements Serializable {
    public static final long serialVersionUID = 1L;

    private int idTelefone;
    private String numero;
    private DDD ddd;

    public Telefone() {}

    public Telefone(int idTelefone, String numero, DDD ddd) {
        this.idTelefone = idTelefone;
        this.numero = numero;
        this.ddd = ddd;
    }

    public int getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(int idTelefone) {
        this.idTelefone = idTelefone;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public DDD getDdd() {
        return ddd;
    }

    public void setDdd(DDD ddd) {
        this.ddd = ddd;
    }

    public String getNumeroCompleto() {
        if (ddd != null && ddd.getDdi() != null) {
            return ddd.getDdi().getCodigo() + " (" + ddd.getCodigo() + ") " + numero;
        } else if (ddd != null) {
            return "(" + ddd.getCodigo() + ") " + numero;
        }
        return numero;
    }
}
