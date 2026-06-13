import config.Koneksi;
import dao.BarangDAO;
import daoimpl.BarangDAOImpl;
import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Connection conn = Koneksi.getKoneksi();
        if (conn == null) {
            System.out.println("Gagal terhubung ke database. Program keluar.");
            return;
        }

        System.out.println("=========================================");
        System.out.println("    SELAMAT DATANG DI Y&U FASHION STORE   ");
        System.out.println("=========================================");
        
        // 1. Pilihan Masuk Pertama Kali
        System.out.println("Masuk sebagai:");
        System.out.println("1. Admin (Butuh Login)");
        System.out.println("2. User / Pelanggan (Tanpa Login)");
        System.out.print("Pilihan Anda [1-2]: ");
        int jenisMasuk = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        String role = "";

        if (jenisMasuk == 1) {
            // 2. Alur jika memilih Admin (Ada batasan maksimal 3 kali gagal)
            role = jalankanSistemLoginAdmin(conn);
            if (role.isEmpty()) {
                System.out.println("\n[SYSTEM] Anda telah salah 3 kali. Akun diblokir sementara!");
                System.out.println("Program dihentikan secara otomatis.");
                return;
            }
        } else if (jenisMasuk == 2) {
            // 3. Alur jika memilih User (Langsung masuk tanpa password)
            role = "user";
            System.out.println("\nLogin Berhasil! Anda masuk sebagai: USER");
        } else {
            System.out.println("Pilihan tidak valid! Program keluar.");
            return;
        }

        // 4. Masuk ke Menu Utama (Menggunakan MenuHandler yang kemarin)
        BarangDAO barangDAO = new BarangDAOImpl(conn);
        MenuHandler menu = new MenuHandler(barangDAO, scanner);
        menu.setCurrentRole(role);
        menu.tampilkanMenuUtama();
    }

    /**
     * Mengurusi perulangan login admin maksimal 3 kali percobaan
     */
    private static String jalankanSistemLoginAdmin(Connection conn) {
        int percobaan = 1;
        int maksPercobaan = 3;

        while (percobaan <= maksPercobaan) {
            System.out.println("\n--- LOGIN ADMIN (Percobaan " + percobaan + " dari " + maksPercobaan + ") ---");
            System.out.print("Username: "); String username = scanner.nextLine();
            System.out.print("Password: "); String password = scanner.nextLine();

            String roleTerdeteksi = cekKredensialDiDatabase(conn, username, password);
            
            // Jika berhasil login dan perannya memang admin
            if (!roleTerdeteksi.isEmpty() && roleTerdeteksi.equals("admin")) {
                System.out.println("\nLogin Berhasil! Anda masuk sebagai: ADMIN");
                return roleTerdeteksi;
            }

            System.out.println("Username/Password salah atau Anda bukan Admin!");
            percobaan++;
        }
        return ""; // Mengembalikan string kosong jika 3 kali gagal
    }

    /**
     * Fungsi khusus untuk menembak query ke database users
     */
    private static String cekKredensialDiDatabase(Connection conn, String username, String password) {
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error database saat login: " + e.getMessage());
        }
        return "";
    }
}
