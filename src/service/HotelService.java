/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Chambre;
import bean.Hotel;
import bean.HotelType;
import bean.Pays;
import bean.User;
import bean.Ville;
import java.util.ArrayList;
import java.util.List;
import util.Session;

/**
 *
 * @author ismail boulaanait
 */
public class HotelService extends AbstractFacade<Hotel> {

    public HotelService() {
        super(Hotel.class);
    }
    ReservationService rs = new ReservationService();
    ChambreService cs = new ChambreService();
    ReviewService rvs = new ReviewService();

    public Hotel addHotel(String nom, String phone, Ville ville, HotelType type) {
        Hotel hotel = new Hotel(nom, phone, false, ville, (User) Session.getConnectedUser(), type);
        create(hotel);
        return hotel;
    }

    public Hotel searchHotelById(long id) {
        return find(id);
    }

    public List<Hotel> searchDemandeHotel() {
        String query = "select h from Hotel h where h.accepte='" + false + "'";
        return getEntityManager().createQuery(query).getResultList();
    }

    public void accepteDemande(Hotel hotel) {
        hotel.setAccepte(true);
        edit(hotel);
    }

    public int verifierAccepte(Hotel hotel) {
        if (hotel.isAccepte() == true) {
            return 1;
        }
        if (hotel.isAccepte() == false) {
            return -1;
        } else {
            return 0;
        }
    }

//    public List<Hotel> searchByCriteria(Ville ville , HotelType type){
//        String query = constractQuery( ville ,type);
//        List<Hotel> res = new ArrayList();
//        res = getEntityManager().createQuery(query).getResultList();
//        return res ;
//    }
//    
//    public String constractQuery(Ville ville , HotelType type){
//        String query = "select h from Hotel h  WHERE h.accepte='true' and h.ville.id='"+ville.getId()+"'";
//        if (type != null) {
//            query += "AND h.type.id='"+type.getId()+"'";
//        }
//        
//        return query;
//    }
    public List<Hotel> searchByCriteria(Pays pays ,Ville ville, HotelType type, Integer nbrlit, Integer prixMin, Integer prixMax) {
        List<Chambre> res = new ArrayList();
        res = cs.searchByCriteria( pays ,ville, type, nbrlit, prixMin, prixMax);
        List<Hotel> hot = new ArrayList();
        for (Chambre re : res) {
            Hotel h = re.getHotel();
            if (!hot.contains(h)) {
                hot.add(h);
            }
        }
        return hot;
    }

    public String searchMyHotelQuery(Pays pays ,Ville ville, HotelType cat, Boolean etat, User owner) {
        String query = "select h from Hotel h where h.proprietaire.id='" + owner.getId() + "' ";
        if (pays != null) {
            query += " and h.ville.pays.id='" + pays.getId() + "'";
        }
        if (ville != null) {
            query += " and h.ville.id='" + ville.getId() + "'";
        }
        if (cat != null) {
            query += " and h.type.id='" + cat.getId() + "'";
        }
        if (etat != null) {
            query += " and h.accepte='" + etat + "'";
        }
        return query;
    }

    public List<Hotel> searchMyHotel(Pays pays , Ville ville, HotelType cat, Boolean etat, User owner) {
        String query = searchMyHotelQuery(pays , ville, cat, etat, owner);
        return getEntityManager().createQuery(query).getResultList();
    }

    public String searchHotelAdminQuery(Pays pays, Ville ville, HotelType cat, Boolean etat) {
        String query = "select h from Hotel h where 1=1 ";
        if (pays != null) {
            query += "and h.ville.pays.id='" + pays.getId() + "'";
        }
        if (ville != null) {
            query += "and h.ville.id='" + ville.getId() + "'";
        }
        if (cat != null) {
            query += "and h.type.id='" + cat.getId() + "'";
        }
        if (etat != null) {
            query += "and h.accepte='" + etat + "'";
        }
        return query;
    }

    public List<Hotel> SearchHotelAdmin(Pays pays, Ville ville, HotelType cat, Boolean etat) {
        String query = searchHotelAdminQuery(pays, ville, cat, etat);
        return getEntityManager().createQuery(query).getResultList();
    }

    public int bloquerHotel(Hotel hotel) {
        if (hotel.isAccepte() == false) {
            return -1;
        } else {
            hotel.setAccepte(false);
            edit(hotel);
            return 1;
        }
    }
    
    
    
    public int deleteHotel(Hotel hotel){
        rs.suppReservationByHotel(hotel);
        cs.deleteChambreByHotel(hotel);
        rvs.deleteReviewByHotel(hotel);
        return execUpdate("delete from Hotel h where h.id='"+hotel.getId()+"'");
    }
}
