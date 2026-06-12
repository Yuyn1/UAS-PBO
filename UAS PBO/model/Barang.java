package model;

public class Barang {
    private int id;
    private String namaBarang;
    private String kategori;
    private double harga;
    private int stok;

    // Constructor Kosong
    public Barang() {}

    // Constructor Parameter
    public Barang(int id, String namaBarang, String kategori, double harga, int stok) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}