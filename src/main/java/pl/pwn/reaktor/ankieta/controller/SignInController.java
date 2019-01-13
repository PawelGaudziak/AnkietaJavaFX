package pl.pwn.reaktor.ankieta.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pl.pwn.reaktor.ankieta.model.RoleEnum;
import pl.pwn.reaktor.ankieta.model.User;
import pl.pwn.reaktor.ankieta.service.UserService;

public class SignInController {

    @FXML
    private Button btnRegister;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfPassword;

    @FXML
    void register(MouseEvent event) {

        User user = new User(tfLogin.getText(), tfPassword.getText(), RoleEnum.ROLE_USER, true);

        UserService userService = new UserService();
        userService.save(user);
    }

}
