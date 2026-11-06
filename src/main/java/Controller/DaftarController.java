
package Controller;

import Model.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DaftarController implements Initializable {

    @FXML private Button btnLihatProduk;
    @FXML private TextField txtNamaProduk;
    @FXML private ComboBox<String> cmdKategoriProduk;
    @FXML private TextField txtHargaProduk;
    @FXML private Button btnsimpan;

    private final Data data = Data.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Mengisi kategori
        cmdKategoriProduk.getItems().addAll(
            "Makanan",
            "Minuman"
        );

        // Event tombol
        btnsimpan.setOnAction(e -> simpan());

        btnLihatProduk.setOnAction(e -> {
            bukaHalaman("/FXML/DataProduk.fxml");
        });
    }

    // Menyimpan produk
    private void simpan() {

        try {

            String nama = txtNamaProduk.getText();
            String jenis = cmdKategoriProduk.getValue();
            String hargaText = txtHargaProduk.getText();

            if (hargaText == null || hargaText.isBlank()) {
                throw new NumberFormatException();
            }

            int harga = Integer.parseInt(hargaText.trim());

            data.tambah(nama, jenis, harga);

            txtNamaProduk.clear();
            txtHargaProduk.clear();
            cmdKategoriProduk.setValue(null);

            new Alert(
                Alert.AlertType.INFORMATION,
                "Produk berhasil ditambahkan."
            ).showAndWait();

        } catch (NumberFormatException ex) {

            new Alert(
                Alert.AlertType.ERROR,
                "Harga harus berupa angka."
            ).showAndWait();

        } catch (IllegalArgumentException ex) {

            new Alert(
                Alert.AlertType.ERROR,
                ex.getMessage()
            ).showAndWait();
        }
    }

    // Navigasi halaman
    private void bukaHalaman(String lokasi) {

        try {

            Parent root = FXMLLoader.load(
                getClass().getResource(lokasi)
            );

            Stage stage = (Stage) btnLihatProduk
                    .getScene().getWindow();

            stage.setScene(new Scene(root));

        } catch (IOException | NullPointerException ex) {

            new Alert(
                Alert.AlertType.ERROR,
                "Gagal membuka halaman."
            ).showAndWait();

            ex.printStackTrace();
        }
    }
}
