/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import bean.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author ismail
 */
public class LoginController implements Initializable {

    @FXML
    private Pane home;
    @FXML
    private Button home_btn;
    @FXML
    private Pane login;
    @FXML
    private Pane register;

    @FXML
    private Button login_btn;
    @FXML
    private Button register_btn;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField password2;
    @FXML
    private TextField cin;
    @FXML
    private Button btn;

    @FXML
    private TextField usernamelog;
    @FXML
    private TextField passlog;
    @FXML
    private Button btnlog;

    UserService userService = new UserService();

    @FXML
    private void buttonAction(MouseEvent event) {
        if (event.getSource() == home_btn) {
            home.setVisible(true);
            login.setVisible(false);
            register.setVisible(false);
        }
        if (event.getSource() == login_btn) {
            home.setVisible(false);
            login.setVisible(true);
            register.setVisible(false);
        }
        if (event.getSource() == register_btn) {
            home.setVisible(false);
            login.setVisible(false);
            register.setVisible(true);
        }
        if (event.getSource() == btn) {
            User user = userService.addUser(username.getText(), password.getText(), cin.getText());
            if (user == null) {
                JOptionPane.showMessageDialog(null, "CIN déja utilisé", "error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "compte crée", "info", JOptionPane.INFORMATION_MESSAGE);
                login.toFront();
            }

        }

    }

    @FXML
    public void connect(MouseEvent event) {
        int res = userService.connect(usernamelog.getText(), passlog.getText());
        if (res < 0) {
            JOptionPane.showMessageDialog(null, "invalid username or password", "error", JOptionPane.ERROR_MESSAGE);
        } else if (res == 1) {
             try {
                Platform.setImplicitExit(false);
                Parent root1;
                root1 = FXMLLoader.load(getClass().getResource("admin.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (res == 2) {
            try {
                Platform.setImplicitExit(false);
                Parent root1;
                root1 = FXMLLoader.load(getClass().getResource("search.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

}
