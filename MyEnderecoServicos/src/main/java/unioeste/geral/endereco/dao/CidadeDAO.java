package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import unioeste.geral.endereco.bo.Cidade;

public class CidadeDAO {
	public static Cidade selectCidade(int id, Connection conexao) throws Exception{
		StringBuffer sql = new StringBuffer("SELECT cidade.nome_cidade, cidade.sigla_estado ");
		sql.append("FROM cidade WHERE cidade.id_cidade = ?;");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Cidade cidade = new Cidade();
			
			cidade.setIdCidade(id);
			cidade.setNome(result.getString("nome_cidade"));
			cidade.setUf(UnidadeFederacaoDAO.selectEstado(result.getString("sigla_estado"), conexao));
			
			return cidade;
		}
		
		return null;

	}
	
	public static List<Cidade> selectAllCidades(Connection connection) throws Exception {
	    List<Cidade> lista = new ArrayList<>();
	    String sql = "SELECT * FROM cidade";
	    
	    PreparedStatement ps = connection.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        Cidade cidade = new Cidade();

	        cidade.setIdCidade(rs.getInt("id_cidade"));
	        cidade.setNome(rs.getString("nome_cidade"));
	        cidade.setUf(UnidadeFederacaoDAO.selectEstado(rs.getString("sigla_estado"), connection));
	        lista.add(cidade);
	    }
	    rs.close();
	    ps.close();
	    
	    return lista;
	}
}
