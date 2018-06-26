package ufrn.alvarofpp.db.models;

import java.util.Vector;

/**
 * Representa uma playlist no sistema, seja geral ou não
 */
public class Playlist {
    /**
     * Vetor de músicas
     */
    private Vector<Music> musics;
    /**
     * Posição inicial
     */
    private int position = -1;

    /**
     * Monta a playlist já formada
     * @param musics Músicas da playlist
     */
    public Playlist(Vector<Music> musics) {
        this.musics = musics;
    }

    /**
     * Avança na playlist
     *
     * @return A próxima música
     */
    public Music next() {
        if (position < musics.size() - 1) {
            return musics.elementAt(++position);
        }

        this.position = 0;
        return this.musics.firstElement();
    }

    /**
     * Volta na playlist
     *
     * @return A música anterior
     */
    public Music back() {
        if (position > 0) {
            return musics.elementAt(--position);
        }

        this.position = this.musics.size()-1;
        return this.musics.lastElement();
    }

    /**
     * Pega o objeto de Musica pelo nome
     * @param name
     * @return
     */
    public Music getMusic(String name) {
        for (Music music : this.musics) {
            if (music.getName().equals(name)) {
                return music;
            }
        }

        return null;
    }

    /**
     * Retorna a música atual
     * @return
     */
    public Music getActual() {
        return this.musics.elementAt(this.position);
    }

    public Vector<Music> getMusics() {
        return musics;
    }

    public void setMusics(Vector<Music> musics) {
        this.musics = musics;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
