package unioeste.geral.receita.col;

import java.sql.Connection;
import unioeste.geral.receita.bo.Medicamento;
import unioeste.geral.receita.dao.MedicamentoDAO;

public class MedicamentoCOL {

    public static boolean idValido(int id) {
        return id > 0;
    }

    public static boolean medicamentoCadastrado(int id, Connection conexao) throws Exception {
        return MedicamentoDAO.selectMedicamentoById(id, conexao) != null;
    }

    public static boolean medicamentoValido(Medicamento medicamento) {
        if (medicamento == null) return false;
        if (medicamento.getIdMedicamento() <= 0) return false;
        return true;
    }
}
