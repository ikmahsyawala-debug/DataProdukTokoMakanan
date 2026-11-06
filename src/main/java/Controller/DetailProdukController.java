
package Controller;

import Data.Tambah;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetailProdukController implements Initializable {

    @FXML private Button btnLihatProduk;
    @FXML private Button btnTambahProduk;

    @FXML private Label lblKode;
    @FXML private Label lblNama;
    @FXML private Label lblKategori;
    @FXML private Label lblHarga;

    // Menerima data produk
    public void setData(Tambah p) {

        if (p == null) {
            return;
        }

        lblKode.setText(p.getKode());
        lblNama.setText(p.getNama());
        lblKategori.setText(p.getJenis());

        lblHarga.setText(
            "Rp " + String.format("%,d", p.getHarga())
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnLihatProduk.setOnAction(e -> {
            bukaHalaman("/FXML/DataProduk.fxml");
        });

        btnTambahProduk.setOnAction(e -> {
            bukaHalaman("/FXML/Daftar.fxml");
        });
    }

    // Navigasi halaman
    private void bukaHalaman(String lokasi) {

        try {

            URL urlFXML = getClass().getResource(lokasi);

            if (urlFXML == null) {
                throw new IOException(
                    "File tidak ditemukan: " + lokasi
                );
            }

            FXMLLoader loader = new FXMLLoader(urlFXML);

            Parent root = loader.load();

            Stage stage = (Stage) btnLihatProduk
                    .getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {

            ex.printStackTrace();

            Alert alert = new Alert(
                Alert.AlertType.ERROR
            );

            alert.setTitle("Error");
            alert.setHeaderText("Gagal membuka halaman");

            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }
}
