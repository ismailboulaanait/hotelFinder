/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import bean.Hotel;
import bean.HotelType;
import bean.Pays;
import bean.Review;
import bean.User;
import bean.Ville;
import helper.HotelHelper;
import helper.ReviewHelper;
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
 * @author ismail
 */
public class SearchController implements Initializable {

    List<Pays> pays = new ArrayList();
    List<Ville> villes = new ArrayList();
    List<HotelType> hotelTypes = new ArrayList();
    @FXML
    private Button toannonce;
    //==============services====================
    PaysService ps = new PaysService();
    VilleService vs = new VilleService();
    HotelTypeService hts = new HotelTypeService();
    HotelService hs = new HotelService();
    ReviewService rs = new ReviewService();
    ChambreService cs = new ChambreService();

    //==========helpers===========================
    private HotelHelper hh;
    private ReviewHelper rh;
    //===============search=======================
    @FXML
    private Pane comment;
    @FXML
    private TableView tableComment = new TableView();
    @FXML
    private TableView hotelTable = new TableView();
    @FXML
    private ComboBox<String> payes = new ComboBox<String>();
    @FXML
    private ComboBox<String> ville = new ComboBox<String>();
    @FXML
    private ComboBox<String> cat = new ComboBox<String>();
    @FXML
    private TextField min;
    @FXML
    private TextField max;
    @FXML
    private TextField place;
    @FXML
    private Button find;
    @FXML
    private Button btnComment;
    @FXML
    private Button addComment;
    @FXML
    private Button btnret;
    @FXML
    private TextField textComment;
    @FXML
    private Pane search;
    @FXML
    private Button singout;
    
    @FXML
    private Button close;
    @FXML
    public void close(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initHelper();
        initComboBoxTypeHotel();
        initComboBoxPays();
        initTableComment();
    }

    @FXML
    public void toannonce(MouseEvent event) {
        try {
            Platform.setImplicitExit(false);
            Parent root1;
            root1 = FXMLLoader.load(getClass().getResource("recherche.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void retour(MouseEvent event) {
      search.toFront();
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
    public void addComment(MouseEvent event) {
        User client = (User) Session.getConnectedUser();
        Hotel hotel = hh.getSelected();

        if (textComment.getText().equals("")) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("veuillez commentez");
            alert.showAndWait();
        } else {
            String comment = textComment.getText();
            rs.createReview(comment, 0, client, hotel);

            List<Review> res = rs.searchReviewByHotel(hotel);
            rh.setList(res);

        }

    }

    @FXML
    public void find(MouseEvent event) {
        int p = payes.getSelectionModel().getSelectedIndex();
        int v = ville.getSelectionModel().getSelectedIndex();
        int c = cat.getSelectionModel().getSelectedIndex();
        Pays payss = null;
        Ville ville = null;
        HotelType type = null;
        Integer prixMin = null;
        Integer prixMax = null;
        Integer nbrlit = null;
        if (p > 0) {
            payss = pays.get(p - 1);
        }
        if (v > 0) {
            ville = villes.get(v - 1);
        }
        if (c > 0) {
            type = hotelTypes.get(c - 1);
        }
        if (place.getText() != null && !place.getText().equals("")) {
            nbrlit = new Integer(place.getText());
        }
        if (min.getText() != null && !min.getText().equals("")) {
            prixMin = new Integer(min.getText());
        }
        if (max.getText() != null && !max.getText().equals("")) {
            prixMax = new Integer(max.getText());
        }

        List<Hotel> res = hs.searchByCriteria(payss, ville, type, nbrlit, prixMin, prixMax);
        hh.setList(res);
    }

    private void initHelper() {
        hh = new HotelHelper(hotelTable);
    }

    @FXML
    public void setVille(ActionEvent event) {
        initComboBoxVille();
    }

    @FXML
    public void setComments(MouseEvent event) {
        Hotel hotel = hh.getSelected();
        List<Review> res = rs.searchReviewByHotel(hotel);
        rh.setList(res);
        comment.toFront();
    }

    private void initComboBoxPays() {
        pays = ps.findAll();
        payes.getItems().add("--select--");

        for (Pays pay : pays) {
            payes.getItems().add(pay.getNom());
        }
        payes.getSelectionModel().select(0);

    }

    private void initComboBoxVille() {
        ville.getItems().clear();
        int index = payes.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            Pays p = pays.get(index - 1);
            villes = vs.findVillesByPays(p.getId());
            ville.getItems().add("--select--");
            for (Ville v : villes) {
                ville.getItems().add(v.getNom());
            }
            ville.getSelectionModel().select(0);

        }
    }

    private void initComboBoxTypeHotel() {
        hotelTypes = hts.findAll();
        cat.getItems().add("--select--");

        for (HotelType items : hotelTypes) {
            cat.getItems().add(items.getEtoile() + "étoile");
        }
        cat.getSelectionModel().select(0);

    }

    private void initTableComment() {
        rh = new ReviewHelper(tableComment);
    }

}
