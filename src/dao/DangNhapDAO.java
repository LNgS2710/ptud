/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import database.JDBC;
import entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Thành Trung
 */
public class DangNhapDAO {
     public NhanVien getNhanVienDangNhap(String soDienThoai) {
        String query = "SELECT * FROM nhanvien WHERE sdt = ?";
        NhanVien nhanVien = null;

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, soDienThoai);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                nhanVien = new NhanVien();
                // Gán các thuộc tính từ ResultSet vào đối tượng nhanVien
                nhanVien.setMaNV(rs.getString("maNV")); // Ví dụ: nếu có cột id
                nhanVien.setTenNV(rs.getString("tenNV"));
                nhanVien.setGioiTinh(rs.getBoolean("gioiTinh"));
                nhanVien.setMatKhau(rs.getString("matKhau"));
                nhanVien.setNgaySinh(rs.getDate("ngaySinh"));
                nhanVien.setSdt(rs.getString("sdt"));
                nhanVien.setCmnd(rs.getString("cmnd"));
                nhanVien.setChucVu(rs.getString("chucVu"));
                // Thêm các thuộc tính khác tương tự
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVien;
    }


    public boolean kiemTraSDT(String sdt) {
        String query = "SELECT * FROM nhanvien WHERE sdt = ?";
        boolean exists = false;

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, sdt);
            ResultSet rs = preparedStatement.executeQuery();
            exists = rs.next(); // Trả về true nếu có kết quả
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}

