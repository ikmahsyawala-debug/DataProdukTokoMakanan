
package Data;

public class Kategori {

    private String namaProduk;
    private int harga;

    public Kategori(String nama, int harga) {

        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException(
                "Nama wajib diisi."
            );
        }

        if (harga <= 0) {
            throw new IllegalArgumentException(
                "Harga harus lebih dari nol."
            );
        }

        this.namaProduk = nama.trim();
        this.harga = harga;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public int getHarga() {
        return harga;
    }
}
