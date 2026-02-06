package unioeste.geral.endereco.col;

import java.sql.Connection;

import unioeste.geral.endereco.bo.UnidadeFederacao;
import unioeste.geral.endereco.dao.UnidadeFederacaoDAO;

public class UnidadeFederacaoCOL {
	public static boolean siglaValida(String sigla) {
		return sigla.matches("[A-Z]+") && sigla.length()==2; // Verificação se esta dentro do alfabeto valido e até 2 de carac.
	}
	
	public static boolean estadoValido(UnidadeFederacao uf) {
		if(uf==null) return false;
		if(!siglaValida(uf.getSigla())) return false;
		if(uf.getNome()==null) return false;
		
		return true;
	}
	
	public static boolean estadoCadastrado(UnidadeFederacao estado, Connection conexao) throws Exception {
		UnidadeFederacao aux = UnidadeFederacaoDAO.selectEstado(estado.getSigla(), conexao);
		if(aux==null) return false;
		return true;
	}
}
