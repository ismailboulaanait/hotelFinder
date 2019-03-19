package helper;

import bean.Hotel;
import java.util.List;

import javafx.scene.control.TableView;

public class SearchHotelHelper extends AbstractFxHelper<Hotel> {

    private static AbstractFxHelperItem[] titres;

    static {

        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("NOM HOTEL", "nom"),
            new AbstractFxHelperItem("PHONE", "phone")};
    }

    public SearchHotelHelper(TableView<Hotel> table, List<Hotel> list) {
        super(titres, table, list);
    }

    public SearchHotelHelper(TableView<Hotel> table) {
        super(titres, table);
    }

}
