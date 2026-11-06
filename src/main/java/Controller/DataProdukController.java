
package Controller;

import Data.Tambah;
import Model.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DataProdukController implements Initializable {

    @FXML private TextField txtCariProduk;

    @FXML private ComboBox<String> cmdKategori;

    @FXML private Button btnCari;
    @FXML private Button btnTambah;
    @FXML private Button btnLihatDetail;
    @FXML private Button btnReset;
    @FXML private Button btnHapus;

    @FXML private CheckBox chxTotal;
    @FXML private CheckBox chxJumlah;

    @FXML private TableView<Tambah> tbProduk;

    @FXML private TableColumn<Tambah, String> thKode;
    @FXML private TableColumn<Tambah, String> thNama;
    @FXML private TableColumn<Tambah, String> thLategori;
    @FXML private TableColumn<Tambah, Integer> thHarga;

    private final Data data = Data.getInstance();

    private FilteredList<Tambah> terfilter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Mengisi kategori
        cmdKategori.getItems().addAll(
            "Makanan",
            "Minuman"
        );

        // Menghubungkan kolom tabel dengan getter
        thKode.setCellValueFactory(
            new PropertyValueFactory<>("kode")
        );

        thNama.setCellValueFactory(
            new PropertyValueFactory<>("nama")
        );

        thLategori.setCellValueFactory(
            new PropertyValueFactory<>("jenis")
        );

        thHarga.setCellValueFactory(
            new PropertyValueFactory<>("harga")
        );

        // Menampilkan data produk
        terfilter = new FilteredList<>(
            data.getDaftar(),
            p -> true
        );

        tbProduk.setItems(terfilter);

        // Event tombol
        btnCari.setOnAction(e -> cari());

        btnReset.setOnAction(e -> reset());

        btnHapus.setOnAction(e -> hapus());

        btnLihatDetail.setOnAction(e -> lihatDetail());

        btnTambah.setOnAction(e -> {
            bukaHalaman("/FXML/Daftar.fxml");
        });
    }

    // Pencarian produk
    private void cari() {

        String kata = txtCariProduk.getText() == null
                ? ""
                : txtCariProduk.getText()
                        .toLowerCase()
                        .trim();

        String jenis = cmdKategori.getValue();

        terfilter.setPredicate(p -> {

            boolean cocokNama =
                    kata.isEmpty() ||
                    p.getNama().toLowerCase().contains(kata);

            boolean cocokKategori =
                    jenis == null ||
                    p.getJenis().equals(jenis);

            return cocokNama && cocokKategori;
        });
    }

    // Reset pencarian
    private void reset() {

        txtCariProduk.clear();
        cmdKategori.setValue(null);

        terfilter.setPredicate(p -> true);
    }

    // Menghapus produk
    private void hapus() {

        Tambah dipilih = tbProduk
                .getSelectionModel()
                .getSelectedItem();

        if (dipilih == null) {

            new Alert(
                Alert.AlertType.WARNING,
                "Pilih produk dulu."
            ).showAndWait();

            return;
        }

        Alert konfirmasi = new Alert(
            Alert.AlertType.CONFIRMATION,
            "Yakin ingin menghapus produk "
            + dipilih.getNama() + "?"
        );

        konfirmasi.setHeaderText("Konfirmasi Hapus");

        if (konfirmasi.showAndWait()
                .orElse(ButtonType.CANCEL) == ButtonType.OK) {

            data.hapus(dipilih);
        }
    }

    // Melihat detail produk
    private void lihatDetail() {

        // Mengambil produk yang dipilih
        Tambah dipilih = tbProduk
                .getSelectionModel()
                .getSelectedItem();

        if (dipilih == null) {

            new Alert(
                Alert.AlertType.WARNING,
                "Pilih produk dulu."
            ).showAndWait();

            return;
        }

        try {

            // Memuat halaman Detail Produk
            URL lokasi = getClass().getResource(
                "/FXML/DetailProduk.fxml"
            );

            // Memastikan file ditemukan
            if (lokasi == null) {

                throw new IOException(
                    "File DetailProduk.fxml tidak ditemukan!"
                );
            }

            FXMLLoader loader = new FXMLLoader(lokasi);

            Parent root = loader.load();

            // Mengambil controller Detail Produk
            DetailProdukController controller =
                    loader.getController();

            // Mengirim data produk
            controller.setData(dipilih);

            // Mengganti halaman
            Stage stage = (Stage) btnLihatDetail
                    .getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {

            ex.printStackTrace();

            Alert alert = new Alert(
                Alert.AlertType.ERROR
            );

            alert.setTitle("Error");
            alert.setHeaderText(
                "Gagal membuka Detail Produk"
            );

            alert.setContentText(
                "Jenis Error: "
                + ex.getClass().getSimpleName()
                + "\n\nPesan Error: "
                + ex.getMessage()
            );

            alert.showAndWait();
        }
    }

    // Navigasi halaman
    private void bukaHalaman(String lokasi) {

        try {

            URL url = getClass().getResource(lokasi);

            if (url == null) {
                throw new IOException(
                    "File tidak ditemukan: " + lokasi
                );
            }

            FXMLLoader loader = new FXMLLoader(url);

            Parent root = loader.load();

            Stage stage = (Stage) btnTambah
                    .getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {

            ex.printStackTrace();

            new Alert(
                Alert.AlertType.ERROR,
                "Gagal membuka halaman: "
                + ex.getMessage()
            ).showAndWait();
        }
    }
}
