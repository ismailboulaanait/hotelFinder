package helper;

import bean.Hotel;
import java.util.List;

import javafx.scene.control.TableView;

public class HotelHelperEspace extends AbstractFxHelper<Hotel> {

    private static AbstractFxHelperItem[] titres;

    static {

        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("NOM HOTEL", "nom"),
            new AbstractFxHelperItem("ETAT", "accepte")};
    }

    public HotelHelperEspace(TableView<Hotel> table, List<Hotel> list) {
        super(titres, table, list);
    }

    public HotelHelperEspace(TableView<Hotel> table) {
        super(titres, table);
    }

}
