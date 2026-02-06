package unioeste.geral.endereco.col;

import java.sql.Connection;
import unioeste.geral.endereco.bo.Cidade;
import unioeste.geral.endereco.dao.CidadeDAO;

public class CidadeCOL {
    
    public static boolean idValido(int id) {
        return id > 0;
    }
    
    public static boolean cidadeValida(Cidade cidade) {
        if (cidade == null) return false;
        
        // SE TEM ID VÁLIDO, consideramos válido (é uma referência)
        if (idValido(cidade.getIdCidade())) return true;
        
        // Se NÃO tem ID, aí sim exigimos os outros campos (caso de nova cidade)
        if (cidade.getNome() == null || cidade.getNome().trim().isEmpty()) return false;
        if (!UnidadeFederacaoCOL.estadoValido(cidade.getUf())) return false;
        
        return true;
    }
    
    public static boolean cidadeCadastrada(Cidade cidade, Connection conexao) throws Exception {
        // Busca no banco pelo ID
        Cidade aux = CidadeDAO.selectCidade(cidade.getIdCidade(), conexao);
        if (aux == null) return false; // Não existe no banco
        
        // Validação extra apenas se o objeto de entrada também tiver UF preenchido
        if (cidade.getUf() != null && cidade.getUf().getSigla() != null) {
             if (!aux.getUf().getSigla().equals(cidade.getUf().getSigla())) return false;
        }
        
        return true;
    }
}