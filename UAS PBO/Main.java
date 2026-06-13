import config.Koneksi;
import dao.BarangDAO;
import daoimpl.BarangDAOImpl;

import dao.AdminDAO;
import daoimpl.AdminDAOImpl;
import service.AdminService;

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
        
        int jenisMasuk = 0;

        // --- VALIDASI INPUT GERBANG UTAMA (ANTI-CRASH) ---
        while (true) {
            System.out.println("Masuk sebagai:");
            System.out.println("1. Admin (Butuh Login)");
            System.out.println("2. User / Pelanggan (Tanpa Login)");
            System.out.print("Pilihan Anda [1-2]: ");
            
            try {
                jenisMasuk = scanner.nextInt();
                scanner.nextLine(); // Clear buffer setelah input sukses

                // Validasi jika angka yang dimasukkan di luar 1 atau 2
                if (jenisMasuk == 1 || jenisMasuk == 2) {
                    break; // Pilihan benar, keluar dari perulangan while
                } else {
                    System.out.println("\n[PERINGATAN] Pilihan tidak ada! Silakan pilih angka 1 atau 2.\n");
                }
            } catch (java.util.InputMismatchException e) {
                // Menangkap eror jika user mengetik huruf/simbol (seperti ;')
                System.out.println("\n[EROR] Input harus berupa angka! Silakan coba lagi.\n");
                scanner.nextLine(); // Membersihkan sisa buffer karakter salah agar tidak looping terus-menerus
            }
        }

        String role = "";

        if (jenisMasuk == 1) {
            role = jalankanSistemLoginAdmin(conn);
            if (role.isEmpty()) {
                System.out.println("\n[SYSTEM] Anda telah salah 3 kali. Akun diblokir sementara!");
                System.out.println("Program dihentikan secara otomatis.");
                return;
            }
        } else {
            role = "user";
            System.out.println("\nLogin Berhasil! Anda masuk sebagai: USER");
        }

        // Masuk ke Menu Utama
        BarangDAO barangDAO = new BarangDAOImpl(conn);
        MenuHandler menu = new MenuHandler(barangDAO, scanner);
        menu.setCurrentRole(role);
        menu.tampilkanMenuUtama();
    }

private static String jalankanSistemLoginAdmin(Connection conn) {

    int percobaan = 1;
    int maksPercobaan = 3;

    AdminDAO adminDAO = new AdminDAOImpl(conn);
    AdminService adminService = new AdminService(adminDAO);

    while (percobaan <= maksPercobaan) {

        System.out.println("\n--- LOGIN ADMIN (Percobaan "
                + percobaan + " dari " + maksPercobaan + ") ---");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        String roleTerdeteksi =
                adminService.login(username, password);

        if (!roleTerdeteksi.isEmpty()
                && roleTerdeteksi.equals("admin")) {

            System.out.println("\nLogin Berhasil! Anda masuk sebagai: ADMIN");
            return roleTerdeteksi;
        }

        System.out.println("Username/Password salah atau Anda bukan Admin!");
        percobaan++;
    }

    return "";
}

}
