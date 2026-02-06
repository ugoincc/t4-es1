package unioeste.geral.os.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import unioeste.geral.os.bo.OrdemServico;
import unioeste.geral.os.bo.Servico;

public class OrdemServicoDAO {

    public static List<OrdemServico> selectAllOrdensServico(Connection conexao) throws Exception {
        List<OrdemServico> ordens = new ArrayList<>();

        StringBuffer sql = new StringBuffer("SELECT nro_ordem, descricao, data_emissao, total, id_cliente, id_atendente ");
        sql.append("FROM ordem_servico ORDER BY nro_ordem DESC");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        ResultSet result = cmd.executeQuery();

        while (result.next()) {
            OrdemServico ordem = new OrdemServico();
            ordem.setNroOrdem(result.getInt("nro_ordem"));
            ordem.setDescricao(result.getString("descricao"));
            ordem.setDataEmissao(result.getTimestamp("data_emissao"));
            ordem.setTotal(result.getDouble("total"));

            ordem.setCliente(ClienteDAO.selectClienteById(result.getInt("id_cliente"), conexao));

            int idAtendente = result.getInt("id_atendente");
            if (idAtendente > 0) {
                ordem.setAtendente(AtendenteDAO.selectAtendenteById(idAtendente, conexao));
            }

            ordem.setServicos(selectServicosByOrdem(ordem.getNroOrdem(), conexao));

            ordens.add(ordem);
        }

        return ordens;
    }

    public static int insertOrdemServico(OrdemServico ordem, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("INSERT INTO ordem_servico (descricao, data_emissao, total, id_cliente, id_atendente) ");
        sql.append("VALUES (?, ?, ?, ?, ?) RETURNING nro_ordem");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setString(1, ordem.getDescricao());
        cmd.setTimestamp(2, new Timestamp(ordem.getDataEmissao().getTime()));
        cmd.setDouble(3, ordem.getTotal());
        cmd.setInt(4, ordem.getCliente().getIdPessoa());
        if (ordem.getAtendente() != null && ordem.getAtendente().getIdPessoa() > 0) {
            cmd.setInt(5, ordem.getAtendente().getIdPessoa());
        } else {
            cmd.setNull(5, java.sql.Types.INTEGER);
        }

        ResultSet rs = cmd.executeQuery();
        if (rs.next()) {
            return rs.getInt("nro_ordem");
        }
        return -1;
    }

    public static void insertOrdemServicoServico(int nroOrdem, int codServico, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("INSERT INTO ordem_servico_servico (nro_ordem, cod_servico) VALUES (?, ?)");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, nroOrdem);
        cmd.setInt(2, codServico);
        cmd.executeUpdate();
    }

    public static OrdemServico selectOrdemServicoByNro(int nroOrdem, Connection conexao) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT nro_ordem, descricao, data_emissao, total, id_cliente, id_atendente ");
        sql.append("FROM ordem_servico WHERE nro_ordem = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, nroOrdem);
        ResultSet result = cmd.executeQuery();

        if (result.next()) {
            OrdemServico ordem = new OrdemServico();
            ordem.setNroOrdem(result.getInt("nro_ordem"));
            ordem.setDescricao(result.getString("descricao"));
            ordem.setDataEmissao(result.getTimestamp("data_emissao"));
            ordem.setTotal(result.getDouble("total"));

            ordem.setCliente(ClienteDAO.selectClienteById(result.getInt("id_cliente"), conexao));

            int idAtendente = result.getInt("id_atendente");
            if (idAtendente > 0) {
                ordem.setAtendente(AtendenteDAO.selectAtendenteById(idAtendente, conexao));
            }

            ordem.setServicos(selectServicosByOrdem(nroOrdem, conexao));

            return ordem;
        }

        return null;
    }

    public static List<Servico> selectServicosByOrdem(int nroOrdem, Connection conexao) throws Exception {
        List<Servico> servicos = new ArrayList<>();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT s.cod_servico, s.tipo_servico, s.valor ");
        sql.append("FROM ordem_servico_servico oss ");
        sql.append("JOIN servico s ON oss.cod_servico = s.cod_servico ");
        sql.append("WHERE oss.nro_ordem = ?");

        PreparedStatement cmd = conexao.prepareStatement(sql.toString());
        cmd.setInt(1, nroOrdem);
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            Servico servico = new Servico();
            servico.setCod(rs.getInt("cod_servico"));
            servico.setTipoServico(rs.getString("tipo_servico"));
            servico.setValor(rs.getDouble("valor"));
            servicos.add(servico);
        }

        return servicos;
    }
}
