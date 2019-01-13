package pl.pwn.reaktor.ankieta.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.pwn.reaktor.ankieta.service.AnkietaService;

import java.io.IOException;

public class AdminController {


    @FXML
    private Button btnViewAllAnkieta;

    @FXML
    private Button btnViewAllUser;

    @FXML
    void viewAllAnkieta(MouseEvent event) throws IOException {
        Stage adminStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/ankietaTableView.fxml"));
        adminStage.setTitle("Lista wszystkich ankiet");
        adminStage.setScene(new Scene(root));
        adminStage.show();

        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();
    }

    @FXML
    void viewAllUser(MouseEvent event) throws IOException {
        Stage adminStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/userTableView.fxml"));
        adminStage.setTitle("Lista wszystkich użytkowników");
        adminStage.setScene(new Scene(root));
        adminStage.show();

        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();
    }

    @FXML
    private Button btnGetLastEmail;

    @FXML
    private Label lbLastEmail;

    @FXML
    void getLastEmailFromAnkieta(MouseEvent event) {

        AnkietaService ankietaService = new AnkietaService();
        String lastEmail = ankietaService.getLastEmail();

        lbLastEmail.setText(lastEmail);

    }

    @FXML
    private Button btnGetNameAndLastName;

    @FXML
    private Label lbNameAndLastName;

    @FXML
    void getNameAndLastNameFromAnkieta(MouseEvent event) {

        String emailText = lbLastEmail.getText();
        if("Brak".equals(emailText)){
            return;
        }

        AnkietaService ankietaService = new AnkietaService();
        String nameAndLastName = ankietaService.getNameAndLastName(emailText);
        lbNameAndLastName.setText(nameAndLastName);
    }

    @FXML
    public Button btnViewAllAnkietaEditable;

    @FXML
    void viewAllAnkietaEditable(MouseEvent event) throws IOException {
        Stage adminStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/ankietaEditableTableView.fxml"));
        adminStage.setTitle("Lista wszystkich użytkowników");
        adminStage.setScene(new Scene(root));
        adminStage.show();

        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();

    }
}