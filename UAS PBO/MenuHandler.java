import dao.BarangDAO;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import model.Barang;

public class MenuHandler {
    private final BarangDAO barangDAO;
    private final Scanner scanner;
    private String currentRole;

    public MenuHandler(BarangDAO barangDAO, Scanner scanner) {
        this.barangDAO = barangDAO;
        this.scanner = scanner;
    }

    public void setCurrentRole(String role) {
        this.currentRole = role;
    }

    public void tampilkanMenuUtama() {
        int pilihan;
        do {
            cetakHeaderMenu();
            pilihan = ambilPilihanMenu();

            if (isAksesDitolak(pilihan)) {
                System.out.println("Akses Ditolak! Menu ini hanya untuk Admin.");
                continue;
            }

            eksekusiFitur(pilihan);
        } while (pilihan != 0);
    }

    private void cetakHeaderMenu() {
        System.out.println("\n=================================");
        System.out.println("   MENU UTAMA (" + currentRole.toUpperCase() + ") ");
        System.out.println("=================================");
        
        System.out.println("1. Tampilkan Semua Barang (Read)");
        if (currentRole.equals("admin")) {
            System.out.println("2. Tambah Barang Baru (Create)");
            System.out.println("3. Ubah Data Barang (Update)");
            System.out.println("4. Hapus Barang (Delete)");
            System.out.println("5. Input Transaksi Baru & Diskon (New Transaction)");
        } else {
            // --- FITUR BARU UNTUK USER ---
            System.out.println("2. Cari Barang Berdasarkan Nama (Search)");
            System.out.println("3. Simulasi Hitung Keranjang Belanja (Cart Simulation)");
        }
        System.out.println("0. Keluar Aplikasi");
    }

    private int ambilPilihanMenu() {
        System.out.print("Pilih menu: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return pilihan;
    }

    private boolean isAksesDitolak(int pilihan) {
        return currentRole.equals("user") && pilihan > 3;
    }

    private void eksekusiFitur(int pilihan) {
        if (currentRole.equals("admin")) {
            switch (pilihan) {
                case 1 -> tampilkanBarang();
                case 2 -> tambahBarang();
                case 3 -> ubahBarang();
                case 4 -> hapusBarang();
                case 5 -> prosesTransaksi();
                case 0 -> System.out.println("Terima kasih telah menggunakan aplikasi!");
                default -> System.out.println("Pilihan tidak valid!");
            }
        } else {
            switch (pilihan) {
                case 1 -> tampilkanBarang();
                case 2 -> cariBarangUser();      // <-- Fungsi Baru
                case 3 -> simulasiKeranjang();    // <-- Fungsi Baru
                case 0 -> System.out.println("Terima kasih telah menggunakan aplikasi!");
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void tampilkanBarang() {
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

    private void tambahBarang() {
        System.out.println("\n--- Tambah Barang Baru ---");
        System.out.print("Nama Barang : "); String nama = scanner.nextLine();
        System.out.print("Kategori    : "); String kategori = scanner.nextLine();
        System.out.print("Harga       : "); double harga = scanner.nextDouble();
        System.out.print("Stok        : "); int stok = scanner.nextInt();
        
        barangDAO.insert(new Barang(nama, kategori, harga, stok));
    }

    private void ubahBarang() {
        System.out.println("\n--- Ubah Data Barang ---");
        System.out.print("Masukkan ID Barang: "); int id = scanner.nextInt(); scanner.nextLine();
        
        Barang bLama = barangDAO.getById(id);
        if (bLama == null) { 
            System.out.println("Barang tidak ditemukan!"); 
            return; 
        }
        
        System.out.print("Nama Baru (" + bLama.getNamaBarang() + "): "); String nama = scanner.nextLine();
        System.out.print("Kategori Baru (" + bLama.getKategori() + "): "); String kategori = scanner.nextLine();
        System.out.print("Harga Baru (Rp" + bLama.getHarga() + "): "); double harga = scanner.nextDouble();
        System.out.print("Stok Baru (" + bLama.getStok() + "): "); int stok = scanner.nextInt();
        
        barangDAO.update(new Barang(id, nama, kategori, harga, stok));
    }

    private void hapusBarang() {
        System.out.println("\n--- Hapus Barang ---");
        System.out.print("Masukkan ID Barang: "); int id = scanner.nextInt();
        
        if (barangDAO.getById(id) == null) { 
            System.out.println("Barang tidak ditemukan!"); 
            return; 
        }
        barangDAO.delete(id);
    }

    private void prosesTransaksi() {
        System.out.println("\n--- INPUT TRANSAKSI BARU ---");
        System.out.print("Masukkan ID Barang yang dibeli: ");
        int idBarang = scanner.nextInt();
        
        Barang barang = barangDAO.getById(idBarang);
        if (barang == null) {
            System.out.println("Barang tidak ditemukan!");
            return;
        }

        System.out.println("Barang yang dipilih: " + barang.getNamaBarang() + " (Harga: Rp" + barang.getHarga() + ")");
        System.out.print("Masukkan Jumlah Beli: ");
        int jumlah = scanner.nextInt();

        if (jumlah > barang.getStok()) {
            System.out.println("Transaksi Gagal! Stok tidak mencukupi (Sisa stok: " + barang.getStok() + ")");
            return;
        }

        double totalKotor = barang.getHarga() * jumlah;
        double diskon = 0;

        if (totalKotor >= 300000) {
            diskon = totalKotor * 0.10; 
            System.out.println("Selamat! Mendapatkan Diskon 10% karena belanja di atas Rp300.000");
        }

        double totalBersih = totalKotor - diskon;

        System.out.println("\n------------------------------------");
        System.out.println("Total Awal   : Rp" + totalKotor);
        System.out.println("Diskon       : Rp" + diskon);
        System.out.println("Total Bayar  : Rp" + totalBersih);
        System.out.println("------------------------------------");
        System.out.print("Konfirmasi Bayar? (1 = Ya, 0 = Batal): ");
        int konfirmasi = scanner.nextInt();

        if (konfirmasi == 1) {
            barang.setStok(barang.getStok() - jumlah);
            barangDAO.update(barang); 
            simpanTransaksiKeDatabase(idBarang, jumlah, totalBersih);
            System.out.println("Transaksi Berhasil Disimpan & Stok Diperbarui!");
        } else {
            System.out.println("Transaksi dibatalkan.");
        }
    }

    private void simpanTransaksiKeDatabase(int barangId, int jumlah, double totalBayar) {
        String insertTransaksi = "INSERT INTO transaksi (total_bayar) VALUES (?)";
        String insertDetail = "INSERT INTO detail_transaksi (transaksi_id, barang_id, jumlah, subtotal) VALUES (?, ?, ?, ?)";
        
        try {
            Connection conn = config.Koneksi.getKoneksi();
            if (conn == null) return;
            
            try (PreparedStatement psTrans = conn.prepareStatement(insertTransaksi, Statement.RETURN_GENERATED_KEYS)) {
                psTrans.setDouble(1, totalBayar);
                psTrans.executeUpdate();
                
                try (ResultSet rs = psTrans.getGeneratedKeys()) {
                    if (rs.next()) {
                        int transaksiId = rs.getInt(1);
                        
                        try (PreparedStatement psDetail = conn.prepareStatement(insertDetail)) {
                            psDetail.setInt(1, transaksiId);
                            psDetail.setInt(2, barangId);
                            psDetail.setInt(3, jumlah);
                            psDetail.setDouble(4, totalBayar);
                            psDetail.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan riwayat transaksi: " + e.getMessage());
        }
    }
    // --- FITUR USER 1: CARI BARANG ---
    private void cariBarangUser() {
        System.out.println("\n--- CARI BARANG ---");
        System.out.print("Masukkan kata kunci nama barang: ");
        String keyword = scanner.nextLine();

        List<Barang> semuaBarang = barangDAO.getAll();
        boolean ditemukan = false;

        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("| %-3s | %-15s | %-12s | %-10s | %-5s |\n", "ID", "Nama Barang", "Kategori", "Harga", "Stok");
        System.out.println("-------------------------------------------------------------");
        
        for (Barang b : semuaBarang) {
            // Mengecek apakah nama barang mengandung kata kunci (tanpa peduli huruf besar/kecil)
            if (b.getNamaBarang().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.printf("| %-3d | %-15s | %-12s | Rp%-8.0f | %-5d |\n", 
                    b.getId(), b.getNamaBarang(), b.getKategori(), b.getHarga(), b.getStok());
                ditemukan = true;
            }
        }
        System.out.println("-------------------------------------------------------------");
        
        if (!ditemukan) {
            System.out.println("Barang dengan kata kunci '" + keyword + "' tidak ditemukan.");
        }
    }

    // --- FITUR USER 2: SIMULASI KERANJANG ---
    private void simulasiKeranjang() {
        System.out.println("\n--- SIMULASI KERANJANG BELANJA ---");
        System.out.print("Masukkan ID Barang yang ingin dibeli: ");
        int id = scanner.nextInt();

        Barang barang = barangDAO.getById(id);
        if (barang == null) {
            System.out.println("Barang tidak ditemukan!");
            return;
        }

        System.out.println("Barang terpilih: " + barang.getNamaBarang() + " (Harga: Rp" + barang.getHarga() + ")");
        System.out.print("Masukkan Jumlah: ");
        int jumlah = scanner.nextInt();

        if (jumlah > barang.getStok()) {
            System.out.println("Maaf, stok tidak mencukupi. Sisa stok: " + barang.getStok());
            return;
        }

        double total = barang.getHarga() * jumlah;
        System.out.println("\n=================================");
        System.out.println("   ESTIMASI NOTA BELANJA ANDA    ");
        System.out.println("=================================");
        System.out.println(" Produk      : " + barang.getNamaBarang());
        System.out.println(" Harga Satuan: Rp" + barang.getHarga());
        System.out.println(" Jumlah Beli : " + jumlah + " pcs");
        System.out.println("---------------------------------");
        System.out.println(" Total Biaya : Rp" + total);
        System.out.println("=================================");
        System.out.println("Silakan tunjukkan estimasi ini ke kasir untuk pembayaran.");
    }
} // Kurung kurawal penutup class luar berada tepat di sini
