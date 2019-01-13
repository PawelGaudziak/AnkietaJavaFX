package pl.pwn.reaktor.ankieta.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import pl.pwn.reaktor.ankieta.service.MailService;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuestController {

    @FXML
    private MenuItem mSaveToFile;

    @FXML
    private MenuItem mClear;

    @FXML
    private MenuItem mClose;

    @FXML
    private MenuItem mAbout;

    @FXML
    private MenuItem mSendEmail;

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
    private RadioButton rbBasic;

    @FXML
    private ToggleGroup g1;

    @FXML
    private RadioButton rbIntermediate;

    @FXML
    private RadioButton rbAdvanced;

    @FXML
    private Button btnPreview;

    @FXML
    private TextArea taPreview;

    @FXML
    private ComboBox<String> cmbCurses;

    ObservableList<String> courses = FXCollections.observableArrayList("Back-end", "Front-end", "Web developer", "Tester");

    @FXML
    void otherAction(MouseEvent event) {
        if (cbOther.isSelected()) {
            tfOther.setDisable(false);
            tfOther.setEditable(true);
        }
        else {
            tfOther.clear();
            tfOther.setDisable(true);
        }
    }

    @FXML
    void preview(MouseEvent event) {

        if (isNotCompleted()) {
            showAlertForNotCompleted();
        }
        else {
            String value = getFormData();
            taPreview.setText(value);
        }
    }

    private String getFormData() {
        String name = tfName.getText();
        String lastName = tfLastName.getText();
        String phone = tfPhone.getText();
        String email = tfMail.getText();

        String java = cbJava.isSelected() ? cbJava.getText() : "";
//            String java = "";
//            if( cbJava.isSelected()) {
//                java = cbJava.getText();
//            }

        String pyhton = cbPython.isSelected() ? cbPython.getText() : "";
        String other = cbOther.isSelected() ? tfOther.getText() : "";
//            String other = "";
//            if( cbOther.isSelected()) {
//                other = tfOther.getText();
//            }

//            String language = rbBasic.isSelected() ? rbBasic.getText() : "";
        String language = "";
        if (rbBasic.isSelected()) {
            language = rbBasic.getText();
        }
        if (rbAdvanced.isSelected()) {
            language = rbAdvanced.getText();
        }
        if (rbIntermediate.isSelected()) {
            language = rbIntermediate.getText();
        }

        String course = cmbCurses.getValue();

        return "Contact: \n " + name + " " + lastName +
               "\n mail:" + email + "\n phone:" + phone +
               "\n Programming languages:" + java + " " + pyhton + " " + other +
               "\n Languages: " + language +
               "\n Course: " + course;
    }

    private void showAlertForNotCompleted() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Błąd");
        alert.setContentText("Proszę o wypełnienie ankiety.");
        alert.setTitle("Błąd wyświetlania danych");
        alert.showAndWait();
    }

    private boolean isNotCompleted() {
        return "".equals(tfName.getText()) || "".equals(tfLastName.getText()) ||
               !(cbJava.isSelected() || cbPython.isSelected() || cbOther.isSelected())
               || cmbCurses.getValue() == null;
    }

    @FXML
    void aboutAction(ActionEvent event) {

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("About");
        info.setHeaderText("Instruction");
        info.setContentText("Instruction for using the form ...");
        info.showAndWait();
    }

    @FXML
    void clearAction(ActionEvent event) {
        tfName.clear();
        tfLastName.clear();
        tfMail.clear();
        tfPhone.clear();
        cbJava.setSelected(false);
        cbPython.setSelected(false);
        cbOther.setSelected(false);
        tfOther.setDisable(true);
        tfOther.clear();
        rbBasic.setSelected(true);
        cmbCurses.setValue(null);
        taPreview.clear();
    }

    @FXML
    void closeAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void saveToFileAction(ActionEvent event) {
        if (isNotCompleted()) {
            showAlertForNotCompleted();
        }
        else {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(null);
            fileChooser.setTitle("Save Resource File");
            String choice = file.getPath();
            try {
                PrintWriter save = new PrintWriter(choice);
                save.println(getFormData());
                save.close();

            } catch (FileNotFoundException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: File is not found");
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        }
    }

    @FXML
    void sendEmailAction(ActionEvent event) {

        if (isNotCompleted()) {
            showAlertForNotCompleted();
        }
        else {
            if (isValidMail(tfMail.getText())) {
                MailService mailService = new MailService();
                try {
                    mailService.sendMail(tfMail.getText(), "Ankieta", getFormData());

                } catch (MessagingException e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error send email");
                    error.setContentText(e.getMessage());
                    error.showAndWait();
                    e.printStackTrace();
                }
            }
            else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Wrong email");
                error.setContentText("Wrong address email:" + tfMail.getText());
                error.showAndWait();
            }
        }
    }

    private boolean isValidMail(String mail) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    public void initialize() {
        cmbCurses.setItems(courses);
    }

}
