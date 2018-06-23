package ufrn.alvarofpp.exceptions;

public class FieldNotFoundException extends Exception {

    public FieldNotFoundException(String field) {
        super("O campo " + field + " está vazio!");
    }
}
