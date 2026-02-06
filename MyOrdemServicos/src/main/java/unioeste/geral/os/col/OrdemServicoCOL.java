package unioeste.geral.os.col;

import java.sql.Connection;
import unioeste.geral.os.bo.OrdemServico;
import unioeste.geral.os.bo.Servico;

public class OrdemServicoCOL {

    public static boolean nroOrdemValido(int nroOrdem) {
        return nroOrdem > 0;
    }

    public static boolean ordemServicoValida(OrdemServico ordem, Connection conexao) throws Exception {
        if (ordem == null) {
            System.out.println("Ordem de servico nula");
            return false;
        }

        if (!ClienteCOL.clienteValido(ordem.getCliente())) {
            System.out.println("Cliente invalido");
            return false;
        }

        if (ordem.getServicos() == null || ordem.getServicos().isEmpty()) {
            System.out.println("Ordem sem servicos");
            return false;
        }

        for (Servico s : ordem.getServicos()) {
            if (!ServicoCOL.servicoCadastrado(s.getCod(), conexao)) {
                System.out.println("Servico nao cadastrado: " + s.getCod());
                return false;
            }
        }

        return true;
    }
}
