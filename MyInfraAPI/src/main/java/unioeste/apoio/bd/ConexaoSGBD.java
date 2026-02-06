package unioeste.apoio.bd;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConexaoSGBD {

    private static final String JNDI_NAME = "java:comp/env/jdbc/T3ES1DataSource";

    public static Connection getConnection() throws Exception {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(JNDI_NAME);
            return ds.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro ao obter conexão do pool: " + e.getMessage());
        }
    }
}