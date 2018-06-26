package ufrn.alvarofpp.db.models;

import ufrn.alvarofpp.db.helpers.MD5;

import java.security.NoSuchAlgorithmException;

/**
 * Representa uma música salva no sistema
 */
public class Music {
    /**
     * Nome do usuário que a música é vinculado
     */
    private String username;
    /**
     * Nome da musica
     */
    private String name;
    /**
     * Caminho da musica
     */
    private String path;
    /**
     * Identificador da música
     */
    private String id;

    /**
     * Músicas que serão registradas
     *
     * @param username Nome do usuário que a detêm
     * @param name     Nome da música
     * @param path     Caminho da música
     */
    public Music(String username, String name, String path) {
        this.username = username;
        this.name = name;
        this.path = path;
        // Cria identificador
        try {
            this.id = MD5.hash(username + this.getFullPath());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Músicas já registradas
     *
     * @param username Nome do usuário que a detêm
     * @param name     Nome da música
     * @param path     Caminho da música
     * @param id       Identificador
     */
    public Music(String username, String name, String path, String id) {
        this.username = username;
        this.name = name;
        this.path = path;
        this.id = id;
    }

    /**
     * Pega o caminho completo da música
     * @return Caminho/nome_da_música
     */
    public String getFullPath() {
        return this.path + '/' + this.name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
