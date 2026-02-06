package unioeste.geral.endereco.col;

import java.sql.Connection;
import unioeste.geral.endereco.bo.Bairro;
import unioeste.geral.endereco.dao.BairroDAO;

public class BairroCOL {
    
    public static boolean idValido(int id) {
        return id > 0;
    }
    
    public static boolean bairroValido(Bairro bairro) {
        if (bairro == null) return false;
        
        // Se tem ID válido, aceita
        if (idValido(bairro.getIdBairro())) return true;
        
        // Senão, exige nome
        if (bairro.getNome() == null || bairro.getNome().trim().isEmpty()) return false;
        
        return true;
    }
    
    public static boolean bairroCadastrado(Bairro bairro, Connection conexao) throws Exception {
        Bairro aux = BairroDAO.selectBairro(bairro.getIdBairro(), conexao);
        return aux != null;
    }
}