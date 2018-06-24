package ufrn.alvarofpp.exceptions;

/**
 * Essa exceção é ativada quando um fos campos do formulário está vazio.
 */
public class FieldNotFoundException extends Exception {

    public FieldNotFoundException(String field) {
        super("O campo " + field + " está vazio!");
    }
}
