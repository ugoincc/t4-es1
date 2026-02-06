package unioeste.geral.receita.col;

import java.sql.Connection;
import unioeste.geral.receita.bo.CID;
import unioeste.geral.receita.dao.CIDDAO;

public class CIDCOL {

    public static boolean codigoValido(String codigo) {
        if (codigo == null || codigo.isEmpty()) return false;
        return codigo.matches("[A-Z][0-9]{2}(\\.[0-9]+)?");
    }

    public static boolean cidCadastrado(String codigo, Connection conexao) throws Exception {
        return CIDDAO.selectCIDByCodigo(codigo, conexao) != null;
    }

    public static boolean cidValido(CID cid) {
        if (cid == null) return false;
        if (cid.getCodigo() == null || cid.getCodigo().isEmpty()) return false;
        return true;
    }
}
