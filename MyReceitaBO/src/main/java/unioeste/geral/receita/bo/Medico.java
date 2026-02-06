package unioeste.geral.receita.bo;

import unioeste.geral.pessoa.bo.PessoaFisica;
import java.util.Date;

public class Medico extends PessoaFisica {
    private String crm; // Número do conselho
    private Date dataEmissaoCRM;
    private String ufCRM; // Opcional: Estado do CRM

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    public Date getDataEmissaoCRM() { return dataEmissaoCRM; }
    public void setDataEmissaoCRM(Date dataEmissaoCRM) { this.dataEmissaoCRM = dataEmissaoCRM; }
    public String getUfCRM() { return ufCRM; }
    public void setUfCRM(String ufCRM) { this.ufCRM = ufCRM; }
}