import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaFrame extends JFrame {
    private JTextField namaField, nimField;
    private JButton addButton, updateButton, deleteButton, viewButton;
    private JTextArea resultArea;

    public MahasiswaFrame() {
        setTitle("CRUD Data Mahasiswa");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nama:"));
        namaField = new JTextField();
        panel.add(namaField);

        panel.add(new JLabel("NIM:"));
        nimField = new JTextField();
        panel.add(nimField);

        addButton = new JButton("Tambah");
        updateButton = new JButton("Ubah");
        deleteButton = new JButton("Hapus");
        viewButton = new JButton("Lihat");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Tambahkan event handler untuk tombol
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahMahasiswa();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahMahasiswa();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusMahasiswa();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lihatMahasiswa();
            }
        });
    }

    private void tambahMahasiswa() {
        String nama = namaField.getText();
        String nim = nimField.getText();

        String query = "INSERT INTO data_mahasiswa (nama, nim) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, nim);
            pstmt.executeUpdate();
            resultArea.setText("Mahasiswa berhasil ditambahkan.");
        } catch (SQLException e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    private void ubahMahasiswa() {
        String nim = nimField.getText();
        String nama = namaField.getText();

        String query = "UPDATE data_mahasiswa SET nama = ? WHERE nim = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, nim);
            int rowsAffected = pstmt.executeUpdate();
            resultArea.setText(rowsAffected + " baris berhasil diperbarui.");
        } catch (SQLException e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    private void hapusMahasiswa() {
        String nim = nimField.getText();

        String query = "DELETE FROM data_mahasiswa WHERE nim = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nim);
            int rowsAffected = pstmt.executeUpdate();
            resultArea.setText(rowsAffected + " baris berhasil dihapus.");
        } catch (SQLException e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    private void lihatMahasiswa() {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();

        String query = "SELECT * FROM data_mahasiswa";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int idmhs = rs.getInt("idmhs");
                String nama = rs.getString("nama");
                String nim = rs.getString("nim");
                mahasiswaList.add(new Mahasiswa(idmhs, nama, nim));
            }

            StringBuilder result = new StringBuilder();
            for (Mahasiswa mhs : mahasiswaList) {
                result.append("ID: ").append(mhs.getIdmhs())
                      .append(", Nama: ").append(mhs.getNama())
                      .append(", NIM: ").append(mhs.getNim()).append("\n");
            }
            resultArea.setText(result.toString());

        } catch (SQLException e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MahasiswaFrame().setVisible(true));
    }
}
