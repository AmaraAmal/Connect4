package com.example.connect4;

import java.sql.*;

public class GameDAO {
    Connection con;
    Statement st = null;

    public void connect() {
        con = MyConnection.connect();
        if (con != null) {
            try {
                st = con.createStatement();
            } catch (SQLException e) {
                System.out.println("Error Statement" + e.getMessage());
            }
        }
    }

    public int insert(String nickname, String pwd) {
        String req = "insert into players(nickname,password,score)values(?,?,0)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(req);
            //ps.setInt(1, id);
            ps.setString(1, nickname);
            ps.setString(2, pwd);
            //ps.setInt(4, sc);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ECHEC INSERT");
            return 0;
        }
    }

    public int update(String pwd, String nickname) {
        String req = "update players set password =? where nickname = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1, pwd);
            ps.setString(2, nickname);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ECHEC UPDATE");
            return 0;
        }
    }

    public int delete(String nickname, String pwd){
        String req = "delete from players where nickname = ? and password = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1, nickname);
            ps.setString(2, pwd);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ECHEC DELETE");
            return 0;
        }
    }

    public ResultSet login(String nickname) {
        String req = "select password from players where nickname=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1, nickname);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("ECHEC SELECT LOGIN");
        }
        return null;
    }


    public String showPassword(ResultSet res) throws SQLException {
        String pwd = null;
        if (res != null) {
            while (res.next()) {
                pwd = res.getString(1);
            }
        }
        return pwd;
    }

    public int setScore(String nickname) {
        String req = "update players set score=score+1 where nickname = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1, nickname);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ECHEC UPDATE");
            return 0;
        }
    }

}