package unioeste.geral.pessoa.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import unioeste.geral.endereco.bo.EnderecoEspecifico;

public abstract class Pessoa implements Serializable {
    public static final long serialVersionUID = 1L;

    private int idPessoa;
    private String nome;
    private String nomeSocial;
    private EnderecoEspecifico endereco;
    private List<Email> emails;
    private List<Telefone> telefones;

    public Pessoa() {
        this.emails = new ArrayList<>();
        this.telefones = new ArrayList<>();
        this.endereco = new EnderecoEspecifico();
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public EnderecoEspecifico getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoEspecifico endereco) {
        this.endereco = endereco;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public void addEmail(Email email) {
        if (this.emails == null) {
            this.emails = new ArrayList<>();
        }
        this.emails.add(email);
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public void addTelefone(Telefone telefone) {
        if (this.telefones == null) {
            this.telefones = new ArrayList<>();
        }
        this.telefones.add(telefone);
    }
}
