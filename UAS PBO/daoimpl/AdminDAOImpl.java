package daoimpl;

import dao.AdminDAO;
import model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public Admin login(String username, String password) {

    String sql =
        "SELECT * FROM admin WHERE username=? AND password=?";

    try {
        PreparedStatement ps =
            conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            Admin admin = new Admin();

            admin.setIdAdmin(rs.getInt("id_admin"));
            admin.setUsername(rs.getString("username"));

            return admin;
        }

    } catch(Exception e) {
        e.printStackTrace();
    }

    return null;
}
