package model;

// --- KONSEP: CLASS ---
public class Produk {
    // Atribut yang akan diwariskan ke class anak
    protected int id;
    protected String namaBarang;
    protected String kategori;
    protected double harga;

    // --- KONSEP: CONSTRUCTOR ---
    public Produk(int id, String namaBarang, String kategori, double harga) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.harga = harga;
    }

    // --- KONSEP: METHOD (Getter & Setter) ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
}
