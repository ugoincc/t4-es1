package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import unioeste.geral.endereco.bo.Logradouro;

public class LogradouroDAO {
	public static Logradouro selectLogradouro(int id, Connection conexao) throws Exception{
		StringBuffer sql = new StringBuffer("SELECT logradouro.nome_logradouro, logradouro.id_tipo_logradouro ");
		sql.append("FROM logradouro WHERE logradouro.id_logradouro =").append(id).append(";");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());

		ResultSet result = cmd.executeQuery();
		
		
		if(result.next()) {
			Logradouro logradouro = new Logradouro();
			
			logradouro.setNome(result.getString("nome_logradouro"));
			logradouro.setIdLogradouro(id);
			logradouro.setTipo(TipoLogradouroDAO.selectTipoLogradouro(result.getInt("id_tipo_logradouro"), conexao));
			
			return logradouro;
		}
		
		return null;

	}
	
	public static List<Logradouro> selectAllLogradouros(Connection connection) throws Exception {
	    List<Logradouro> lista = new ArrayList<>();
	    String sql = "SELECT * FROM logradouro";
	    
	    PreparedStatement ps = connection.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        Logradouro log = new Logradouro();

	        log.setIdLogradouro(rs.getInt("id_logradouro"));
	        log.setNome(rs.getString("nome_logradouro"));
	       
	        lista.add(log);
	    }
	    rs.close();
	    ps.close();
	    
	    return lista;
	}
}
