package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import unioeste.geral.endereco.bo.Endereco;

public class EnderecoDAO {

	public static Endereco selectEnderecoId (int id, Connection conexao) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT endereco.CEP, endereco.id_cidade, ");
		sql.append("endereco.id_bairro, endereco.id_logradouro FROM endereco ");
		sql.append("WHERE endereco.id_endereco = ?;");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Endereco endereco = new Endereco();
			endereco.setIdEndereco(id);
			endereco.setCidade(CidadeDAO.selectCidade(result.getInt("id_cidade"), conexao));
			endereco.setBairro(BairroDAO.selectBairro(result.getInt("id_bairro"), conexao));
			endereco.setLogradouro(LogradouroDAO.selectLogradouro(result.getInt("id_logradouro"), conexao));
			endereco.setCep(result.getString("CEP"));
			
			return endereco;
		}
		
		return null;
	}
	
	public static Endereco selectEnderecoCEP (String CEP, Connection conexao) throws Exception {
		
		StringBuffer sql = new StringBuffer("SELECT endereco.id_endereco, endereco.id_cidade, ");
		sql.append("endereco.id_bairro, endereco.id_logradouro FROM endereco ");
		sql.append("WHERE endereco.CEP = ?");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setString(1, CEP);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Endereco endereco = new Endereco();
			endereco.setIdEndereco(result.getInt("id_endereco"));
			endereco.setCidade(CidadeDAO.selectCidade(result.getInt("id_cidade"), conexao));
			endereco.setBairro(BairroDAO.selectBairro(result.getInt("id_bairro"), conexao));
			endereco.setLogradouro(LogradouroDAO.selectLogradouro(result.getInt("id_logradouro"), conexao));
			endereco.setCep(CEP);
			
			return endereco;
		}
		
		return null;
	}
	
	public static void insertEndereco(Endereco endereco, Connection conexao) throws Exception {
	    
	    StringBuffer sql = new StringBuffer("INSERT INTO endereco (CEP, id_cidade, id_bairro, id_logradouro) VALUES (?, ?, ?, ?);");
	    System.out.println("SQL a ser executada: " + sql.toString());  
	    

	    System.out.println("CEP: " + endereco.getCep());
	    
	    if (endereco.getCidade() == null) {
	        System.out.println("ERRO: Cidade está nula!");
	    } else {
	        System.out.println("Cidade id: " + endereco.getCidade().getIdCidade());
	    }
	    
	    if (endereco.getBairro() == null) {
	        System.out.println("ERRO: Bairro está nulo!");
	    } else {
	        System.out.println("Bairro id: " + endereco.getBairro().getIdBairro());
	    }
	    
	    if (endereco.getLogradouro() == null) {
	        System.out.println("ERRO: Logradouro está nulo!");
	    } else {
	        System.out.println("Logradouro id: " + endereco.getLogradouro().getIdLogradouro());
	    }

	    PreparedStatement cmd = conexao.prepareStatement(sql.toString());
	    cmd.setString(1, endereco.getCep());
	    cmd.setInt(2, endereco.getCidade().getIdCidade());
	    cmd.setInt(3, endereco.getBairro().getIdBairro());
	    cmd.setInt(4, endereco.getLogradouro().getIdLogradouro());
	    
	    int affectedRows = cmd.executeUpdate();
	    System.out.println("Número de linhas afetadas: " + affectedRows);
	}

}
