package ufrn.alvarofpp.exceptions;

public class UserExistException extends Exception {

    public UserExistException(String username) {
        super("O usuário " + username + " já existe!");
    }
}
