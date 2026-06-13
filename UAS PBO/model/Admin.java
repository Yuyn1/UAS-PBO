package model;

public class admin {
    private int idAdmin;
    private String username;
    private String password;

    public admin(){};

    public admin(int idAdmin, String username, String password){
        this.idAdmin = idAdmin;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return idAdmin;
    }
    public void setId(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
