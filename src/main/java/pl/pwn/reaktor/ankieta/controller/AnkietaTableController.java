package pl.pwn.reaktor.ankieta.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.pwn.reaktor.ankieta.model.Ankieta;
import pl.pwn.reaktor.ankieta.service.AnkietaService;

import java.io.IOException;
import java.util.List;

public class AnkietaTableController {

    @FXML
    private TableView<Ankieta> tableAnkieta;

    @FXML
    private TableColumn<Ankieta, Long> colId;

    @FXML
    private TableColumn<Ankieta, String> colName;

    @FXML
    private TableColumn<Ankieta, String> colLastName;

    @FXML
    private TableColumn<Ankieta, String> colMail;

    @FXML
    private TableColumn<Ankieta, String> colPhone;

    @FXML
    private TableColumn<Ankieta, Boolean> colJava;

    @FXML
    private TableColumn<Ankieta, Boolean> colPython;

    @FXML
    private TableColumn<Ankieta, Boolean> colOther;

    @FXML
    private TableColumn<Ankieta, String> colOtherDesc;

    @FXML
    private TableColumn<Ankieta, String> colLanguage;

    @FXML
    private TableColumn<Ankieta, String> colCourse;

    public void initialize() {

        // ---- pobranie danych Ankiety z bazy danych ----
        AnkietaService ankietaService = new AnkietaService();
        List<Ankieta> allAnkieta = ankietaService.getAllAnkieta();

        ObservableList<Ankieta> data = FXCollections.observableArrayList(allAnkieta);
        tableAnkieta.setItems(null);
        tableAnkieta.setItems(data);
        // --------------------------------------------

        // ustawienie kolumn które pola z Ankiety mają być widoczne i w jakiej kolumnie z widoku
        colId.setCellValueFactory(new PropertyValueFactory<Ankieta, Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("name"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("lastName"));
        colMail.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("mail"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("phone"));
        colJava.setCellValueFactory(new PropertyValueFactory<Ankieta, Boolean>("java"));
        colPython.setCellValueFactory(new PropertyValueFactory<Ankieta, Boolean>("python"));
        colOther.setCellValueFactory(new PropertyValueFactory<Ankieta, Boolean>("other"));
        colOtherDesc.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("otherDesc"));
        colLanguage.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("language"));
        colCourse.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("course"));
        // --------------------------------------------
    }

    @FXML
    void backAction(MouseEvent event) throws IOException {
        Stage adminStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/adminView.fxml"));
        adminStage.setTitle("Panel administratora");
        adminStage.setScene(new Scene(root));
        adminStage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }


    @FXML
    void onSelect(MouseEvent event) throws IOException {
        if (tableAnkieta.getSelectionModel() == null || tableAnkieta.getSelectionModel().getSelectedItem() == null) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("ERROR");
            error.setContentText("Please select Item before clicked Delete");
            error.setTitle("No selected item!");
            error.show();
            return;
        }

        // pobranie zaznaczonego wiersza
        long id = tableAnkieta.getSelectionModel()
                               .getSelectedItem()
                               .getId();
        AnkietaShowController.selectedItemId = id;

        Stage adminStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/ankietaShowView.fxml"));
        adminStage.setTitle("Ankieta show view");
        adminStage.setScene(new Scene(root));
        adminStage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();

    }


}
