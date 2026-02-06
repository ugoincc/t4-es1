package unioeste.geral.endereco.col;

import java.sql.Connection;

import unioeste.geral.endereco.bo.TipoLogradouro;
import unioeste.geral.endereco.dao.TipoLogradouroDAO;

public class TipoLogradouroCOL {
	public static boolean idValido(int id) {
		return id>0;
	}
	
	public static boolean tipoLogradouroValido(TipoLogradouro tipo_logradouro) {
		if(tipo_logradouro==null) return false;
		if(!idValido(tipo_logradouro.getCod())) return false;
		if(tipo_logradouro.getNome()==null) return false;

		return true;
	}
	
	public static boolean tipoLogradouroCadastrado(TipoLogradouro tipo_logradouro, Connection conexao) throws Exception {
		TipoLogradouro aux = TipoLogradouroDAO.selectTipoLogradouro(tipo_logradouro.getCod(), conexao);
		if(aux==null) return false;
		return true;
	}
}
