package ufrn.alvarofpp.controllers.helpers;

import javafx.scene.control.Alert;

/**
 * Essa classe servirá para exibir alertas nas telas.
 */
public class Alerts {
    private Alert alert;

    public Alerts(){}

    /**
     * Monta o alerta com os valores desejados.
     * @param title Título
     * @param header Cabeçalho
     * @param content Conteúdo
     */
    private void make(String title, String header, String content) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
    }

    /**
     * Exibe o alerta.
     * @param type Tipo de alerta
     * @param title Título
     * @param header Cabeçalho
     * @param content Conteúdo
     */
    public void show(int type, String title, String header, String content) {
        // Define o alerta
        switch (type) {
            case 1: alert = new Alert(Alert.AlertType.INFORMATION);
            default: alert = new Alert(Alert.AlertType.WARNING);
        }

        // Monta o alerta
        this.make(title, header, content);

        // Exibe o alerta
        alert.showAndWait();
    }
}
