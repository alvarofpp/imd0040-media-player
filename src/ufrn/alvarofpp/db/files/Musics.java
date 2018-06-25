
package ufrn.alvarofpp.db.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import ufrn.alvarofpp.db.models.Music;
import ufrn.alvarofpp.exceptions.FieldNotFoundException;
import ufrn.alvarofpp.exceptions.UserExistException;

public class Musics extends TableFile{
    
    private Vector<Music> musics;
    private int position = 0;
    
    public Musics(){
        this.filename = "musics.csv";
        this.path = getClass().getResource("../data/" + this.filename).getPath();
        this.musics = new Vector<>();
        readFile();
        
    }
    
    @Override
    public void readFile() {
        String line;
        String[] inputs;

        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            // Percorre o arquivo
            while ((line = br.readLine()) != null) {
                inputs = line.split(this.delimiter);
                this.musics.add(new Music(inputs[0], inputs[1]));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void create(String musicname, String path) throws FieldNotFoundException,UserExistException {
        // Verifica se um dos campos está vazio
        if ("".equals(musicname)) {
            throw new FieldNotFoundException("Music name");
        } else if ("".equals(path)) {
            throw new FieldNotFoundException("Path");
        }
        
        // Verifica se a música já existe
        if (this.existMusic(musicname)) {
            throw new UserExistException(musicname);
        }

        // Nova música
        Music music = new Music(musicname, path);

        try {
            // Salva a nova música
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path, true));
            writer.write(music.getName() + this.delimiter + music.getPath()+"\n");
            writer.close();

            // Atualiza a lista de músicas
            this.musics.add(music);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    private boolean existMusic(String musicname) {
        // Procura a música
        if (this.getMusic(musicname) != null) {
            return true;
        }
        return false;
    }
    public Music nextMusic(){
        if(position <= musics.size()){
            position++;
            return musics.elementAt(position);
        }
        return null;
    } 
    
    public Music backMusic(){
        if(position > 0){
            position--;
            return musics.elementAt(position);
        }
        return null;
    }
    
    public Music getMusic(String musicname) {
        // Procura a música
        for (Music music : this.musics) {
            if (music.getName().equals(musicname)) {
                return music;
            }
        }
        return null;
    }
}
