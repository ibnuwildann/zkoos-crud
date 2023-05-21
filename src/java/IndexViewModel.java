import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;

public class IndexViewModel {
  private List<Mahasiswa> mahasiswaList;
  private Mahasiswa mahasiswaBaru;
  private Connection connection;

  public IndexViewModel() {
    mahasiswaList = new ArrayList<>();
    mahasiswaBaru = new Mahasiswa();
    connection = null;
    try {
      // Membuat koneksi ke database Derby
      connection = DriverManager.getConnection("jdbc:derby://localhost:1527/app");
    } catch (SQLException e) {
        // Handle error

    }
  }

  @Command
  @NotifyChange("mahasiswaList")
  public void tambah() {
    try {
      // Insert data mahasiswa ke database
      PreparedStatement statement = connection.prepareStatement("INSERT INTO mahasiswa (nama, email, address, phone) VALUES (?, ?, ?, ?)");
      statement.setString(1, mahasiswaBaru.getNama());
      statement.setString(2, mahasiswaBaru.getEmail());
      statement.setString(3, mahasiswaBaru.getAddress());
      statement.setString(4, mahasiswaBaru.getPhone());
      statement.executeUpdate();

      // Reset input fields
      mahasiswaBaru = new Mahasiswa();

      // Refresh daftar mahasiswa
      loadData();
    } catch (SQLException e) {
      // Handle error
      e.printStackTrace();
    }
  }

  @Command
  @NotifyChange("mahasiswaList")
  public void edit(@BindingParam("mahasiswa") Mahasiswa mahasiswa) {
    // Implementasi logika edit sesuai kebutuhan
  }

  @Command
  @NotifyChange("mahasiswaList")
  public void hapus(@BindingParam("mahasiswa") Mahasiswa mahasiswa) {
    try {
      // Hapus data mahasiswa dari database
      PreparedStatement statement = connection.prepareStatement("DELETE FROM mahasiswa WHERE id = ?");
      statement.setInt(1, mahasiswa.getId());
      statement.executeUpdate();

      // Refresh daftar mahasiswa
      loadData();
    } catch (SQLException e) {
      // Handle error
      e.printStackTrace();
    }
  }

  public List<Mahasiswa> getMahasiswaList() {
    return mahasiswaList;
  }

  void loadData() {
    try {
      // Ambil data mahasiswa dari database
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM mahasiswa");

      // Kosongkan daftar mahasiswa sebelumnya
      mahasiswaList.clear();

      // Tambahkan data mahasiswa dari hasil query
      while (resultSet.next()) {
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setId(resultSet.getInt("id"));
        mahasiswa.setNama(resultSet.getString("nama"));
        mahasiswa.setEmail(resultSet.getString("email"));
        mahasiswa.setAddress(resultSet.getString("address"));
        mahasiswa.setPhone(resultSet.getString("phone"));
        mahasiswaList.add(mahasiswa);
      }
    } catch (SQLException e) {
      // Handle error
      e.printStackTrace();
    }
  }

   
}
