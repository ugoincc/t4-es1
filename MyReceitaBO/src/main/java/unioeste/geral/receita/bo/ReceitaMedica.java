package unioeste.geral.receita.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceitaMedica implements Serializable {
    private int numeroReceita;
    private Date dataEmissao;
    
    // Atores
    private Medico medico;
    private Paciente paciente;
    
    // Diagnóstico
    private CID cid; 
    
    // Composição (Lista de Itens)
    private List<Prescricao> prescricoes;

    public ReceitaMedica() {
        this.prescricoes = new ArrayList<>();
    }

    public void adicionarPrescricao(Prescricao p) {
        this.prescricoes.add(p);
    }

    // Getters e Setters
    public int getNumeroReceita() { return numeroReceita; }
    public void setNumeroReceita(int numeroReceita) { this.numeroReceita = numeroReceita; }
    public Date getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(Date dataEmissao) { this.dataEmissao = dataEmissao; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public CID getCid() { return cid; }
    public void setCid(CID cid) { this.cid = cid; }
    public List<Prescricao> getPrescricoes() { return prescricoes; }
    public void setPrescricoes(List<Prescricao> prescricoes) { this.prescricoes = prescricoes; }
}