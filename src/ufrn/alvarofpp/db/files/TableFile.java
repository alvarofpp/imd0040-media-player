package ufrn.alvarofpp.db.files;

public abstract class TableFile {
    /**
     * Nome do arquivo
     */
    String filename;
    /**
     * Caminho para o arquivo
     */
    String path;
    /**
     * Delimitador padrão para leitura de CSV
     */
    String delimiter = ",";

    /**
     * Cria uma nova instância e atualiza no arquivo
     */
    public abstract void create();

    /**
     * Ler o arquivo e carrega os dados
     */
    public abstract void readFile();
}
