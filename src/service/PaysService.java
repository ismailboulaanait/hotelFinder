/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Pays;

/**
 *
 * @author ismail boulaanait
 */
public class PaysService extends AbstractFacade<Pays>{
    
    public PaysService() {
        super(Pays.class);
    }
    
    
    public Pays addPays (String pays){
        Pays p = new Pays(pays);
        create(p);
        return p;
    }
}
