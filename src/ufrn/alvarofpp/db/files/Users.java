package ufrn.alvarofpp.db.files;

import java.util.Vector;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import ufrn.alvarofpp.db.models.User;

public class Users extends TableFile {
    Vector<User> users;

    public Users() {
        this.filename = "users.csv";
        this.path = getClass().getResource("../data/users.csv").getPath();
        this.users = new Vector<>();
        readFile();
    }

    @Override
    public void create() {

    }

    @Override
    public void readFile() {
        String line = "";
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
}
