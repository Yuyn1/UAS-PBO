package daoimpl;

import dao.AdminDAO;
import java.sql.Connection; // Kita import model Admin kamu
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Admin;

public class AdminDAOImpl implements AdminDAO {
    private final Connection conn;

    public AdminDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Admin login(String username, String password) {
        // Query disesuaikan untuk mengambil seluruh data admin (id, username, password, role)
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 1. Gunakan constructor kosong bawaan Java (Pasti Aman & Anti-Bentrokan Parameter)
                    Admin admin = new Admin();
                    
                    // 2. Isi datanya satu per satu menggunakan method setter standar
                    admin.setUsername(rs.getString("username"));
                    admin.setPassword(rs.getString("password"));
                    
                    return admin; 
                }
            }
        } catch (SQLException e) {
            System.out.println("Error query database AdminDAOImpl: " + e.getMessage());
        }
        return null; // Kembalikan null jika username/password tidak ditemukan
    }
}
