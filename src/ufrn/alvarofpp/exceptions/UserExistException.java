package ufrn.alvarofpp.exceptions;

/**
 * Essa exceção é ativada quando o usuário que está tentando ser registrado já existe.
 */
public class UserExistException extends Exception {

    public UserExistException(String username) {
        super("O usuário " + username + " já existe!");
    }
}
