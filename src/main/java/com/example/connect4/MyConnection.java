package com.example.connect4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connect(){
        String nom_driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1/game";
        String user = "root";
        String password = "";
        Connection con = null;
        try {
            Class.forName(nom_driver);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR DRIVER"+e.getMessage());
        }
        try {
            con = DriverManager.getConnection(url,user,password);
            System.out.println("CONNECTED");
        } catch (SQLException e) {
            System.out.println("ECHEC CONNECTION");
        }
        return con;

    }

}
