package unioeste.geral.os.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemServico implements Serializable {
    private int nroOrdem;
    private String descricao;
    private Date dataEmissao;
    private double total;
    
    // Associações (Baseadas no seu diagrama)
    private Cliente cliente;
    private Atendente atendente;
    
    // Composição
    private List<Servico> servicos;

    public OrdemServico() {
        this.servicos = new ArrayList<>();
    }

    public void adicionarServico(Servico s) {
        this.servicos.add(s);
        calcularTotal();
    }

    public void calcularTotal() {
        this.total = 0;
        for (Servico s : servicos) {
            this.total += s.getValor();
        }
    }

    // Getters e Setters
    public int getNroOrdem() { return nroOrdem; }
    public void setNroOrdem(int nroOrdem) { this.nroOrdem = nroOrdem; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Date getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(Date dataEmissao) { this.dataEmissao = dataEmissao; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; } // Opcional se for calculado
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Atendente getAtendente() { return atendente; }
    public void setAtendente(Atendente atendente) { this.atendente = atendente; }
    public List<Servico> getServicos() { return servicos; }
    public void setServicos(List<Servico> servicos) { 
        this.servicos = servicos;
        calcularTotal();
    }
}