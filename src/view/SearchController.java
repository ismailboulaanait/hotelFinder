/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import helper.HotelHelper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import service.HotelService;

/**
 * FXML Controller class
 *
 * @author ismail
 */
public class SearchController implements Initializable {

   
    HotelService hotelService=new HotelService();
    
    @FXML
    private TableView hotelTable = new TableView();
    private HotelHelper hotelFxHelper;
    private void initHelper(){
        hotelFxHelper= new HotelHelper(hotelTable, hotelService.findAll());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initHelper();
    }    

    public TableView getHotelTable() {
        return hotelTable;
    }

    public void setHotelTable(TableView hotelTable) {
        this.hotelTable = hotelTable;
    }
    
    
}
