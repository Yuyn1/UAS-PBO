package dao;

import model.Admin;
import java.util.List;

public interface AdminDAO {
    Admin login(String username, String password);
}
