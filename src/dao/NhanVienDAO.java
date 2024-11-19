package dao;

import database.JDBC;
import entity.NhanVien;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    // Method to insert a new employee
    public void insertNhanVien(NhanVien nhanVien) {
        try (Connection conn = JDBC.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL insertNhanVien(?, ?, ?, ?, ?)}")) {

            stmt.setString(1, nhanVien.getMa());
            stmt.setString(2, nhanVien.getTen());
            stmt.setString(3, nhanVien.getEmail());
            stmt.setString(4, nhanVien.getSoDienThoai());
            stmt.setBoolean(5, nhanVien.isTrangThai());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging this exception instead
        }
    }

    // Method to update an existing employee
    public void updateNhanVien(NhanVien nhanVien) {
        try (Connection conn = JDBC.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL updateNhanVien(?, ?, ?, ?, ?)}")) {

            stmt.setString(1, nhanVien.getMa());
            stmt.setString(2, nhanVien.getTen());
            stmt.setString(3, nhanVien.getEmail());
            stmt.setString(4, nhanVien.getSoDienThoai());
            stmt.setBoolean(5, nhanVien.isTrangThai());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging this exception instead
        }
    }

    // Method to delete an employee
    public void deleteNhanVien(String ma) {
        try (Connection conn = JDBC.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL deleteNhanVien(?)}")) {

            stmt.setString(1, ma);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging this exception instead
        }
    }

    // Method to get an employee by ID
    public NhanVien getNhanVien(String ma) {
        NhanVien nhanVien = null;
        try (Connection conn = JDBC.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL getNhanVien(?)}")) {

            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nhanVien = new NhanVien();
                nhanVien.setMa(rs.getString("ma"));
                nhanVien.setTen(rs.getString("ten"));
                nhanVien.setEmail(rs.getString("email"));
                nhanVien.setSoDienThoai(rs.getString("soDienThoai"));
                nhanVien.setTrangThai(rs.getBoolean("trangThai"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging this exception instead
        }
        return nhanVien;
    }

    // Method to get all employees
    public List<NhanVien> getDSNhanVien() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        try (Connection conn = JDBC.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL getAllNhanVien()}")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMa(rs.getString("ma"));
                nhanVien.setTen(rs.getString("ten"));
                nhanVien.setEmail(rs.getString("email"));
                nhanVien.setSoDienThoai(rs.getString("soDienThoai"));
                nhanVien.setTrangThai(rs.getBoolean("trangThai"));

                nhanVienList.add(nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging this exception instead
        }
        return nhanVienList;
    }
    
    // Method to search for employees
    public List<NhanVien> searchNhanVien(String ma, String ten) {
        List<NhanVien> resultList = new ArrayList<>();
        String sql = "{CALL searchNhanVien(?, ?)}"; // Call the stored procedure

        try (Connection conn = JDBC.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, ma.isEmpty() ? null : ma); // Use null for empty
            stmt.setString(2, ten.isEmpty() ? null : ten); // Use null for empty
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setMa(rs.getString("ma_nhan_vien"));
                    nhanVien.setTen(rs.getString("ten_nhan_vien"));
                    nhanVien.setEmail(rs.getString("email"));
                    nhanVien.setSoDienThoai(rs.getString("so_dien_thoai"));
                    nhanVien.setTrangThai(rs.getBoolean("trang_thai"));
                    resultList.add(nhanVien);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging this exception instead
        }
        return resultList;
    }
}
