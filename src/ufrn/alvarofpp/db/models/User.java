package ufrn.alvarofpp.db.models;

/**
 * Representa o usuário do sistema
 */
public class User {
    /**
     * Nome do usuário
     */
    private String username;
    /**
     * Senha do usuário
     */
    private String password;
    /**
     * Senha do usuário
     */
    private int type;

    /**
     * Usuários cadastrados
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.type = 2;
    }

    /**
     * Usuários admin
     * @param username
     * @param password
     */
    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = Integer.parseInt(type);
    }

    public boolean isAdmin() {
        return this.type==1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
