/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Chambre;
import bean.Hotel;
import bean.Reservation;
import bean.User;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ismail boulaanait
 */
public class ReservationService extends AbstractFacade<Reservation>{
    
    public ReservationService() {
        super(Reservation.class);
    }
    
    public Reservation reserver(Date deteReservation ,Date dateArrive , Date DateDepart , User client , Chambre chambre){
        Reservation res = new Reservation(deteReservation, dateArrive, DateDepart, client, chambre);
        create(res);
        return res;
    }
    
    public Reservation searchReservationById(long id){
        Reservation res = find(id);
        return res;
    }
    
    public int suppReservationByHotel(Hotel hotel){
        String query = "delete from Reservation r where r.chambre.hotel.id='"+hotel.getId()+"'";
        return execUpdate(query);
    }
    
    public List<Reservation> searchReservation(User client , Boolean commented){
        String query = "select r from Reservation r where r.client.id='"+client.getId()+"' and r.commented='"+commented+"'";
        return getEntityManager().createQuery(query).getResultList();
    } 
}
