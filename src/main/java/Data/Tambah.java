
package Data;

public class Tambah {

    private final String kode;
    private final Kategori kategori;

    public Tambah(String kode, Kategori kategori) {

        if (kode == null || kode.isBlank() || kategori == null) {
            throw new IllegalArgumentException(
                "Data produk tidak lengkap."
            );
        }

        this.kode = kode;
        this.kategori = kategori;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return kategori.getNamaProduk();
    }

    public int getHarga() {
        return kategori.getHarga();
    }

    public Kategori getKategori() {
        return kategori;
    }

    public String getJenis() {

        if (kategori instanceof KategoriMakanan) {
            return "Makanan";
        } else if (kategori instanceof KategoriMinuman) {
            return "Minuman";
        }

        return "Tidak diketahui";
    }
}
