package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_yu_fashionstore";
                String user = "root"; 
                String password = "user"; 

                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi ke Database Y&U Fashion Store BERHASIL!");
                
            } catch (SQLException e) {
                System.out.println("Koneksi GAGAL: " + e.getMessage());
            }
        }
        return koneksi;
    }
}