package unioeste.geral.endereco.manager;

import java.sql.Connection;
import java.util.ArrayList;

import unioeste.apoio.bd.ConexaoSGBD;
import unioeste.geral.endereco.bo.Endereco;
import unioeste.geral.endereco.col.EnderecoCOL;
import unioeste.geral.endereco.dao.EnderecoDAO;
import unioeste.geral.endereco.dao.CidadeDAO;
import unioeste.geral.endereco.infra.CepAPI;
import unioeste.geral.endereco.bo.Cidade;

public class UCEnderecoGeralServicos {

    public Endereco cadastrarEndereco(Endereco endereco) throws EnderecoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            
            if (!EnderecoCOL.EnderecoValido(endereco, conn)) {
                throw new EnderecoException("Dados do endereço inválidos ou incompletos.");
            }
            
            if (EnderecoCOL.EnderecoCadastrado(endereco, conn)) {
                throw new EnderecoException("Endereço já cadastrado.");
            }

            EnderecoDAO.insertEndereco(endereco, conn);
            
            return EnderecoDAO.selectEnderecoCEP(endereco.getCep(), conn);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new EnderecoException("Erro ao cadastrar: " + e.getMessage());
        }
    }

    public Endereco obterEnderecoPorCEP(String cep) throws EnderecoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (!EnderecoCOL.CEPValido(cep)) {
                throw new EnderecoException("Formato de CEP inválido.");
            }
            return EnderecoDAO.selectEnderecoCEP(cep, conn);
        }
    }

    public Endereco obterEnderecoPorID(int id) throws EnderecoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (!EnderecoCOL.idValido(id)) {
                throw new EnderecoException("ID inválido.");
            }
            return EnderecoDAO.selectEnderecoId(id, conn);
        }
    }

    public Endereco obterEnderecoExterno(String cep) throws EnderecoException, Exception {

        try {
            return CepAPI.getCEP(cep);
        } catch (Exception e) {
            throw new EnderecoException("Erro ao consultar API externa: " + e.getMessage());
        }
    }
    
    public Cidade obterCidade(int idCidade) throws EnderecoException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            // Valida ID
            if (idCidade <= 0) {
                throw new EnderecoException("ID de cidade inválido.");
            }
            
            Cidade cidade = CidadeDAO.selectCidade(idCidade, conn);
            
            if (cidade == null) {
                throw new EnderecoException("Cidade não encontrada para o ID: " + idCidade);
            }
            
            return cidade;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EnderecoException("Erro ao buscar cidade: " + e.getMessage());
        }
    }
}