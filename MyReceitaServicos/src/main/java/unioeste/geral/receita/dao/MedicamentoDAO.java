package unioeste.geral.receita.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import unioeste.geral.receita.bo.Medicamento;

public class MedicamentoDAO {

    public static List<Medicamento> selectAllMedicamentos(Connection conexao) throws Exception {
        List<Medicamento> medicamentos = new ArrayList<>();

        StringBuffer sql = new StringBuffer("SELECT id_medicamento, nome_generico, fabricante FROM medicamento ORDER BY nome_generico");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        ResultSet result = cmd.executeQuery();

        while (result.next()) {
            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento(result.getInt("id_medicamento"));
            medicamento.setNomeGenerico(result.getString("nome_generico"));
            medicamento.setFabricante(result.getString("fabricante"));
            medicamentos.add(medicamento);
        }

        return medicamentos;
    }

    public static List<Medicamento> selectMedicamentosByNome(String nome, Connection conexao) throws Exception {
        List<Medicamento> medicamentos = new ArrayList<>();

        StringBuffer sql = new StringBuffer("SELECT id_medicamento, nome_generico, fabricante FROM medicamento ");
        sql.append("WHERE LOWER(nome_generico) LIKE LOWER(?) ORDER BY nome_generico");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setString(1, "%" + nome + "%");
        ResultSet result = cmd.executeQuery();

        while (result.next()) {
            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento(result.getInt("id_medicamento"));
            medicamento.setNomeGenerico(result.getString("nome_generico"));
            medicamento.setFabricante(result.getString("fabricante"));
            medicamentos.add(medicamento);
        }

        return medicamentos;
    }

    public static Medicamento selectMedicamentoById(int id, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT id_medicamento, nome_generico, fabricante FROM medicamento WHERE id_medicamento = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, id);
        ResultSet result = cmd.executeQuery();

        if (result.next()) {
            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento(result.getInt("id_medicamento"));
            medicamento.setNomeGenerico(result.getString("nome_generico"));
            medicamento.setFabricante(result.getString("fabricante"));
            return medicamento;
        }

        return null;
    }
}
