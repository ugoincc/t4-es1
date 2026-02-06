package unioeste.geral.receita.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import unioeste.geral.receita.bo.CID;

public class CIDDAO {

    public static CID selectCIDByCodigo(String codigo, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT codigo, descricao FROM cid WHERE codigo = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setString(1, codigo);
        ResultSet result = cmd.executeQuery();

        if (result.next()) {
            CID cid = new CID();
            cid.setCodigo(result.getString("codigo"));
            cid.setDescricao(result.getString("descricao"));
            return cid;
        }

        return null;
    }

    public static CID selectCIDByCodigoLike(String codigo, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT codigo, descricao FROM cid WHERE codigo LIKE ? LIMIT 1");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setString(1, codigo + "%");
        ResultSet result = cmd.executeQuery();

        if (result.next()) {
            CID cid = new CID();
            cid.setCodigo(result.getString("codigo"));
            cid.setDescricao(result.getString("descricao"));
            return cid;
        }

        return null;
    }
}
