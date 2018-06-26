package ufrn.alvarofpp.db.files;

import java.util.Vector;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import ufrn.alvarofpp.db.models.User;
import ufrn.alvarofpp.exceptions.UserExistException;
import ufrn.alvarofpp.exceptions.FieldNotFoundException;

/**
 * Classe para leitura de arquivos de dados dos usuários
 */
public class Users extends TableFile {
    /**
     * Vetor de usuários cadastrados.
     */
    private Vector<User> users;

    public Users() {
        this.filename = "users.csv";
        this.path = getClass().getResource("../data/" + this.filename).getPath();
        this.users = new Vector<>();
        readFile();
    }

    /**
     * Cria uma nova instância e atualiza no arquivo
     * @param username Nome de usuário
     * @param password Senha de usuário
     */
    public void create(String username, String password) throws FieldNotFoundException,UserExistException {
        // Verifica se um dos campos está vazio
        if ("".equals(username)) {
            throw new FieldNotFoundException("Username");
        } else if ("".equals(password)) {
            throw new FieldNotFoundException("Password");
        }

        // Verifica se usuário já existe
        if (this.exist(username)) {
            throw new UserExistException(username);
        }

        // Novo usuário
        User user = new User(username, password);

        try {
            // Salva o novo usuário
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path, true));
            writer.write(user.getUsername() + this.delimiter + user.getPassword()+"\n");
            writer.close();

            // Atualiza a lista de usuários
            this.users.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Ler e salva os dados de todos os usuários cadastrados.
     */
    @Override
    public void readFile() {
        String line;
        String[] inputs;

        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            // Percorre o arquivo
            while ((line = br.readLine()) != null) {
                inputs = line.split(this.delimiter);
                this.users.add(new User(inputs[0], inputs[1]));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Válida um usuário a partir do seu username e senha
     * @param username Username do usuário
     * @param password Senha do usuário
     * @return Verdadeiro se for válido, falso caso contrário
     */
    public boolean validateUser(String username, String password) throws FieldNotFoundException {
        // Verifica se um dos campos está vazio
        if ("".equals(username)) {
            throw new FieldNotFoundException("Username");
        } else if ("".equals(password)) {
            throw new FieldNotFoundException("Password");
        }

        // Procura o usuário
        for (User user : this.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica se usuário existe
     * @param username Username do usuário
     * @return Verdadeiro se existir, falso caso contrário
     */
    private boolean exist(String username) {
        // Procura o usuário
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Pega o objeto referente ao usuário desejado.
     * @param username Nome de usuário
     * @return O objeto referente ao usuário em questão ou null caso o usuário não exista
     */
    public User getUser(String username) {
        // Procura o usuário
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }
}
