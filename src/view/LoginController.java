/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    private void buttonAction(MouseEvent event){
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
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        
    }    
    
}
