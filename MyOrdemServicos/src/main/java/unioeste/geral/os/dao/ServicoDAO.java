package unioeste.geral.os.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import unioeste.geral.os.bo.Servico;

public class ServicoDAO {

    public static List<Servico> selectAllServicos(Connection conexao) throws Exception {
        List<Servico> servicos = new ArrayList<>();

        StringBuffer sql = new StringBuffer("SELECT cod_servico, tipo_servico, valor FROM servico ORDER BY tipo_servico");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        ResultSet result = cmd.executeQuery();

        while (result.next()) {
            Servico servico = new Servico();
            servico.setCod(result.getInt("cod_servico"));
            servico.setTipoServico(result.getString("tipo_servico"));
            servico.setValor(result.getDouble("valor"));
            servicos.add(servico);
        }

        return servicos;
    }

    public static Servico selectServicoByCod(int cod, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT cod_servico, tipo_servico, valor FROM servico WHERE cod_servico = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, cod);
        ResultSet result = cmd.executeQuery();

        if (result.next()) {
            Servico servico = new Servico();
            servico.setCod(result.getInt("cod_servico"));
            servico.setTipoServico(result.getString("tipo_servico"));
            servico.setValor(result.getDouble("valor"));
            return servico;
        }

        return null;
    }
}
