/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import util.Session;

/**
 *
 * @author ismail boulaanait
 */
public class UserService extends AbstractFacade<User>{
    
    public UserService() {
        super(User.class);
    }
    
    
    
    public User addUser(String nom , String password , String cin){
        String query = "select u from User u where u.cin = '"+cin+"'";
        User user = getSingleResult(query);
        if (user == null) {
            user = new User(nom, password, cin, "client");
            create(user);
            return user;
        }else{
            return null;
        }
    }
    Session session = new Session();
    
    public int connect(String nom, String password) {
        User user = getSingleResult("select u from User u where u.nom ='"+nom+"'");
        if (user == null) {
            return -1;
        } else if (!user.getPassword().equals(password)) {
            return -2;
        }else if (user.getType().equals("admin")) {
            Session.registreUser(user);
            return 1;
        }else{
            Session.registreUser(user);
            return 2;
        }
    }
    
}
