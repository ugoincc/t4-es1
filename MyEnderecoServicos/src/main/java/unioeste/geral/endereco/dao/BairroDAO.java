package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import unioeste.geral.endereco.bo.Bairro;
import unioeste.geral.endereco.bo.Cidade;

public class BairroDAO {
	public static Bairro selectBairro(int id, Connection conexao) throws Exception{
		
		StringBuffer sql = new StringBuffer("SELECT bairro.nome_bairro ");
		sql.append("FROM bairro WHERE bairro.id_bairro = ?;");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());
		cmd.setInt(1, id);
		ResultSet result = cmd.executeQuery();
		
		if(result.next()) {
			Bairro bairro = new Bairro();
			
			bairro.setNome(result.getString("nome_bairro"));
			bairro.setIdBairro(id);
			
			return bairro;
		}
		
		return null;

	}
	
	public static List<Bairro> selectTodosBairros(Connection connection) throws Exception {
		List<Bairro> bairros = new ArrayList<>();
        String sql = "SELECT * FROM bairro;";
        
        PreparedStatement ps = connection.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            Bairro bairro = new Bairro();
            bairro.setIdBairro(rs.getInt("id_bairro"));
            bairro.setNome(rs.getString("nome_bairro"));
            bairros.add(bairro);
        }
        
        rs.close();
	    ps.close();
	    
        return bairros;
    }
}
