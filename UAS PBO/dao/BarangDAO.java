package dao;

import model.Barang;
import java.util.List;

public interface BarangDAO {
    void insert(Barang barang);
    void update(Barang barang);
    void delete(int id);
    Barang getById(int id);
    List<Barang> getAll();
}