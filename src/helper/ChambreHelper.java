package helper;

import bean.Chambre;
import java.util.List;

import javafx.scene.control.TableView;

public class ChambreHelper extends AbstractFxHelper<Chambre> {

    private static AbstractFxHelperItem[] titres;

    static {

        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("NUMERO", "numero"),
            new AbstractFxHelperItem("NOMBRE DE PLACE", "nbrLit"),
            new AbstractFxHelperItem("PRIX", "prix")};
    }

    public ChambreHelper(TableView<Chambre> table, List<Chambre> list) {
        super(titres, table, list);
    }

    public ChambreHelper(TableView<Chambre> table) {
        super(titres, table);
    }

}
