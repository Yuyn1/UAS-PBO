package service;

import java.util.List;

import dao.barangDAO;
import model.Barang;

public class BarangService {

    private final barangDAO dao;

    public BarangService(barangDAO dao) {
        this.dao = dao;
    }

    private String formatKategori(String kategori) {

        kategori = kategori.trim().toLowerCase();

        switch (kategori) {
            case "atasan":
                return "Atasan";

            case "bawahan":
                return "Bawahan";

            case "outwear":
                return "Outwear";

            case "alas kaki":
                return "Alas Kaki";

            case "aksesoris":
                return "Aksesoris";

            default:
                throw new IllegalArgumentException(
                    "Kategori harus: Atasan, Bawahan, Outwear, Alas Kaki, atau Aksesoris"
                );
        }
    }

    public void tambahBarang(Barang b) {

        if (b.getNamaBarang() == null || b.getNamaBarang().isBlank())
            throw new IllegalArgumentException("Nama barang tidak boleh kosong");

        b.setKategori(formatKategori(b.getKategori()));

        if (b.getHarga() <= 0)
            throw new IllegalArgumentException("Harga harus lebih dari 0");

        if (b.getStok() < 0)
            throw new IllegalArgumentException("Stok tidak boleh negatif");

        dao.insert(b);
    }

    public List<Barang> semuaBarang() {
        return dao.getAll();
    }

    public Barang cariBarang(int id) {

        if (id <= 0)
            throw new IllegalArgumentException("ID harus bernilai positif");

        Barang b = dao.getById(id);

        if (b == null)
            throw new IllegalArgumentException(
                "Barang dengan ID " + id + " tidak ditemukan"
            );

        return b;
    }

    public void ubahBarang(Barang b) {

        if (b.getId() <= 0)
            throw new IllegalArgumentException("ID tidak valid");

        if (b.getNamaBarang() == null || b.getNamaBarang().isBlank())
            throw new IllegalArgumentException("Nama barang tidak boleh kosong");

        b.setKategori(formatKategori(b.getKategori()));

        if (b.getHarga() <= 0)
            throw new IllegalArgumentException("Harga harus lebih dari 0");

        if (b.getStok() < 0)
            throw new IllegalArgumentException("Stok tidak boleh negatif");

        dao.update(b);
    }

    public void hapusBarang(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID barang tidak valid");
        }

        Barang barang = dao.getById(id);

        if (barang == null) {
            throw new IllegalArgumentException("ID barang tidak ada");
        }

        dao.delete(id);
    }
}
