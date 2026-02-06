package unioeste.geral.os.col;

import java.sql.Connection;
import unioeste.geral.os.bo.Cliente;
import unioeste.geral.os.dao.ClienteDAO;

public class ClienteCOL {

    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;
        return cpf.matches("[0-9]+") && cpf.length() == 11;
    }

    public static boolean idValido(int id) {
        return id > 0;
    }

    public static boolean clienteCadastrado(String cpf, Connection conexao) throws Exception {
        return ClienteDAO.selectClienteByCPF(cpf, conexao) != null;
    }

    public static boolean clienteValido(Cliente cliente) {
        if (cliente == null) return false;
        if (cliente.getIdPessoa() <= 0) return false;
        return true;
    }
}
