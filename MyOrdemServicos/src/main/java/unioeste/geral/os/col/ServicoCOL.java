package unioeste.geral.os.col;

import java.sql.Connection;
import unioeste.geral.os.bo.Servico;
import unioeste.geral.os.dao.ServicoDAO;

public class ServicoCOL {

    public static boolean codValido(int cod) {
        return cod > 0;
    }

    public static boolean servicoCadastrado(int cod, Connection conexao) throws Exception {
        return ServicoDAO.selectServicoByCod(cod, conexao) != null;
    }

    public static boolean servicoValido(Servico servico) {
        if (servico == null) return false;
        if (servico.getCod() <= 0) return false;
        return true;
    }
}
