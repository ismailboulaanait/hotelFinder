/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import bean.Chambre;
import bean.Hotel;
import bean.HotelType;
import bean.Pays;
import bean.User;
import bean.Ville;
import helper.ChambreHelper;
import helper.HotelHelperEspace;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import service.ChambreService;
import service.HotelService;
import service.HotelTypeService;
import service.PaysService;
import service.ReviewService;
import service.VilleService;
import util.Session;

/**
 * FXML Controller class
 *
 * @author Mohcine
 */
public class RechercheController implements Initializable {

    @FXML
    private TableView tableFind = new TableView();
    @FXML
    private TableView tableChambre = new TableView();

    private HotelHelperEspace hhe;
    private ChambreHelper ch;

    List<Pays> pays = new ArrayList();
    List<Ville> villes = new ArrayList();
    List<HotelType> hotelTypes = new ArrayList();

    PaysService ps = new PaysService();
    VilleService vs = new VilleService();
    HotelTypeService hts = new HotelTypeService();
    HotelService hs = new HotelService();
    ReviewService rs = new ReviewService();
    ChambreService cs = new ChambreService();

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
    private Button detail;
    @FXML
    private Button buttonFind;
    //================manip chambre ==============================
    @FXML
    private TextField numch;
    @FXML
    private TextField nbrch;
    @FXML
    private TextField prixch;
    @FXML
    private Button ajbtn;
    @FXML
    private Button modbtn;
    @FXML
    private Button supbtn;
    @FXML
    private Button retour;
    @FXML
    private Button torecherche;
    @FXML
    private Button singout;
    
    @FXML
    private Button close;
    @FXML
    public void close(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void buttonAction(MouseEvent event) {
        if (event.getSource() == btn_mesHotel) {
            pan_mesHotel.toFront();
        }
        if (event.getSource() == btn_ajouterHotel) {
            pan_ajouterHotel.toFront();
        }
        if (event.getSource() == torecherche) {
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
    
    @FXML
    private void sngout(MouseEvent event) {
        Session.finSession();
        try {
                Platform.setImplicitExit(false);
                Parent root1;
                root1 = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @FXML
    private void ajouter(MouseEvent event) {
        Ville ville = villes.get(villeAjout.getSelectionModel().getSelectedIndex() - 1);
        HotelType type = hotelTypes.get(catAjout.getSelectionModel().getSelectedIndex() - 1);
        hs.addHotel(nomAjout.getText(), phoneAjout.getText(), ville, type);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("info");
        alert.setHeaderText(null);
        alert.setContentText("votre demande est envoyé ");
        alert.showAndWait();
        nomAjout.setText("");
        phoneAjout.setText("");

    }

    @FXML
    private void find(MouseEvent event) {

        Ville ville = null;
        Pays payss = null;
        HotelType type = null;
        Boolean etat = null;

        int vil = villeFind.getSelectionModel().getSelectedIndex();
        int pay = villeFind.getSelectionModel().getSelectedIndex();
        int cat = catFind.getSelectionModel().getSelectedIndex();
        if (vil > 0) {
            ville = villes.get(vil - 1);
        }
        if (pay > 0) {
            payss = pays.get(pay - 1);
        }
        if (cat > 0) {
            type = hotelTypes.get(cat - 1);
        }
        int index = etatFind.getSelectionModel().getSelectedIndex();
        if (index == 1) {
            etat = true;
        } else if (index == 2) {
            etat = false;
        }
        User owner = (User) Session.getConnectedUser();
        List<Hotel> res = hs.searchMyHotel(payss, ville, type, etat, owner);
        hhe.setList(res);
    }

    @FXML
    private void detail(MouseEvent event) {
        Hotel hotel = hhe.getSelected();
        List<Chambre> res = cs.getChambresHotel(hotel);
        ch.setList(res);
        pan_chambre.toFront();
    }

    @FXML
    private void setchambre(MouseEvent event) {
        Chambre chambre = ch.getSelected();
        numch.setText(chambre.getNumero() + "");
        nbrch.setText(chambre.getNbrLit() + "");
        prixch.setText(chambre.getPrix() + "");
    }

    @FXML
    private void ajoutChambre(MouseEvent event) {
        int num = new Integer(numch.getText());
        int lit = new Integer(nbrch.getText());
        int prix = new Integer(prixch.getText());
        Hotel hotel = hhe.getSelected();
        if (hs.verifierAccepte(hotel) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("votre hotel n'est pas encore accepé");
            alert.showAndWait();
        } else {

            int res = cs.addChambre(num, lit, prix, hotel);
            if (res == 1) {
                ch.setList(cs.getChambresHotel(hotel));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("info");
                alert.setHeaderText(null);
                alert.setContentText("chambre ajouté avec succes");
                alert.showAndWait();
            }
            if (res == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("numero chambre existe deja");
                alert.showAndWait();
            }

        }
    }

    @FXML
    private void modifierChambre(MouseEvent event) {
        int num = new Integer(numch.getText());
        int lit = new Integer(nbrch.getText());
        int prix = new Integer(prixch.getText());
        Hotel hotel = hhe.getSelected();
        if (hs.verifierAccepte(hotel) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("votre hotel n'est pas accepté");
            alert.showAndWait();
        } else {
            Chambre chambre = ch.getSelected();
            int res = cs.modifierChambre(num, lit, prix, chambre);
            if (res == 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("une chambre a été modifier");
                alert.showAndWait();
                ch.setList(cs.getChambresHotel(hotel));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("aucune modification n'est effectuer");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void supprimerChambre(MouseEvent event) {
        Chambre chambre = ch.getSelected();
        Hotel hotel = hhe.getSelected();

        int res = cs.deleteChambre(chambre);
        if (res == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("une chambre a été supprimé");
            alert.showAndWait();
            ch.setList(cs.getChambresHotel(hotel));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("aucune modification n'est effectuer");
            alert.showAndWait();
        }
    }

    public void retour(MouseEvent event) {
        pan_mesHotel.toFront();
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
        initTableFind();
        initTableChambre();

    }

    @FXML
    public void setVille(ActionEvent event) {
        initComboBoxVille();
    }

    private void initComboBoxPays() {
        pays = ps.findAll();
        paysAjout.getItems().add("--select--");

        for (Pays pay : pays) {
            paysAjout.getItems().add(pay.getNom());
        }
        paysAjout.getSelectionModel().select(0);

    }

    private void initComboBoxVille() {
        villeAjout.getItems().clear();
        int index = paysAjout.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            Pays p = pays.get(index - 1);
            villes = vs.findVillesByPays(p.getId());
            villeAjout.getItems().add("--select--");
            for (Ville v : villes) {
                villeAjout.getItems().add(v.getNom());
            }
            villeAjout.getSelectionModel().select(0);

        }
    }

    private void initComboBoxTypeHotel() {
        hotelTypes = hts.findAll();
        catAjout.getItems().add("--select--");

        for (HotelType items : hotelTypes) {
            catAjout.getItems().add(items.getEtoile() + "étoile");
        }
        catAjout.getSelectionModel().select(0);

    }

    @FXML
    public void setVilleFind(ActionEvent event) {
        initComboBoxVille1();
    }

    private void initComboBoxPays1() {
        pays = ps.findAll();
        paysFind.getItems().add("--select--");
        for (Pays pay : pays) {
            paysFind.getItems().add(pay.getNom());
        }
        paysFind.getSelectionModel().select(0);
    }

    private void initComboBoxVille1() {
        villeFind.getItems().clear();
        int index = paysFind.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            Pays p = pays.get(index - 1);
            villes = vs.findVillesByPays(p.getId());
            villeFind.getItems().add("--select--");
            for (Ville v : villes) {
                villeFind.getItems().add(v.getNom());
            }
            villeFind.getSelectionModel().select(0);

        }
    }

    private void initComboBoxTypeHotel1() {
        hotelTypes = hts.findAll();
        catFind.getItems().add("--select--");
        for (HotelType items : hotelTypes) {
            catFind.getItems().add(items.getEtoile() + "étoile");
        }
        catFind.getSelectionModel().select(0);
    }

    private void initComboBoxEtat() {
        etatFind.getItems().add("--select--");
        etatFind.getItems().add("accepte");
        etatFind.getItems().add("en traitement");
        etatFind.getSelectionModel().select(0);
    }

    private void initTableFind() {
        hhe = new HotelHelperEspace(tableFind);
    }

    private void initTableChambre() {
        ch = new ChambreHelper(tableChambre);
    }

}
