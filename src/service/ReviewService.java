/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Hotel;
import bean.Review;
import bean.User;
import java.util.List;

/**
 *
 * @author ismail boulaanait
 */
public class ReviewService extends AbstractFacade<Review>{
    
    public ReviewService() {
        super(Review.class);
    }
    
    public int deleteReviewByHotel(Hotel hotel){
        String query = "delete from Review r where r.hotelRev.id='"+hotel.getId()+"' ";
        return execUpdate(query);
    }
    
    public void createReview(String comment ,int note ,  User client , Hotel hotel){
        Review rev = new Review(comment, note, client, hotel);
        create(rev);
    }
    
    public List<Review> searchReviewByHotel(Hotel hotel){
        String query = "select r from Review r where r.hotelRev.id='"+hotel.getId()+"'";
        return getEntityManager().createQuery(query).getResultList();
    }
    
}
