public class AdminService {

    private AdminDAO adminDAO;

    public AdminService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public String login(String username, String password) {
        return adminDAO.login(username, password);
    }
}
