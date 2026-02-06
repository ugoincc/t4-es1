package unioeste.geral.endereco.col;

import java.sql.Connection;

import unioeste.geral.endereco.bo.Endereco;
import unioeste.geral.endereco.dao.EnderecoDAO;


public class EnderecoCOL {
	public static boolean idValido(int id) {
		return id>0;
	}
	
	public static boolean EnderecoCadastrado(Endereco endereco, Connection conexao) throws Exception {
		if(EnderecoDAO.selectEnderecoCEP(endereco.getCep(), conexao)!=null) return true;
		return false;
	}
	
	public static boolean CEPValido(String CEP) {
		if(CEP==null) return false;
		return CEP.matches("[0-9]+") && CEP.length() ==8;
	}
	
	public static boolean EnderecoValido(Endereco endereco, Connection conexao) throws Exception {
        if (endereco == null) {
            System.out.println("Endereço é nulo");
            return false;
        }
        System.out.println("Validando endereço: " + endereco);

        if (!CEPValido(endereco.getCep())) {
            System.out.println("CEP inválido: " + endereco.getCep());
            return false;
        }

        if (!CidadeCOL.cidadeValida(endereco.getCidade())) {
            System.out.println("Cidade inválida: " + endereco.getCidade());
            return false;
        }
        if (!CidadeCOL.cidadeCadastrada(endereco.getCidade(), conexao)) {
            System.out.println("Cidade não cadastrada: " + endereco.getCidade());
            return false;
        }

        if (!BairroCOL.bairroValido(endereco.getBairro())) {
            System.out.println("Bairro inválido: " + endereco.getBairro());
            return false;
        }
        if (!BairroCOL.bairroCadastrado(endereco.getBairro(), conexao)) {
            System.out.println("Bairro não cadastrado: " + endereco.getBairro());
            return false;
        }

        if (!LogradouroCOL.logradouroValido(endereco.getLogradouro())) {
            System.out.println("Logradouro inválido: " + endereco.getLogradouro());
            return false;
        }
        if (!LogradouroCOL.logradouroCadastrado(endereco.getLogradouro(), conexao)) {
            System.out.println("Logradouro não cadastrado: " + endereco.getLogradouro());
            return false;
        }

        System.out.println("Endereço validado com sucesso!");
        return true;
    }
}
