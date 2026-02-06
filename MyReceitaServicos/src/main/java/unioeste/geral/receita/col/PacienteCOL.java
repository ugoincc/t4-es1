package unioeste.geral.receita.col;

import java.sql.Connection;
import unioeste.geral.receita.bo.Paciente;
import unioeste.geral.receita.dao.PacienteDAO;

public class PacienteCOL {

    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;
        return cpf.matches("[0-9]+") && cpf.length() == 11;
    }

    public static boolean idValido(int id) {
        return id > 0;
    }

    public static boolean pacienteCadastrado(String cpf, Connection conexao) throws Exception {
        return PacienteDAO.selectPacienteByCPF(cpf, conexao) != null;
    }

    public static boolean pacienteValido(Paciente paciente) {
        if (paciente == null) return false;
        if (paciente.getIdPessoa() <= 0) return false;
        return true;
    }
}
