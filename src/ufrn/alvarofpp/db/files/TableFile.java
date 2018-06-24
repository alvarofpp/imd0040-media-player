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
     * Ler o arquivo e carrega os dados
     */
    public abstract void readFile();
}
