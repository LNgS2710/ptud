/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import database.JDBC;
import entity.KhuyenMai;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thành Trung
 */
public class KhuyenMaiDao {

    
    public boolean themKhuyenMai(KhuyenMai khuyenMai) {

        String sql = "INSERT INTO KhuyenMai (maKM, moTa, chietKhau, trangThai) VALUES (?, ?, ?, ?)";
        try (Connection con = JDBC.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, khuyenMai.getMaKM());
            stmt.setString(2, khuyenMai.getMoTa());
            stmt.setFloat(3, khuyenMai.getChietKhau());
            stmt.setBoolean(4, khuyenMai.isTrangThai());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<KhuyenMai> layDanhSachKhuyenMai() {
        List<KhuyenMai> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai";

        try (Connection con = JDBC.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                KhuyenMai khuyenMai = new KhuyenMai();
                khuyenMai.setMaKM(rs.getString("maKM"));
                khuyenMai.setMoTa(rs.getString("moTa"));
                khuyenMai.setChietKhau(rs.getFloat("chietKhau"));
                khuyenMai.setTrangThai(rs.getBoolean("trangThai"));
                danhSach.add(khuyenMai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
    
    public List<KhuyenMai> layDanhSachKhuyenMaiTheoTrangThai(int trangThai) {
        List<KhuyenMai> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai WHERE trangThai = ?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, trangThai);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    KhuyenMai khuyenMai = new KhuyenMai();
                    khuyenMai.setMaKM(rs.getString("maKM"));
                    khuyenMai.setMoTa(rs.getString("moTa"));
                    khuyenMai.setChietKhau(rs.getFloat("chietKhau"));
                    khuyenMai.setTrangThai(rs.getBoolean("trangThai"));
                    danhSach.add(khuyenMai);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
    
    public KhuyenMai layKhuyenMaiTheoMa(String maKM) {
        String sql = "SELECT * FROM KhuyenMai WHERE maKM = ?";
        try (Connection con = JDBC.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maKM);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    KhuyenMai khuyenMai = new KhuyenMai();
                    khuyenMai.setMaKM(rs.getString("maKM"));
                    khuyenMai.setMoTa(rs.getString("moTa"));
                    khuyenMai.setChietKhau(rs.getFloat("chietKhau"));
                    khuyenMai.setTrangThai(rs.getBoolean("trangThai"));
                    return khuyenMai;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean capNhatKhuyenMai(KhuyenMai khuyenMai) {
        String sql = "UPDATE KhuyenMai SET moTa = ?, chietKhau = ?, trangThai = ? WHERE maKM = ?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, khuyenMai.getMoTa());
            stmt.setFloat(2, khuyenMai.getChietKhau());
            stmt.setBoolean(3, khuyenMai.isTrangThai());
            stmt.setString(4, khuyenMai.getMaKM());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<KhuyenMai> layDanhSachKhuyenMaiTheoMaVaTrangThai(String maKM, String trangThai) {
        List<KhuyenMai> result = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai WHERE maKM LIKE ? AND trangThai = ?"; // Cập nhật câu SQL theo yêu cầu của bạn

        try (Connection con = JDBC.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, "%" + maKM + "%"); // Lọc theo mã khuyến mãi
            stmt.setString(2, trangThai); // Lọc theo trạng thái (Hoạt động/Hết hạn)

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getString("maKM"));
                km.setMoTa(rs.getString("moTa"));
                km.setChietKhau(rs.getFloat("chietKhau"));
                km.setTrangThai(rs.getBoolean("trangThai"));
                result.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public boolean xoaKhuyenMai(String maKM) {
        String sql = "DELETE FROM KhuyenMai WHERE maKM = ?"; // Câu lệnh SQL xóa khuyến mãi

        try (Connection con = JDBC.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maKM); // Gán giá trị cho tham số ? (maKM)

            int rowsAffected = stmt.executeUpdate(); // Thực thi câu lệnh xóa
            return rowsAffected > 0; // Nếu có ít nhất một dòng bị xóa, trả về true
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }
}
