/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Pays;
import bean.Ville;
import java.util.List;

/**
 *
 * @author ismail boulaanait
 */
public class VilleService extends AbstractFacade<Ville>{
    
    public VilleService() {
        super(Ville.class);
    }
    
    PaysService ps = new PaysService();
    
   public List<Ville> findVillesByPays(long idPays){
       String query = "select v from Ville v where v.pays.id='"+idPays+"'";
       return getEntityManager().createQuery(query).getResultList();
   }
   
   
    
    
    
}
