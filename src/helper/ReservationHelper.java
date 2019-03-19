package helper;

import bean.Reservation;
import java.util.List;

import javafx.scene.control.TableView;

public class ReservationHelper extends AbstractFxHelper<Reservation> {

    private static AbstractFxHelperItem[] titres;

    static {

        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("DATE RESERVATION", "DateReservation"),
            new AbstractFxHelperItem("DATE DEBUT", "DateArrive"),
            new AbstractFxHelperItem("DATE FIN", "DateDepart"),
            new AbstractFxHelperItem("CHAMBRE", "Chambre")};
    }

    public ReservationHelper(TableView<Reservation> table, List<Reservation> list) {
        super(titres, table, list);
    }

    public ReservationHelper(TableView<Reservation> table) {
        super(titres, table);
    }

}
