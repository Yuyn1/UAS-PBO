import config.Koneksi;
import dao.BarangDAO;
import daoimpl.BarangDAOImpl;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import model.Barang;

public class Main {
    private static BarangDAO barangDAO;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Connection conn = Koneksi.getKoneksi();
        
        if (conn == null) {
            System.out.println("Gagal terhubung ke database. Program keluar.");
            return;
        }

        barangDAO = new BarangDAOImpl(conn);
        int pilihan;

        do {
            System.out.println("\n=================================");
            System.out.println("   MENU Y&U FASHION STORE ");
            System.out.println("=================================");
            System.out.println("1. Tampilkan Semua Barang (Read)");
            System.out.println("2. Tambah Barang Baru (Create)");
            System.out.println("3. Ubah Data Barang (Update)");
            System.out.println("4. Hapus Barang (Delete)");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilih menu [0-4]: ");
            
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer enter

            switch (pilihan) {
                case 1:
                    tampilkanBarang();
                    break;
                case 2:
                    tambahBarang();
                    break;
                case 3:
                    ubahBarang();
                    break;
                case 4:
                    hapusBarang();
                    break;
                case 0:
                    System.out.println("Terima kasih telah menggunakan aplikasi!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    // 1. READ
    private static void tampilkanBarang() {
        List<Barang> daftarBarang = barangDAO.getAll();
        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("| %-3s | %-15s | %-12s | %-10s | %-5s |\n", "ID", "Nama Barang", "Kategori", "Harga", "Stok");
        System.out.println("-------------------------------------------------------------");
        for (Barang b : daftarBarang) {
            System.out.printf("| %-3d | %-15s | %-12s | Rp%-8.0f | %-5d |\n", 
                b.getId(), b.getNamaBarang(), b.getKategori(), b.getHarga(), b.getStok());
        }
        System.out.println("-------------------------------------------------------------");
    }

    // 2. CREATE
    private static void tambahBarang() {
        System.out.println("\n--- Tambah Barang Baru ---");
        System.out.print("Nama Barang : ");
        String nama = scanner.nextLine();
        System.out.print("Kategori    : ");
        String kategori = scanner.nextLine();
        System.out.print("Harga       : ");
        double harga = scanner.nextDouble();
        System.out.print("Stok        : ");
        int stok = scanner.nextInt();

        Barang barangBaru = new Barang(0, nama, kategori, harga, stok);
        barangDAO.insert(barangBaru);
    }

    // 3. UPDATE
    private static void ubahBarang() {
        System.out.println("\n--- Ubah Data Barang ---");
        System.out.print("Masukkan ID Barang yang akan diubah: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Barang barangLama = barangDAO.getById(id);
        if (barangLama == null) {
            System.out.println("Barang dengan ID " + id + " tidak ditemukan!");
            return;
        }

        System.out.print("Nama Baru (" + barangLama.getNamaBarang() + "): ");
        String nama = scanner.nextLine();
        System.out.print("Kategori Baru (" + barangLama.getKategori() + "): ");
        String kategori = scanner.nextLine();
        System.out.print("Harga Baru (Rp" + barangLama.getHarga() + "): ");
        double harga = scanner.nextDouble();
        System.out.print("Stok Baru (" + barangLama.getStok() + "): ");
        int stok = scanner.nextInt();

        Barang barangUpdate = new Barang(id, nama, kategori, harga, stok);
        barangDAO.update(barangUpdate);
    }

    // 4. DELETE
    private static void hapusBarang() {
        System.out.println("\n--- Hapus Barang ---");
        System.out.print("Masukkan ID Barang yang akan dihapus: ");
        int id = scanner.nextInt();

        Barang barang = barangDAO.getById(id);
        if (barang == null) {
            System.out.println("Barang dengan ID " + id + " tidak ditemukan!");
            return;
        }

        barangDAO.delete(id);
    }
}