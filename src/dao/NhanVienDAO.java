package dao;

import entity.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    private Connection conn;

    public boolean addNhanVien(NhanVien nv) {
        String sql = "CALL themNV(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getMaNV());
            stmt.setString(2, nv.getTenNV());
            stmt.setBoolean(3, nv.isGioiTinh());
            stmt.setDate(4, nv.getNgaySinh());
            stmt.setString(5, nv.getSdt());
            stmt.setString(6, nv.getCmnd());
            stmt.setString(7, nv.getChucVu());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNhanVien(NhanVien nv) {
        String sql = "CALL suaNV(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getMaNV());
            stmt.setString(2, nv.getTenNV());
            stmt.setBoolean(3, nv.isGioiTinh());
            stmt.setDate(4, nv.getNgaySinh());
            stmt.setString(5, nv.getSdt());
            stmt.setString(6, nv.getCmnd());
            stmt.setString(7, nv.getChucVu());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhanVien(String maNV) {
        String sql = "CALL xoaNV(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public NhanVien getNhanVienById(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToNhanVien(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> nvList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                nvList.add(mapResultSetToNhanVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nvList;
    }

    private NhanVien mapResultSetToNhanVien(ResultSet rs) throws SQLException {
        String maNV = rs.getString("maNV");
        String tenNV = rs.getString("tenNV");
        boolean gioiTinh = rs.getBoolean("gioiTinh");
        Date ngaySinh = rs.getDate("ngaySinh");
        String sdt = rs.getString("sdt");
        String cmnd = rs.getString("cmnd");
        String chucVu = rs.getString("chucVu");
        return new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, sdt, cmnd, chucVu);
    }
}
