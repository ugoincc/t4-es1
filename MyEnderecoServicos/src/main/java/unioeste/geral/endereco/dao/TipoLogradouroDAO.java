package unioeste.geral.endereco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import unioeste.geral.endereco.bo.TipoLogradouro;
public class TipoLogradouroDAO {
	public static TipoLogradouro selectTipoLogradouro(int id, Connection conexao) throws Exception{

		StringBuffer sql = new StringBuffer("SELECT tipo_logradouro.descricao ");
		sql.append("FROM tipo_logradouro WHERE tipo_logradouro.id_tipo_logradouro =").append(id).append(";");
		PreparedStatement cmd = conexao.prepareStatement(sql.toString());

		ResultSet result = cmd.executeQuery();

		if(result.next()) {
			TipoLogradouro tipo_logradouro = new TipoLogradouro();

			tipo_logradouro.setNome(result.getString("descricao"));
			tipo_logradouro.setCod(id);

			return tipo_logradouro;
		}

		return null;

	}
}
