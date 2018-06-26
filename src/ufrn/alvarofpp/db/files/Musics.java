package ufrn.alvarofpp.db.files;

import java.util.Vector;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import ufrn.alvarofpp.db.helpers.MD5;
import ufrn.alvarofpp.db.models.Music;

import java.security.NoSuchAlgorithmException;

import ufrn.alvarofpp.exceptions.UserExistException;
import ufrn.alvarofpp.exceptions.FieldNotFoundException;

/**
 * Classe para leitura de arquivos de dados das músicas.
 * Filtra para as músicas do usuário logado.
 */
public class Musics extends TableFile {
    /**
     * Vetor de músicas
     */
    private Vector<Music> musics;
    /**
     * Username do usuário logado
     */
    private String username;

    public Musics(String username) {
        this.username = username;
        this.filename = "musics.csv";
        this.path = getClass().getResource("../data/" + this.filename).getPath();
        this.musics = new Vector<>();
        readFile();
    }

    /**
     * Ler e salva os dados de todas as músicas.
     */
    @Override
    public void readFile() {
        String line;
        String[] inputs;

        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            // Percorre o arquivo
            while ((line = br.readLine()) != null) {
                inputs = line.split(this.delimiter);
                // Verifica se a música é do usuário
                if (inputs[0].equals(this.username)) {
                    this.musics.add(new Music(inputs[0], inputs[1], inputs[2], inputs[3]));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cria uma nova instância e atualiza no arquivo
     *
     * @param musicname Nome da música
     * @param path      Caminho da música
     */
    public void create(String musicname, String path) throws FieldNotFoundException, UserExistException {
        // Verifica se um dos campos está vazio
        if ("".equals(musicname)) {
            throw new FieldNotFoundException("Music name");
        } else if ("".equals(path)) {
            throw new FieldNotFoundException("Path");
        }

        // Verifica se a música já existe
        if (this.exist(musicname, path)) {
            throw new UserExistException(musicname);
        }

        // Nova música
        Music music = new Music(this.username, musicname, path);

        try {
            // Salva a nova música
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path, true));
            writer.write(music.getName() + this.delimiter + music.getPath() + "\n");
            writer.close();

            // Atualiza a lista de músicas
            this.musics.add(music);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se a música existe
     *
     * @param musicname Nome da música
     * @param path      Caminho da música
     * @return Verdadeiro se a música existir, falso caso contrário
     */
    private boolean exist(String musicname, String path) {
        // Entrada do algoritmo hash md5
        String inputHash = this.username + path + '/' + musicname;
        // Tenta pegar a música na lista de músicas
        try {
            return this.getMusic(MD5.hash(inputHash)) != null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @return A lista de músicas
     */
    public Vector<Music> getMusics() {
        return musics;
    }

    /**
     * Pega o objeto referente a música desejada.
     *
     * @param id Identificador da música
     * @return O objeto referente a música em questão ou null caso contrário
     */
    public Music getMusic(String id) {
        // Procura a música
        for (Music music : this.musics) {
            if (music.getId().equals(id)) {
                return music;
            }
        }

        return null;
    }
}
