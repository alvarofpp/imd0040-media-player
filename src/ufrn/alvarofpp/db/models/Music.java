
package ufrn.alvarofpp.db.models;

public class Music {
    /**
     * Nome da musica
     */
    private String name;
    /**
     * Caminho da musica 
     */
    private String path;

    public Music(String musicname, String path){
        this.name = musicname;
        this.path = path;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    
}
