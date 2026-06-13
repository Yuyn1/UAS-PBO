package service; // Baris 1: Wajib huruf kecil semua sesuai nama folder

import dao.AdminDAO;
import model.Admin;

public class AdminService { // Baris 5: Nama class WAJIB sama persis dengan nama file
    private final AdminDAO adminDAO;

    public AdminService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public String login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return "";
        }
        
        Admin admin = adminDAO.login(username, password);
        
        if (admin != null) {
            return "admin"; 
        }
        
        return "";
    }
}
