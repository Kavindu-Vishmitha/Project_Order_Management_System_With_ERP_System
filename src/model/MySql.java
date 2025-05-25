/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author sanja
 */
public class MySql {
   
    private static Connection connection;
    
//    Create Connection
    
    public static void CreatConnection () throws Exception {
        
        if (connection == null) {
            
             Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_management_system_with_erp_db","root","*******");
            
        }
        
    }
    
//    Update
    
    public static ResultSet executeSearch (String query) throws Exception {
    
        CreatConnection();
        return connection.createStatement().executeQuery(query);
    
    }
    
//   Insert , Delete
    
    public static Integer executeUpdate (String query) throws Exception {
    
        CreatConnection();
        return connection.createStatement().executeUpdate(query);
        
    }
    
}