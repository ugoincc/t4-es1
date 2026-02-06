package unioeste.geral.receita.col;

import java.sql.Connection;
import unioeste.geral.receita.bo.ReceitaMedica;
import unioeste.geral.receita.bo.Prescricao;

public class ReceitaMedicaCOL {

    public static boolean numeroReceitaValido(int numeroReceita) {
        return numeroReceita > 0;
    }

    public static boolean receitaMedicaValida(ReceitaMedica receita, Connection conexao) throws Exception {
        if (receita == null) {
            System.out.println("Receita medica nula");
            return false;
        }

        if (!PacienteCOL.pacienteValido(receita.getPaciente())) {
            System.out.println("Paciente invalido");
            return false;
        }

        if (receita.getPrescricoes() == null || receita.getPrescricoes().isEmpty()) {
            System.out.println("Receita sem prescricoes");
            return false;
        }

        for (Prescricao p : receita.getPrescricoes()) {
            if (!MedicamentoCOL.medicamentoCadastrado(p.getMedicamento().getIdMedicamento(), conexao)) {
                System.out.println("Medicamento nao cadastrado: " + p.getMedicamento().getIdMedicamento());
                return false;
            }
        }

        return true;
    }
}
