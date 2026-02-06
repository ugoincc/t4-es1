package unioeste.geral.endereco.col;

import java.sql.Connection;
import unioeste.geral.endereco.bo.Logradouro;
import unioeste.geral.endereco.dao.LogradouroDAO;

public class LogradouroCOL {
    
    public static boolean idValido(int id) {
        return id > 0;
    }
    
    public static boolean logradouroValido(Logradouro logradouro) {
        if (logradouro == null) return false;
        
        // Se tem ID válido, aceita
        if (idValido(logradouro.getIdLogradouro())) return true;
        
        // Senão, exige nome e tipo
        if (logradouro.getNome() == null || logradouro.getNome().trim().isEmpty()) return false;
        if (!TipoLogradouroCOL.tipoLogradouroValido(logradouro.getTipo())) return false;
        
        return true;
    }
    
    public static boolean logradouroCadastrado(Logradouro logradouro, Connection conexao) throws Exception {
        Logradouro aux = LogradouroDAO.selectLogradouro(logradouro.getIdLogradouro(), conexao);
        return aux != null;
    }
}