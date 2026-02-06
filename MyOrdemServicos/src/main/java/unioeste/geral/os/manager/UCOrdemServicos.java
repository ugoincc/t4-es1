package unioeste.geral.os.manager;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import unioeste.apoio.bd.ConexaoSGBD;
import unioeste.geral.os.bo.Atendente;
import unioeste.geral.os.bo.Cliente;
import unioeste.geral.os.bo.OrdemServico;
import unioeste.geral.os.bo.Servico;
import unioeste.geral.os.col.ClienteCOL;
import unioeste.geral.os.col.OrdemServicoCOL;
import unioeste.geral.os.dao.AtendenteDAO;
import unioeste.geral.os.dao.ClienteDAO;
import unioeste.geral.os.dao.OrdemServicoDAO;
import unioeste.geral.os.dao.ServicoDAO;

public class UCOrdemServicos {

    public Cliente obterClientePorCPF(String cpf) throws OrdemServicoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (!ClienteCOL.cpfValido(cpf)) {
                throw new OrdemServicoException("Formato de CPF invalido.");
            }
            return ClienteDAO.selectClienteByCPF(cpf, conn);
        }
    }

    public Cliente cadastrarCliente(Cliente cliente) throws OrdemServicoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (!ClienteCOL.cpfValido(cliente.getCpf())) {
                throw new OrdemServicoException("Formato de CPF invalido.");
            }
            if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
                throw new OrdemServicoException("Nome do cliente e obrigatorio.");
            }
            if (ClienteCOL.clienteCadastrado(cliente.getCpf(), conn)) {
                throw new OrdemServicoException("Cliente com este CPF ja esta cadastrado.");
            }
            return ClienteDAO.insertCliente(cliente, conn);
        } catch (OrdemServicoException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrdemServicoException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public List<Atendente> listarAtendentes() throws OrdemServicoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return AtendenteDAO.selectAllAtendentes(conn);
        } catch (Exception e) {
            throw new OrdemServicoException("Erro ao listar atendentes: " + e.getMessage());
        }
    }

    public Atendente obterAtendentePorId(int id) throws OrdemServicoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return AtendenteDAO.selectAtendenteById(id, conn);
        } catch (Exception e) {
            throw new OrdemServicoException("Erro ao buscar atendente: " + e.getMessage());
        }
    }

    public List<Servico> listarServicos() throws OrdemServicoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return ServicoDAO.selectAllServicos(conn);
        } catch (Exception e) {
            throw new OrdemServicoException("Erro ao listar servicos: " + e.getMessage());
        }
    }

    public List<OrdemServico> listarOrdensServico() throws OrdemServicoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return OrdemServicoDAO.selectAllOrdensServico(conn);
        } catch (Exception e) {
            throw new OrdemServicoException("Erro ao listar ordens de servico: " + e.getMessage());
        }
    }

    public OrdemServico obterOrdemServicoPorNumero(int nroOrdem) throws OrdemServicoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return OrdemServicoDAO.selectOrdemServicoByNro(nroOrdem, conn);
        } catch (Exception e) {
            throw new OrdemServicoException("Erro ao buscar ordem de servico: " + e.getMessage());
        }
    }

    public OrdemServico criarOrdemServico(OrdemServico ordem) throws OrdemServicoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (ordem.getDataEmissao() == null) {
                ordem.setDataEmissao(new Date());
            }

            ordem.calcularTotal();

            if (!OrdemServicoCOL.ordemServicoValida(ordem, conn)) {
                throw new OrdemServicoException("Dados da ordem de servico invalidos.");
            }

            int nroOrdem = OrdemServicoDAO.insertOrdemServico(ordem, conn);

            for (Servico s : ordem.getServicos()) {
                OrdemServicoDAO.insertOrdemServicoServico(nroOrdem, s.getCod(), conn);
            }

            return OrdemServicoDAO.selectOrdemServicoByNro(nroOrdem, conn);

        } catch (OrdemServicoException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrdemServicoException("Erro ao criar ordem de servico: " + e.getMessage());
        }
    }
}
