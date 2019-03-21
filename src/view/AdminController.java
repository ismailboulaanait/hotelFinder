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
import helper.HotelHelper;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import service.HotelService;
import service.HotelTypeService;
import service.PaysService;
import service.VilleService;
import util.Session;

/**
 * FXML Controller class
 *
 * @author Mohcine
 */
public class AdminController implements Initializable {

    //=====list
    List<Pays> pays = new ArrayList();
    List<Ville> villes = new ArrayList();
    List<HotelType> hotelTypes = new ArrayList();

    //===sevice
    PaysService ps = new PaysService();
    VilleService vs = new VilleService();
    HotelService hotelService = new HotelService();
    HotelTypeService hts = new HotelTypeService();

    @FXML
    private Pane panAdmin;

    //====combobox
    @FXML
    private ComboBox<String> comboPay = new ComboBox<String>();
    @FXML
    private ComboBox<String> comboVill = new ComboBox<String>();
    @FXML
    private ComboBox<String> comboEtat = new ComboBox<String>();
    @FXML
    private ComboBox<String> comboCategorier = new ComboBox<String>();
    //====button
    @FXML
    private Button btnFind;
    @FXML
    private Button btnAccept;
    @FXML
    private Button btnDelet;
    @FXML
    private Button btnBlock;
    @FXML
    private Button singout;
    
    @FXML
    private Button close;
    @FXML
    public void close(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    //===table
    @FXML
    private TableView hotelTable = new TableView();
    private HotelHelper hotelHelper;

    private void initHelper() {
        hotelHelper = new HotelHelper(hotelTable);
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private void find(MouseEvent event) {
        searchAgain();
    }

    @FXML
    private void accepter(MouseEvent event) {
        Hotel hotel = hotelHelper.getSelected();
        hotelService.accepteDemande(hotelHelper.getSelected());
        searchAgain();

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
    private void delete(MouseEvent event) {
        int res = hotelService.deleteHotel(hotelHelper.getSelected());
        if (res == 1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("un hotel a été supprimé");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("aucune suppression n'est effectué");
            alert.showAndWait();
        }
        searchAgain();

    }

    @FXML
    private void blocke(MouseEvent event) {
        Hotel hotel = hotelHelper.getSelected();
        int res = hotelService.bloquerHotel(hotel);
        if (res < 0) {
             Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("l'hotel est deja bloqué");
            alert.showAndWait();
        }
        if (res > 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("l'hotel bloqué avec succes");
            alert.showAndWait();
        }
        searchAgain();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBoxPays();
        initComboBoxVille();
        initComboBoxEtat();
        initComboBoxTypeHotel();
        initHelper();
    }

    @FXML
    public void setVille(ActionEvent event) {
        initComboBoxVille();
    }

    private void initComboBoxPays() {
        pays = ps.findAll();
        comboPay.getItems().add("--select--");
        for (Pays pay : pays) {
            comboPay.getItems().add(pay.getNom());
        }
        comboPay.getSelectionModel().select(0);
    }

    private void initComboBoxVille() {
        comboVill.getItems().clear();
        comboVill.getItems().add("--select--");

        int index = comboPay.getSelectionModel().getSelectedIndex() - 1;
        if (index >= 0) {
            Pays p = pays.get(index);
            villes = vs.findVillesByPays(p.getId());
            for (Ville v : villes) {
                comboVill.getItems().add(v.getNom());
            }
            comboVill.getSelectionModel().select(0);
        }
    }

    private void initComboBoxEtat() {
        comboEtat.getItems().add("--select--");
        comboEtat.getItems().add("demmandes");
        comboEtat.getItems().add("valide");
        comboEtat.getSelectionModel().select(0);
    }

    private void initComboBoxTypeHotel() {
        hotelTypes = hts.findAll();
        comboCategorier.getItems().add("--select--");
        for (HotelType items : hotelTypes) {
            comboCategorier.getItems().add(items.getEtoile() + "étoile");
        }
        comboCategorier.getSelectionModel().select(0);
    }

    private void searchAgain() {
        int pay = comboPay.getSelectionModel().getSelectedIndex();
        int vil = comboVill.getSelectionModel().getSelectedIndex();
        int cat = comboCategorier.getSelectionModel().getSelectedIndex();
        int eta = comboEtat.getSelectionModel().getSelectedIndex();
        Pays payss = null;
        Ville ville = null;
        HotelType categ = null;
        Boolean etat = null;
        if (pay > 0) {
            payss = pays.get(pay - 1);
        }
        if (vil > 0) {
            ville = villes.get(vil - 1);
        }
        if (cat > 0) {
            categ = hotelTypes.get(cat - 1);
        }
        if (eta == 1) {
            etat = false;
        } else if (eta == 2) {
            etat = true;
        }
        List<Hotel> res = hotelService.SearchHotelAdmin(payss, ville, categ, etat);
        hotelHelper.setList(res);

    }

    public Pane getPanAdmin() {
        return panAdmin;
    }

    public void setPanAdmin(Pane panAdmin) {
        this.panAdmin = panAdmin;
    }

    public ComboBox<String> getComboPay() {
        return comboPay;
    }

    public void setComboPay(ComboBox<String> comboPay) {
        this.comboPay = comboPay;
    }

    public ComboBox<String> getComboVill() {
        return comboVill;
    }

    public void setComboVill(ComboBox<String> comboVill) {
        this.comboVill = comboVill;
    }

    public ComboBox<String> getComboEtat() {
        return comboEtat;
    }

    public void setComboEtat(ComboBox<String> comboEtat) {
        this.comboEtat = comboEtat;
    }

    public ComboBox<String> getComboCategorier() {
        return comboCategorier;
    }

    public void setComboCategorier(ComboBox<String> comboCategorier) {
        this.comboCategorier = comboCategorier;
    }

    public Button getBtnFind() {
        return btnFind;
    }

    public void setBtnFind(Button btnFind) {
        this.btnFind = btnFind;
    }

    public Button getBtnAccept() {
        return btnAccept;
    }

    public void setBtnAccept(Button btnAccept) {
        this.btnAccept = btnAccept;
    }

    public Button getBtnDelet() {
        return btnDelet;
    }

    public void setBtnDelet(Button btnDelet) {
        this.btnDelet = btnDelet;
    }

    public Button getBtnBlock() {
        return btnBlock;
    }

    public void setBtnBlock(Button btnBlock) {
        this.btnBlock = btnBlock;
    }

    public TableView getHotelTable() {
        return hotelTable;
    }

    public void setHotelTable(TableView hotelTable) {
        this.hotelTable = hotelTable;
    }

    public HotelHelper getHotelFxHelper() {
        return hotelHelper;
    }

    public void setHotelFxHelper(HotelHelper hotelFxHelper) {
        this.hotelHelper = hotelFxHelper;
    }

}
