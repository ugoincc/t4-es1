package unioeste.geral.os.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import unioeste.geral.os.bo.Atendente;
import unioeste.geral.endereco.bo.Endereco;
import unioeste.geral.endereco.bo.EnderecoEspecifico;
import unioeste.geral.endereco.bo.Logradouro;
import unioeste.geral.endereco.bo.Bairro;
import unioeste.geral.endereco.bo.Cidade;
import unioeste.geral.pessoa.bo.Telefone;
import unioeste.geral.pessoa.bo.Email;
import unioeste.geral.pessoa.bo.DDD;
import unioeste.geral.pessoa.bo.DDI;

import java.util.ArrayList;
import java.util.List;

public class AtendenteDAO {

    public static List<Atendente> selectAllAtendentes(Connection conexao) throws Exception {
        List<Atendente> atendentes = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.id_atendente, a.cpf, a.nome, a.nome_social, ");
        sql.append("a.numero_casa, a.complemento, ");
        sql.append("e.id_endereco, e.cep, ");
        sql.append("l.id_logradouro, l.nome_logradouro, ");
        sql.append("b.id_bairro, b.nome_bairro, ");
        sql.append("ci.id_cidade, ci.nome_cidade, ci.sigla_estado ");
        sql.append("FROM atendente a ");
        sql.append("LEFT JOIN endereco e ON a.id_endereco = e.id_endereco ");
        sql.append("LEFT JOIN logradouro l ON e.id_logradouro = l.id_logradouro ");
        sql.append("LEFT JOIN bairro b ON e.id_bairro = b.id_bairro ");
        sql.append("LEFT JOIN cidade ci ON e.id_cidade = ci.id_cidade ");
        sql.append("ORDER BY a.nome");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            atendentes.add(montarAtendente(rs, conexao));
        }

        return atendentes;
    }

    public static Atendente selectAtendenteByCPF(String cpf, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.id_atendente, a.cpf, a.nome, a.nome_social, ");
        sql.append("a.numero_casa, a.complemento, ");
        sql.append("e.id_endereco, e.cep, ");
        sql.append("l.id_logradouro, l.nome_logradouro, ");
        sql.append("b.id_bairro, b.nome_bairro, ");
        sql.append("ci.id_cidade, ci.nome_cidade, ci.sigla_estado ");
        sql.append("FROM atendente a ");
        sql.append("LEFT JOIN endereco e ON a.id_endereco = e.id_endereco ");
        sql.append("LEFT JOIN logradouro l ON e.id_logradouro = l.id_logradouro ");
        sql.append("LEFT JOIN bairro b ON e.id_bairro = b.id_bairro ");
        sql.append("LEFT JOIN cidade ci ON e.id_cidade = ci.id_cidade ");
        sql.append("WHERE a.cpf = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setString(1, cpf);
        ResultSet rs = cmd.executeQuery();

        if (rs.next()) {
            return montarAtendente(rs, conexao);
        }

        return null;
    }

    public static Atendente selectAtendenteById(int id, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.id_atendente, a.cpf, a.nome, a.nome_social, ");
        sql.append("a.numero_casa, a.complemento, ");
        sql.append("e.id_endereco, e.cep, ");
        sql.append("l.id_logradouro, l.nome_logradouro, ");
        sql.append("b.id_bairro, b.nome_bairro, ");
        sql.append("ci.id_cidade, ci.nome_cidade, ci.sigla_estado ");
        sql.append("FROM atendente a ");
        sql.append("LEFT JOIN endereco e ON a.id_endereco = e.id_endereco ");
        sql.append("LEFT JOIN logradouro l ON e.id_logradouro = l.id_logradouro ");
        sql.append("LEFT JOIN bairro b ON e.id_bairro = b.id_bairro ");
        sql.append("LEFT JOIN cidade ci ON e.id_cidade = ci.id_cidade ");
        sql.append("WHERE a.id_atendente = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, id);
        ResultSet rs = cmd.executeQuery();

        if (rs.next()) {
            return montarAtendente(rs, conexao);
        }

        return null;
    }

    private static Atendente montarAtendente(ResultSet rs, Connection conexao) throws Exception {
        Atendente atendente = new Atendente();
        atendente.setIdPessoa(rs.getInt("id_atendente"));
        atendente.setCpf(rs.getString("cpf"));
        atendente.setNome(rs.getString("nome"));
        atendente.setNomeSocial(rs.getString("nome_social"));

        EnderecoEspecifico endEsp = new EnderecoEspecifico();
        endEsp.setNumero(rs.getInt("numero_casa"));
        endEsp.setComplemento(rs.getString("complemento"));

        if (rs.getInt("id_endereco") > 0) {
            Endereco endereco = new Endereco();
            endereco.setIdEndereco(rs.getInt("id_endereco"));
            endereco.setCep(rs.getString("cep"));

            Logradouro logradouro = new Logradouro();
            logradouro.setIdLogradouro(rs.getInt("id_logradouro"));
            logradouro.setNome(rs.getString("nome_logradouro"));
            endereco.setLogradouro(logradouro);

            Bairro bairro = new Bairro();
            bairro.setIdBairro(rs.getInt("id_bairro"));
            bairro.setNome(rs.getString("nome_bairro"));
            endereco.setBairro(bairro);

            Cidade cidade = new Cidade();
            cidade.setIdCidade(rs.getInt("id_cidade"));
            cidade.setNome(rs.getString("nome_cidade"));
            endereco.setCidade(cidade);

            endEsp.setEndereco(endereco);
        }

        atendente.setEndereco(endEsp);

        carregarTelefones(atendente, conexao);
        carregarEmails(atendente, conexao);

        return atendente;
    }

    private static void carregarTelefones(Atendente atendente, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT t.id_telefone, t.numero, ");
        sql.append("d.id_ddd, d.codigo AS ddd_codigo, d.regiao, ");
        sql.append("i.id_ddi, i.codigo AS ddi_codigo, i.pais ");
        sql.append("FROM atendente_telefone at ");
        sql.append("JOIN telefone t ON at.id_telefone = t.id_telefone ");
        sql.append("LEFT JOIN ddd d ON t.id_ddd = d.id_ddd ");
        sql.append("LEFT JOIN ddi i ON d.id_ddi = i.id_ddi ");
        sql.append("WHERE at.id_atendente = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, atendente.getIdPessoa());
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            Telefone telefone = new Telefone();
            telefone.setIdTelefone(rs.getInt("id_telefone"));
            telefone.setNumero(rs.getString("numero"));

            if (rs.getInt("id_ddd") > 0) {
                DDD ddd = new DDD();
                ddd.setIdDDD(rs.getInt("id_ddd"));
                ddd.setCodigo(rs.getString("ddd_codigo"));
                ddd.setRegiao(rs.getString("regiao"));

                if (rs.getInt("id_ddi") > 0) {
                    DDI ddi = new DDI();
                    ddi.setIdDDI(rs.getInt("id_ddi"));
                    ddi.setCodigo(rs.getString("ddi_codigo"));
                    ddi.setPais(rs.getString("pais"));
                    ddd.setDdi(ddi);
                }

                telefone.setDdd(ddd);
            }

            atendente.addTelefone(telefone);
        }
    }

    private static void carregarEmails(Atendente atendente, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT e.id_email, e.endereco_email ");
        sql.append("FROM atendente_email ae ");
        sql.append("JOIN email e ON ae.id_email = e.id_email ");
        sql.append("WHERE ae.id_atendente = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, atendente.getIdPessoa());
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            Email email = new Email();
            email.setIdEmail(rs.getInt("id_email"));
            email.setEnderecoEmail(rs.getString("endereco_email"));
            atendente.addEmail(email);
        }
    }
}
