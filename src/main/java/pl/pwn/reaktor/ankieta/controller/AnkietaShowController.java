package pl.pwn.reaktor.ankieta.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.pwn.reaktor.ankieta.model.Ankieta;
import pl.pwn.reaktor.ankieta.service.AnkietaService;

import java.io.IOException;

public class AnkietaShowController {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfMail;

    @FXML
    private TextField tfPhone;

    @FXML
    private CheckBox cbJava;

    @FXML
    private CheckBox cbPython;

    @FXML
    private CheckBox cbOther;

    @FXML
    private TextField tfOther;

    @FXML
    private RadioButton rbName;

    @FXML
    private ComboBox<String> cmbCurses;

    static long selectedItemId;

    @FXML
    void back(MouseEvent event) throws IOException {
        Stage userStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/ankietaTableView.fxml"));
        userStage.setTitle("Lista wszystkich ankiet");
        userStage.setScene(new Scene(root));
        userStage.show();

        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();
    }

    public void initialize() {

        if (selectedItemId > 0) {
            AnkietaService ankietaService = new AnkietaService();
            Ankieta ankieta = ankietaService.findById(selectedItemId);

            tfName.setText(ankieta.getName());
            tfLastName.setText(ankieta.getLastName());
            tfMail.setText(ankieta.getMail());
            tfPhone.setText(ankieta.getPhone());
            cbJava.setSelected(ankieta.getJava());
            cbPython.setSelected(ankieta.getPython());
            cbOther.setSelected(ankieta.getOther());
            tfOther.setText(ankieta.getOtherDesc());
            rbName.setText(ankieta.getLanguage());
            rbName.setSelected(true);
            cmbCurses.setValue(ankieta.getCourse());
        }
    }
}