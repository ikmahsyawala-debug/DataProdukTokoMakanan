
package Model;

import Data.Kategori;
import Data.KategoriMakanan;
import Data.KategoriMinuman;
import Data.Tambah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data {

    private static final Data INSTANCE = new Data();

    public static Data getInstance() {
        return INSTANCE;
    }

    private final ObservableList<Tambah> daftar =
            FXCollections.observableArrayList();

    private int urutan = 1;

    private Data() {
    }

    public ObservableList<Tambah> getDaftar() {
        return daftar;
    }

    // Menambahkan produk
    public Tambah tambah(String nama, String jenis, int harga) {

        if (jenis == null || jenis.isBlank()) {
            throw new IllegalArgumentException(
                "Kategori wajib dipilih."
            );
        }

        Kategori k;

        if (jenis.equals("Makanan")) {

            k = new KategoriMakanan(nama, harga);

        } else if (jenis.equals("Minuman")) {

            k = new KategoriMinuman(nama, harga);

        } else {

            throw new IllegalArgumentException(
                "Kategori tidak valid."
            );
        }

        Tambah p = new Tambah(
            String.format("PR%03d", urutan),
            k
        );

        daftar.add(p);
        urutan++;

        return p;
    }

    // Menghapus produk
    public void hapus(Tambah p) {
        daftar.remove(p);
    }
}
