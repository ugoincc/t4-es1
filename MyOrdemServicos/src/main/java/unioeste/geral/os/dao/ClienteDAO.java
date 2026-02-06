package unioeste.geral.os.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import unioeste.geral.os.bo.Cliente;
import unioeste.geral.endereco.bo.Endereco;
import unioeste.geral.endereco.bo.EnderecoEspecifico;
import unioeste.geral.endereco.bo.Logradouro;
import unioeste.geral.endereco.bo.Bairro;
import unioeste.geral.endereco.bo.Cidade;
import unioeste.geral.pessoa.bo.Telefone;
import unioeste.geral.pessoa.bo.Email;
import unioeste.geral.pessoa.bo.DDD;
import unioeste.geral.pessoa.bo.DDI;

public class ClienteDAO {

    public static Cliente selectClienteByCPF(String cpf, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT c.id_cliente, c.cpf, c.nome, c.nome_social, ");
        sql.append("c.numero_casa, c.complemento, ");
        sql.append("e.id_endereco, e.cep, ");
        sql.append("l.id_logradouro, l.nome_logradouro, ");
        sql.append("b.id_bairro, b.nome_bairro, ");
        sql.append("ci.id_cidade, ci.nome_cidade, ci.sigla_estado ");
        sql.append("FROM cliente c ");
        sql.append("LEFT JOIN endereco e ON c.id_endereco = e.id_endereco ");
        sql.append("LEFT JOIN logradouro l ON e.id_logradouro = l.id_logradouro ");
        sql.append("LEFT JOIN bairro b ON e.id_bairro = b.id_bairro ");
        sql.append("LEFT JOIN cidade ci ON e.id_cidade = ci.id_cidade ");
        sql.append("WHERE c.cpf = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setString(1, cpf);
        ResultSet rs = cmd.executeQuery();

        if (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setIdPessoa(rs.getInt("id_cliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setNome(rs.getString("nome"));
            cliente.setNomeSocial(rs.getString("nome_social"));

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

            cliente.setEndereco(endEsp);

            carregarTelefones(cliente, conexao);
            carregarEmails(cliente, conexao);

            return cliente;
        }

        return null;
    }

    public static Cliente selectClienteById(int id, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT c.id_cliente, c.cpf, c.nome, c.nome_social, ");
        sql.append("c.numero_casa, c.complemento, ");
        sql.append("e.id_endereco, e.cep, ");
        sql.append("l.id_logradouro, l.nome_logradouro, ");
        sql.append("b.id_bairro, b.nome_bairro, ");
        sql.append("ci.id_cidade, ci.nome_cidade, ci.sigla_estado ");
        sql.append("FROM cliente c ");
        sql.append("LEFT JOIN endereco e ON c.id_endereco = e.id_endereco ");
        sql.append("LEFT JOIN logradouro l ON e.id_logradouro = l.id_logradouro ");
        sql.append("LEFT JOIN bairro b ON e.id_bairro = b.id_bairro ");
        sql.append("LEFT JOIN cidade ci ON e.id_cidade = ci.id_cidade ");
        sql.append("WHERE c.id_cliente = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, id);
        ResultSet rs = cmd.executeQuery();

        if (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setIdPessoa(rs.getInt("id_cliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setNome(rs.getString("nome"));
            cliente.setNomeSocial(rs.getString("nome_social"));

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

            cliente.setEndereco(endEsp);

            carregarTelefones(cliente, conexao);
            carregarEmails(cliente, conexao);

            return cliente;
        }

        return null;
    }

    private static void carregarTelefones(Cliente cliente, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT t.id_telefone, t.numero, ");
        sql.append("d.id_ddd, d.codigo AS ddd_codigo, d.regiao, ");
        sql.append("i.id_ddi, i.codigo AS ddi_codigo, i.pais ");
        sql.append("FROM cliente_telefone ct ");
        sql.append("JOIN telefone t ON ct.id_telefone = t.id_telefone ");
        sql.append("LEFT JOIN ddd d ON t.id_ddd = d.id_ddd ");
        sql.append("LEFT JOIN ddi i ON d.id_ddi = i.id_ddi ");
        sql.append("WHERE ct.id_cliente = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, cliente.getIdPessoa());
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

            cliente.addTelefone(telefone);
        }
    }

    private static void carregarEmails(Cliente cliente, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT e.id_email, e.endereco_email ");
        sql.append("FROM cliente_email ce ");
        sql.append("JOIN email e ON ce.id_email = e.id_email ");
        sql.append("WHERE ce.id_cliente = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, cliente.getIdPessoa());
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            Email email = new Email();
            email.setIdEmail(rs.getInt("id_email"));
            email.setEnderecoEmail(rs.getString("endereco_email"));
            cliente.addEmail(email);
        }
    }

    // ========== INSERT ==========

    public static Cliente insertCliente(Cliente cliente, Connection conexao) throws Exception {
        // 1. Resolver endereco (reaproveitar existente por CEP ou criar novo)
        Integer idEndereco = null;
        EnderecoEspecifico endEsp = cliente.getEndereco();

        if (endEsp != null && endEsp.getEndereco() != null && endEsp.getEndereco().getCep() != null) {
            Endereco end = endEsp.getEndereco();
            String cep = end.getCep().replaceAll("[^0-9]", "");

            idEndereco = findEnderecoIdByCep(cep, conexao);

            if (idEndereco == null) {
                String siglaEstado = null;
                if (end.getCidade() != null && end.getCidade().getUf() != null) {
                    siglaEstado = end.getCidade().getUf().getSigla();
                }
                if (siglaEstado != null && !siglaEstado.isEmpty()) {
                    findOrCreateEstado(siglaEstado, conexao);
                }
                int idCidade = findOrCreateCidade(
                    end.getCidade() != null ? end.getCidade().getNome() : null,
                    siglaEstado, conexao);
                int idBairro = findOrCreateBairro(
                    end.getBairro() != null ? end.getBairro().getNome() : null,
                    idCidade, conexao);
                int idLogradouro = findOrCreateLogradouro(
                    end.getLogradouro() != null ? end.getLogradouro().getNome() : null,
                    conexao);
                idEndereco = insertEndereco(cep, idLogradouro, idBairro, idCidade, conexao);
            }
        }

        // 2. Inserir cliente
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO cliente (cpf, nome, nome_social, id_endereco, numero_casa, complemento) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?) RETURNING id_cliente");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setString(1, cliente.getCpf());
        cmd.setString(2, cliente.getNome());
        cmd.setString(3, cliente.getNomeSocial());
        if (idEndereco != null) {
            cmd.setInt(4, idEndereco);
        } else {
            cmd.setNull(4, java.sql.Types.INTEGER);
        }
        cmd.setInt(5, endEsp != null ? endEsp.getNumero() : 0);
        cmd.setString(6, endEsp != null ? endEsp.getComplemento() : null);

        ResultSet rs = cmd.executeQuery();
        int idCliente = -1;
        if (rs.next()) {
            idCliente = rs.getInt("id_cliente");
        }

        // 3. Inserir emails
        if (cliente.getEmails() != null) {
            for (Email email : cliente.getEmails()) {
                if (email.getEnderecoEmail() != null && !email.getEnderecoEmail().isEmpty()) {
                    int idEmail = insertEmail(email.getEnderecoEmail(), conexao);
                    insertClienteEmail(idCliente, idEmail, conexao);
                }
            }
        }

        // 4. Inserir telefones
        if (cliente.getTelefones() != null) {
            for (Telefone tel : cliente.getTelefones()) {
                if (tel.getNumero() != null && !tel.getNumero().isEmpty()) {
                    int idDDD = findOrCreateDDD(
                        tel.getDdd() != null ? tel.getDdd().getCodigo() : null, conexao);
                    int idTelefone = insertTelefone(tel.getNumero(), idDDD, conexao);
                    insertClienteTelefone(idCliente, idTelefone, conexao);
                }
            }
        }

        // 5. Retornar cliente completo
        return selectClienteById(idCliente, conexao);
    }

    // --- Helpers de Endereco ---

    private static Integer findEnderecoIdByCep(String cep, Connection conexao) throws Exception {
        PreparedStatement cmd = conexao.prepareStatement(
            "SELECT id_endereco FROM endereco WHERE cep = ? LIMIT 1");
        cmd.setString(1, cep);
        ResultSet rs = cmd.executeQuery();
        if (rs.next()) {
            return rs.getInt("id_endereco");
        }
        return null;
    }

    private static void findOrCreateEstado(String sigla, Connection conexao) throws Exception {
        PreparedStatement cmd = conexao.prepareStatement(
            "SELECT sigla_estado FROM estado WHERE sigla_estado = ?");
        cmd.setString(1, sigla.toUpperCase());
        ResultSet rs = cmd.executeQuery();
        if (rs.next()) return;

        PreparedStatement cmdInsert = conexao.prepareStatement(
            "INSERT INTO estado (sigla_estado, nome_estado) VALUES (?, ?)");
        cmdInsert.setString(1, sigla.toUpperCase());
        cmdInsert.setString(2, sigla.toUpperCase());
        cmdInsert.executeUpdate();
    }

    private static int findOrCreateCidade(String nome, String siglaEstado, Connection conexao) throws Exception {
        if (nome != null && !nome.isEmpty()) {
            PreparedStatement cmd = conexao.prepareStatement(
                "SELECT id_cidade FROM cidade WHERE LOWER(nome_cidade) = LOWER(?) LIMIT 1");
            cmd.setString(1, nome);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) return rs.getInt("id_cidade");
        }

        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO cidade (nome_cidade, sigla_estado) VALUES (?, ?) RETURNING id_cidade");
        cmd.setString(1, nome != null && !nome.isEmpty() ? nome : "Desconhecida");
        cmd.setString(2, siglaEstado);
        ResultSet rs = cmd.executeQuery();
        rs.next();
        return rs.getInt("id_cidade");
    }

    private static int findOrCreateBairro(String nome, int idCidade, Connection conexao) throws Exception {
        if (nome != null && !nome.isEmpty()) {
            PreparedStatement cmd = conexao.prepareStatement(
                "SELECT id_bairro FROM bairro WHERE LOWER(nome_bairro) = LOWER(?) AND id_cidade = ? LIMIT 1");
            cmd.setString(1, nome);
            cmd.setInt(2, idCidade);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) return rs.getInt("id_bairro");
        }

        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO bairro (nome_bairro, id_cidade) VALUES (?, ?) RETURNING id_bairro");
        cmd.setString(1, nome != null && !nome.isEmpty() ? nome : "Desconhecido");
        cmd.setInt(2, idCidade);
        ResultSet rs = cmd.executeQuery();
        rs.next();
        return rs.getInt("id_bairro");
    }

    private static int findOrCreateLogradouro(String nome, Connection conexao) throws Exception {
        if (nome != null && !nome.isEmpty()) {
            PreparedStatement cmd = conexao.prepareStatement(
                "SELECT id_logradouro FROM logradouro WHERE LOWER(nome_logradouro) = LOWER(?) LIMIT 1");
            cmd.setString(1, nome);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) return rs.getInt("id_logradouro");
        }

        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO logradouro (nome_logradouro) VALUES (?) RETURNING id_logradouro");
        cmd.setString(1, nome != null && !nome.isEmpty() ? nome : "Desconhecido");
        ResultSet rs = cmd.executeQuery();
        rs.next();
        return rs.getInt("id_logradouro");
    }

    private static int insertEndereco(String cep, int idLogradouro, int idBairro, int idCidade, Connection conexao) throws Exception {
        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO endereco (cep, id_logradouro, id_bairro, id_cidade) VALUES (?, ?, ?, ?) RETURNING id_endereco");
        cmd.setString(1, cep);
        cmd.setInt(2, idLogradouro);
        cmd.setInt(3, idBairro);
        cmd.setInt(4, idCidade);
        ResultSet rs = cmd.executeQuery();
        rs.next();
        return rs.getInt("id_endereco");
    }

    // --- Helpers de Email ---

    private static int insertEmail(String enderecoEmail, Connection conexao) throws Exception {
        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO email (endereco_email) VALUES (?) RETURNING id_email");
        cmd.setString(1, enderecoEmail);
        ResultSet rs = cmd.executeQuery();
        rs.next();
        return rs.getInt("id_email");
    }

    private static void insertClienteEmail(int idCliente, int idEmail, Connection conexao) throws Exception {
        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO cliente_email (id_cliente, id_email) VALUES (?, ?)");
        cmd.setInt(1, idCliente);
        cmd.setInt(2, idEmail);
        cmd.executeUpdate();
    }

    // --- Helpers de Telefone ---

    private static int findOrCreateDDD(String codigo, Connection conexao) throws Exception {
        if (codigo != null && !codigo.isEmpty()) {
            PreparedStatement cmd = conexao.prepareStatement(
                "SELECT id_ddd FROM ddd WHERE codigo = ? LIMIT 1");
            cmd.setString(1, codigo);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) return rs.getInt("id_ddd");
        }

        int idDDI = 1;
        PreparedStatement cmdDDI = conexao.prepareStatement(
            "SELECT id_ddi FROM ddi WHERE codigo = '+55' LIMIT 1");
        ResultSet rsDDI = cmdDDI.executeQuery();
        if (rsDDI.next()) idDDI = rsDDI.getInt("id_ddi");

        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO ddd (codigo, id_ddi) VALUES (?, ?) RETURNING id_ddd");
        cmd.setString(1, codigo != null && !codigo.isEmpty() ? codigo : "00");
        cmd.setInt(2, idDDI);
        ResultSet rs = cmd.executeQuery();
        rs.next();
        return rs.getInt("id_ddd");
    }

    private static int insertTelefone(String numero, int idDDD, Connection conexao) throws Exception {
        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO telefone (numero, id_ddd) VALUES (?, ?) RETURNING id_telefone");
        cmd.setString(1, numero);
        cmd.setInt(2, idDDD);
        ResultSet rs = cmd.executeQuery();
        rs.next();
        return rs.getInt("id_telefone");
    }

    private static void insertClienteTelefone(int idCliente, int idTelefone, Connection conexao) throws Exception {
        PreparedStatement cmd = conexao.prepareStatement(
            "INSERT INTO cliente_telefone (id_cliente, id_telefone) VALUES (?, ?)");
        cmd.setInt(1, idCliente);
        cmd.setInt(2, idTelefone);
        cmd.executeUpdate();
    }
}
