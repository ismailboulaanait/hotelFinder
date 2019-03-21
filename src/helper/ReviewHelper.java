package helper;

import bean.Review;
import java.util.List;

import javafx.scene.control.TableView;

public class ReviewHelper extends AbstractFxHelper<Review> {

    private static AbstractFxHelperItem[] titres;

    static {

        titres = new AbstractFxHelperItem[]{
            
            new AbstractFxHelperItem("COMMENTAIRE", "commentaire")};
    }

    public ReviewHelper(TableView<Review> table, List<Review> list) {
        super(titres, table, list);
    }

    public ReviewHelper(TableView<Review> table) {
        super(titres, table);
    }

}
