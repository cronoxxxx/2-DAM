package org.example.Others.BaseDatos;

import java.sql.*;

public class Database {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba","root", "doge7777xd");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONA");

            while (rs.next()) {
                System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
