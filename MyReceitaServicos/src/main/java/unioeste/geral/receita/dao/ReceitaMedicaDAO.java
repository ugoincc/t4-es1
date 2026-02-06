package unioeste.geral.receita.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import unioeste.geral.receita.bo.ReceitaMedica;
import unioeste.geral.receita.bo.Prescricao;
import unioeste.geral.receita.bo.Medicamento;

public class ReceitaMedicaDAO {

    public static List<ReceitaMedica> selectAllReceitasMedicas(Connection conexao) throws Exception {
        List<ReceitaMedica> receitas = new ArrayList<>();

        StringBuffer sql = new StringBuffer("SELECT numero_receita, data_emissao, id_medico, id_paciente, codigo_cid ");
        sql.append("FROM receita_medica ORDER BY numero_receita DESC");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        ResultSet result = cmd.executeQuery();

        while (result.next()) {
            ReceitaMedica receita = new ReceitaMedica();
            receita.setNumeroReceita(result.getInt("numero_receita"));
            receita.setDataEmissao(result.getTimestamp("data_emissao"));

            int idMedico = result.getInt("id_medico");
            if (idMedico > 0) {
                receita.setMedico(MedicoDAO.selectMedicoById(idMedico, conexao));
            }

            receita.setPaciente(PacienteDAO.selectPacienteById(result.getInt("id_paciente"), conexao));

            String codigoCid = result.getString("codigo_cid");
            if (codigoCid != null) {
                receita.setCid(CIDDAO.selectCIDByCodigo(codigoCid, conexao));
            }

            receita.setPrescricoes(selectPrescricoesByReceita(receita.getNumeroReceita(), conexao));

            receitas.add(receita);
        }

        return receitas;
    }

    public static int insertReceitaMedica(ReceitaMedica receita, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("INSERT INTO receita_medica (data_emissao, id_medico, id_paciente, codigo_cid) ");
        sql.append("VALUES (?, ?, ?, ?) RETURNING numero_receita");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setTimestamp(1, new Timestamp(receita.getDataEmissao().getTime()));
        if (receita.getMedico() != null && receita.getMedico().getIdPessoa() > 0) {
            cmd.setInt(2, receita.getMedico().getIdPessoa());
        } else {
            cmd.setNull(2, java.sql.Types.INTEGER);
        }
        cmd.setInt(3, receita.getPaciente().getIdPessoa());
        cmd.setString(4, receita.getCid() != null ? receita.getCid().getCodigo() : null);

        ResultSet rs = cmd.executeQuery();
        if (rs.next()) {
            return rs.getInt("numero_receita");
        }
        return -1;
    }

    public static void insertPrescricao(int numeroReceita, Prescricao prescricao, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("INSERT INTO prescricao (numero_receita, id_medicamento, posologia, periodo_uso) ");
        sql.append("VALUES (?, ?, ?, ?)");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, numeroReceita);
        cmd.setInt(2, prescricao.getMedicamento().getIdMedicamento());
        cmd.setString(3, prescricao.getPosologia());
        cmd.setString(4, prescricao.getPeriodoUso());
        cmd.executeUpdate();
    }

    public static ReceitaMedica selectReceitaMedicaByNumero(int numeroReceita, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT numero_receita, data_emissao, id_medico, id_paciente, codigo_cid ");
        sql.append("FROM receita_medica WHERE numero_receita = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, numeroReceita);
        ResultSet result = cmd.executeQuery();

        if (result.next()) {
            ReceitaMedica receita = new ReceitaMedica();
            receita.setNumeroReceita(result.getInt("numero_receita"));
            receita.setDataEmissao(result.getTimestamp("data_emissao"));

            int idMedico = result.getInt("id_medico");
            if (idMedico > 0) {
                receita.setMedico(MedicoDAO.selectMedicoById(idMedico, conexao));
            }

            receita.setPaciente(PacienteDAO.selectPacienteById(result.getInt("id_paciente"), conexao));

            String codigoCid = result.getString("codigo_cid");
            if (codigoCid != null) {
                receita.setCid(CIDDAO.selectCIDByCodigo(codigoCid, conexao));
            }

            receita.setPrescricoes(selectPrescricoesByReceita(numeroReceita, conexao));

            return receita;
        }

        return null;
    }

    public static List<Prescricao> selectPrescricoesByReceita(int numeroReceita, Connection conexao) throws Exception {
        List<Prescricao> prescricoes = new ArrayList<>();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT p.id_prescricao, p.posologia, p.periodo_uso, ");
        sql.append("m.id_medicamento, m.nome_generico, m.fabricante ");
        sql.append("FROM prescricao p ");
        sql.append("JOIN medicamento m ON p.id_medicamento = m.id_medicamento ");
        sql.append("WHERE p.numero_receita = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, numeroReceita);
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            Prescricao prescricao = new Prescricao();
            prescricao.setPosologia(rs.getString("posologia"));
            prescricao.setPeriodoUso(rs.getString("periodo_uso"));

            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento(rs.getInt("id_medicamento"));
            medicamento.setNomeGenerico(rs.getString("nome_generico"));
            medicamento.setFabricante(rs.getString("fabricante"));
            prescricao.setMedicamento(medicamento);

            prescricoes.add(prescricao);
        }

        return prescricoes;
    }
}
