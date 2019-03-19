package helper;

import bean.Hotel;
import java.util.List;

import javafx.scene.control.TableView;

public class HotelHelper extends AbstractFxHelper<Hotel> {

    private static AbstractFxHelperItem[] titres;

    static {

        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("NOM HOTEL", "nom"),
            new AbstractFxHelperItem("hotel", "proprietaire"),
            new AbstractFxHelperItem("PHONE", "phone")};
    }

    public HotelHelper(TableView<Hotel> table, List<Hotel> list) {
        super(titres, table, list);
    }

    public HotelHelper(TableView<Hotel> table) {
        super(titres, table);
    }

}
