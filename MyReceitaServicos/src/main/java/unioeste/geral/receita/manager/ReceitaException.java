package unioeste.geral.receita.manager;

public class ReceitaException extends Exception {
    private static final long serialVersionUID = 1L;

    public ReceitaException(String errorMessage) {
        super(errorMessage);
    }
}
