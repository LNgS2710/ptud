package dao;

import database.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhieuDatBanDAO {

    // Method to insert a new reservation
    public static boolean datBan(String maKhachHang, String maBan, String thoiGian, String ghiChu) throws SQLException {
        String sql = "INSERT INTO phieudatban (ma_khach_hang, ma_ban, thoi_gian, ghi_chu) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, maKhachHang);
            stmt.setString(2, maBan);
            stmt.setString(3, thoiGian);
            stmt.setString(4, ghiChu);

            return stmt.executeUpdate() > 0; // Return true if insertion is successful
        }
    }

    // Method to check if a table is available at a specific time (optional)
    public static boolean isTableAvailable(String maBan, String thoiGian) throws SQLException {
        String sql = "SELECT COUNT(*) FROM phieudatban WHERE ma_ban = ? AND thoi_gian = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, maBan);
            stmt.setString(2, thoiGian);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0; // Return true if no reservations found
            }
            return false;
        }
    }

    // Other DAO methods can be added here, such as updating or deleting reservations
}
