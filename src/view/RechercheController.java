/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Mohcine
 */
public class RechercheController implements Initializable {
    
    @FXML
    private Pane pan_mesHotel;
    @FXML
    private Pane pan_ajouterHotel;
    @FXML
    private Pane pan_chambre;
    @FXML
    private Pane pan_administretion;
    @FXML
    private Button btn_mesHotel;
    @FXML
    private Button btn_ajouterHotel;
    @FXML
    private Button btn_administration;
    
    
    
    @FXML
    private void buttonAction(MouseEvent event){
        if (event.getSource() == btn_mesHotel) {
           pan_mesHotel.setVisible(true);
           pan_ajouterHotel.setVisible(false);
           pan_chambre.setVisible(false);
           pan_administretion.setVisible(false);
        }
        if (event.getSource() == btn_ajouterHotel) {
           pan_mesHotel.setVisible(false);
           pan_ajouterHotel.setVisible(true);
           pan_chambre.setVisible(false);
           pan_administretion.setVisible(false);
        }
        if (event.getSource() == btn_administration) {
           pan_mesHotel.setVisible(false);
           pan_ajouterHotel.setVisible(false);
           pan_chambre.setVisible(false);
           pan_administretion.setVisible(true);
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
