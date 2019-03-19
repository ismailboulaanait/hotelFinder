/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import bean.Hotel;
import bean.HotelType;
import bean.Pays;
import bean.Ville;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import service.HotelService;
import service.HotelTypeService;
import service.PaysService;
import service.VilleService;

/**
 * FXML Controller class
 *
 * @author Mohcine
 */
public class RechercheController implements Initializable {

    List<Pays> pays = new ArrayList();
    List<Ville> villes = new ArrayList();
    List<HotelType> hotelTypes = new ArrayList();

    PaysService ps = new PaysService();
    VilleService vs = new VilleService();
    HotelTypeService hts = new HotelTypeService();
    HotelService hs = new HotelService();

    //=======pane control=============
    @FXML
    private Pane pan_mesHotel;
    @FXML
    private Pane pan_ajouterHotel;
    @FXML
    private Pane pan_chambre;

    @FXML
    private Button btn_mesHotel;
    @FXML
    private Button btn_ajouterHotel;
    //====================work add====================
    @FXML
    private ComboBox<String> paysAjout = new ComboBox<String>();
    @FXML
    private ComboBox<String> villeAjout = new ComboBox<String>();
    ;
    @FXML
    private ComboBox<String> catAjout = new ComboBox<String>();
    @FXML
    private TextField nomAjout;
    @FXML
    private TextField phoneAjout;
    @FXML
    private Button buttonAjout;
//================work find ==================================
    @FXML
    private ComboBox<String> paysFind = new ComboBox<String>();
    @FXML
    private ComboBox<String> villeFind = new ComboBox<String>();
    ;
    @FXML
    private ComboBox<String> catFind = new ComboBox<String>();
    @FXML
    private ComboBox<String> etatFind = new ComboBox<String>();

    @FXML
    private void buttonAction(MouseEvent event) {
        if (event.getSource() == btn_mesHotel) {
            pan_mesHotel.setVisible(true);
            pan_ajouterHotel.setVisible(false);
            pan_chambre.setVisible(false);
        }
        if (event.getSource() == btn_ajouterHotel) {
            pan_mesHotel.setVisible(false);
            pan_ajouterHotel.setVisible(true);
            pan_chambre.setVisible(false);
        }
    }

    @FXML
    private void ajouter(MouseEvent event) {
        Ville ville = villes.get(villeAjout.getSelectionModel().getSelectedIndex());
        HotelType type = hotelTypes.get(catAjout.getSelectionModel().getSelectedIndex());
        hs.addHotel(nomAjout.getText(), phoneAjout.getText(), ville, type);
        JOptionPane.showMessageDialog(null, "votre demande est envoyé ", "info", JOptionPane.INFORMATION_MESSAGE);
        nomAjout.setText("");
        phoneAjout.setText("");

    }

    @FXML
    private void find(MouseEvent event) {
        Ville ville =null;
        Pays payss =null;
        HotelType type=null;
        Boolean etat=null;
         ville = villes.get(villeFind.getSelectionModel().getSelectedIndex());
         payss = pays.get(villeFind.getSelectionModel().getSelectedIndex());
         type = hotelTypes.get(catFind.getSelectionModel().getSelectedIndex());
        int index = etatFind.getSelectionModel().getSelectedIndex();
        if (index == 0) {
            etat = true;
        }else if (index==1) {
            etat = false;
        }
        List<Hotel> res = hs.SearchHotelAdmin(payss, ville, type, etat);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBoxTypeHotel();
        initComboBoxPays();
        initComboBoxTypeHotel1();
        initComboBoxPays1();
        initComboBoxEtat();
    }

    @FXML
    public void setVille(ActionEvent event) {
        initComboBoxVille();
    }

    private void initComboBoxPays() {
        pays = ps.findAll();
        paysAjout.setPromptText("--SELECT--");
        for (Pays pay : pays) {
            paysAjout.getItems().add(pay.getNom());
        }
    }

    private void initComboBoxVille() {
        villeAjout.getItems().clear();
        villeAjout.setPromptText("--SELECT--");
        int index = paysAjout.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Pays p = pays.get(index);
            villes = vs.findVillesByPays(p.getId());
            for (Ville v : villes) {
                villeAjout.getItems().add(v.getNom());
            }
        }
    }

    private void initComboBoxTypeHotel() {
        hotelTypes = hts.findAll();
        catAjout.setPromptText("--SELECT--");
        for (HotelType items : hotelTypes) {
            catAjout.getItems().add(items.getEtoile() + "étoile");
        }
    }

    @FXML
    public void setVilleFind(ActionEvent event) {
        initComboBoxVille1();
    }

    private void initComboBoxPays1() {
        pays = ps.findAll();
        paysFind.setPromptText("--SELECT--");
        for (Pays pay : pays) {
            paysFind.getItems().add(pay.getNom());
        }
    }

    private void initComboBoxVille1() {
        villeFind.getItems().clear();
        villeFind.setPromptText("--SELECT--");
        int index = paysFind.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Pays p = pays.get(index);
            villes = vs.findVillesByPays(p.getId());
            for (Ville v : villes) {
                villeFind.getItems().add(v.getNom());
            }
        }
    }

    private void initComboBoxTypeHotel1() {
        hotelTypes = hts.findAll();
        catFind.setPromptText("--SELECT--");
        for (HotelType items : hotelTypes) {
            catFind.getItems().add(items.getEtoile() + "étoile");
        }
    }

    private void initComboBoxEtat() {
        etatFind.setPromptText("--SELECT--");
        etatFind.getItems().add("accepte");
        etatFind.getItems().add("en traitement");
    }

}
