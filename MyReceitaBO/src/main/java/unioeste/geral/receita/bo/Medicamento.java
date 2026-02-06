package unioeste.geral.receita.bo;
import java.io.Serializable;

public class Medicamento implements Serializable {
    private int idMedicamento;
    private String nomeGenerico;
    private String fabricante; // Opcional

    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }
    public String getNomeGenerico() { return nomeGenerico; }
    public void setNomeGenerico(String nomeGenerico) { this.nomeGenerico = nomeGenerico; }
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }
}