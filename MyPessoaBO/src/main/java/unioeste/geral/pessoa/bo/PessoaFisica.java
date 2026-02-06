package unioeste.geral.pessoa.bo;

public class PessoaFisica extends Pessoa {
    private String cpf;
    private String primeiroNome; // Opcional se usar 'nome' completo em Pessoa
    private String sobreNome;

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getPrimeiroNome() { return primeiroNome; }
    public void setPrimeiroNome(String primeiroNome) { this.primeiroNome = primeiroNome; }
    public String getSobreNome() { return sobreNome; }
    public void setSobreNome(String sobreNome) { this.sobreNome = sobreNome; }
}