package helper;

import bean.Hotel;
import java.util.List;

import javafx.scene.control.TableView;

public class HotelFxHelper extends AbstractFxHelper<Hotel> {

    private static AbstractFxHelperItem[] titres;

    static {

        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("hotel", "id"),
            new AbstractFxHelperItem("phone number", "phone")};
    }

    public HotelFxHelper(TableView<Hotel> table, List<Hotel> list) {
        super(titres, table, list);
    }

    public HotelFxHelper(TableView<Hotel> table) {
        super(titres, table);
    }

}
