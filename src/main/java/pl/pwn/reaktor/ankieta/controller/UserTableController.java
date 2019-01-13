package pl.pwn.reaktor.ankieta.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.pwn.reaktor.ankieta.model.RoleEnum;
import pl.pwn.reaktor.ankieta.model.User;
import pl.pwn.reaktor.ankieta.service.UserService;

import java.io.IOException;
import java.util.List;
//budoeanie tablicy
public class UserTableController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Long> colId;

    @FXML
    private TableColumn<User, String> colLogin;

    @FXML
    private TableColumn<User, RoleEnum> colRole;

    @FXML
    private TableColumn<User, Boolean> colActive;

    @FXML
    private Button btnBack;

    @FXML
    void backAction(MouseEvent event) throws IOException {
        Stage adminStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/adminView.fxml"));
        adminStage.setTitle("Panel administratora");
        adminStage.setScene(new Scene(root));
        adminStage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void initialize(){

        //-- Pobranie danych z bazy danych z tabeli user i dodanie do TableView na widoku
        UserService userService = new UserService();
        List<User> users = userService.getAllUser();

        ObservableList<User> allUser= FXCollections.observableArrayList(users);
        userTable.setItems(null);
        userTable.setItems(allUser);
        //------

        // wskazanie kolumn do wyświetlenia z naszego modelu
        colId.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        colLogin.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        colRole.setCellValueFactory(new PropertyValueFactory<User, RoleEnum>("role"));
        colActive.setCellValueFactory(new PropertyValueFactory<User, Boolean>("active"));
        // -------

    }


}
