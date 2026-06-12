package daoimpl;

import dao.BarangDAO;
import model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAOImpl implements BarangDAO {
    private Connection conn;

    // Constructor untuk menerima koneksi database
    public BarangDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Barang barang) {
        String sql = "INSERT INTO barang (nama_barang, kategori, harga, stok) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barang.getNamaBarang());
            ps.setString(2, barang.getKategori());
            ps.setDouble(3, barang.getHarga());
            ps.setInt(4, barang.getStok());
            ps.executeUpdate();
            System.out.println("Data barang berhasil ditambahkan!");
        } catch (SQLException e) {
            System.out.println("Gagal tambah barang: " + e.getMessage());
        }
    }

    @Override
    public void update(Barang barang) {
        String sql = "UPDATE barang SET nama_barang=?, kategori=?, harga=?, stok=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barang.getNamaBarang());
            ps.setString(2, barang.getKategori());
            ps.setDouble(3, barang.getHarga());
            ps.setInt(4, barang.getStok());
            ps.setInt(5, barang.getId());
            ps.executeUpdate();
            System.out.println("Data barang berhasil diubah!");
        } catch (SQLException e) {
            System.out.println("Gagal update barang: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM barang WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Barang berhasil dihapus!");
        } catch (SQLException e) {
            System.out.println("Gagal hapus barang: " + e.getMessage());
        }
    }

    @Override
    public Barang getById(int id) {
        String sql = "SELECT * FROM barang WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Barang(
                    rs.getInt("id"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                );
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil barang: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Barang b = new Barang(
                    rs.getInt("id"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                );
                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil semua barang: " + e.getMessage());
        }
        return list;
    }
}