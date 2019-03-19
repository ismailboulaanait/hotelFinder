/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ismail
 */
public class UserServiceTest {
    
    public UserServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class UserService.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        String nom = "ismail";
        String password = "123456";
        String cin = "jm68664";
        UserService instance = new UserService();
        User result = instance.addUser(nom, password, cin);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of connect method, of class UserService.
     */
//    @Test
//    public void testConnect() {
//        System.out.println("connect");
//        String nom = "";
//        String password = "";
//        UserService instance = new UserService();
//        int expResult = 0;
//        int result = instance.connect(nom, password);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
