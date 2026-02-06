package unioeste.geral.receita.bo;
import java.io.Serializable;

public class Prescricao implements Serializable {
    private Medicamento medicamento; 
    private String posologia; 
    private String periodoUso; 

    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public String getPosologia() { return posologia; }
    public void setPosologia(String posologia) { this.posologia = posologia; }
    public String getPeriodoUso() { return periodoUso; }
    public void setPeriodoUso(String periodoUso) { this.periodoUso = periodoUso; }
}