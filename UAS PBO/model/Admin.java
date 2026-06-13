package model;

public class Admin {
    private String username;
    private String password;

    // 1. Constructor Kosong (Ini yang dipanggil oleh AdminDAOImpl lewat 'new Admin()')
    public Admin() {
    }

    // 2. Constructor dengan Parameter (Opsional, jika sewaktu-waktu kamu butuh)
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- GETTER & SETTER USERNAME ---
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // --- GETTER & SETTER PASSWORD ---
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
