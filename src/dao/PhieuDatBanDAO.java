package dao;

import database.JDBC;
import entity.Ban;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuDatBan;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhieuDatBanDAO {

    private String maBan;


    public String phatSinhMaTuDong() {
            String maPDB = null;
            String sql = "SELECT MAX(maPDB) FROM phieudatban";
            Connection connection = JDBC.getConnection();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    maPDB = resultSet.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBC.closeConnection(connection);
            }

            if (maPDB == null || maPDB.equalsIgnoreCase("")) {
                maPDB = "PDBAA001";
            }
            return "P" + new MaTuDong().fomatAA000(maPDB.substring(1));
        }

        // Method to add a new PhieuDatPhong to the database
        public boolean themPhieuDatBan(PhieuDatBan phieuDatBan) {
            String maPDB = phatSinhMaTuDong();
            phieuDatBan.setMaPDB(maPDB);
            phieuDatBan.setTrangThai(true);

            String sql = "INSERT INTO phieudatban (maPDB, maBan, maKH, maNV, thoiGianDat, thoiGianNhanBan, trangThai) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            Connection connection = JDBC.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, phieuDatBan.getMaPDB());
                preparedStatement.setString(2, phieuDatBan.getBan().getMaBan());
                preparedStatement.setString(3, phieuDatBan.getKhachHang().getMakhachhang());
                preparedStatement.setString(4, phieuDatBan.getNhanVienLap().getMaNV());
                preparedStatement.setTimestamp(5, new Timestamp(phieuDatBan.getThoiGianDat().getTime()));
            if (phieuDatBan.getThoiGianNhanBan() != null) {
                preparedStatement.setTimestamp(6, new Timestamp(phieuDatBan.getThoiGianNhanBan().getTime()));
            } else {
                preparedStatement.setNull(6, Types.TIMESTAMP); // Nếu là null, set giá trị null vào SQL
            }
                preparedStatement.setBoolean(7, phieuDatBan.isTrangThai());
                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBC.closeConnection(connection);
            }
            return false;
        }

//        // Method to cancel a PhieuDatPhong by updating its status
        public boolean huyPhieuDatBan(String maPDB) {
            String sql = "UPDATE phieudatban SET trangThai = ? WHERE maPDB = ?";
            Connection connection = JDBC.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, false);
                preparedStatement.setString(2, maPDB);

                int rowsUpdated = preparedStatement.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBC.closeConnection(connection);
            }
            return false;
        }

//        // Method to update the status of reservations based on specific conditions
        public int updateTrangThaiPDB() {
            String sql = "UPDATE phieudatban SET trangThai = 0 WHERE thoiGianNhanBan >= DATE_SUB(NOW(), INTERVAL 2 HOUR)";
            Connection connection = JDBC.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int rowsUpdated = preparedStatement.executeUpdate();
                return rowsUpdated;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBC.closeConnection(connection);
            }
            return 0;
        }

//        // Method to retrieve the latest PhieuDatPhong for a specific room
//        public PhieuDatPhong layPhieuDatPhongMoiNhatTheoPhong(String maPhong) {
//            String sql = "SELECT * FROM phieudatphong WHERE maPhong = ? AND tinhTrang = 1 ORDER BY maPDP DESC LIMIT 1";
//            Connection connection = JDBC.getConnection();
//
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setString(1, maPhong);
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                if (resultSet.next()) {
//                    return mapResultSetToPhieuDatPhong(resultSet);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } finally {
//                JDBC.closeConnection(connection);
//            }
//            return null;
//        }
        private String getViTriBanByMaBan(String maBan) {
            String viTri = null;
            String sql = "SELECT viTri FROM ban WHERE maBan = ?";  

            try (Connection connection = JDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, maBan);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        viTri = resultSet.getString("viTri");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return viTri;
        }
        
        private int getSoThuTuBanByMaBan(String maBan) {
            int soThuTu = -1;
            String sql = "SELECT soThuTu FROM ban WHERE maBan = ?";  

            try (Connection connection = JDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, maBan);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        soThuTu = resultSet.getInt("soThuTu");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return soThuTu;
        }

        private String getTenNhanVienByMaNV(String maNV) {
            String tenNV = null;
            String sql = "SELECT tenNV FROM nhanvien WHERE maNV = ?";  // Giả sử bảng nhân viên có cột 'tenNV'

            try (Connection connection = JDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, maNV);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        tenNV = resultSet.getString("tenNV");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return tenNV;
        }
        
        
        // Helper method to map ResultSet to PhieuDatPhong object
        private PhieuDatBan mapResultSetToPhieuDatBan(ResultSet resultSet) throws SQLException  {
            PhieuDatBan phieuDatBan = new PhieuDatBan();
            phieuDatBan.setMaPDB(resultSet.getString("maPDB"));
            String maBan = resultSet.getString("maBan");
            Ban ban = new Ban();
            ban.setMaBan(maBan);
            int soThuTu = getSoThuTuBanByMaBan(maBan); // Gọi phương thức phụ để lấy tên nhân viên
            ban.setSoThuTu(soThuTu);
            String viTri = getViTriBanByMaBan(maBan);
            ban.setViTri(viTri);
            phieuDatBan.setBan(ban);
            
            

            // Tạo và ánh xạ đối tượng KhachHang từ dữ liệu trong ResultSet
            KhachHang khachHang = new KhachHang();
            khachHang.setTenkhachhang(resultSet.getString("TenKH"));
            khachHang.setSodienthoai(resultSet.getString("SDT"));
            phieuDatBan.setKhachHang(khachHang);

            String maNV = resultSet.getString("maNV");

           // Tạo và ánh xạ đối tượng NhanVien
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(maNV);

            // Lấy tên nhân viên từ mã nhân viên
            String tenNV = getTenNhanVienByMaNV(maNV); // Gọi phương thức phụ để lấy tên nhân viên
            nhanVien.setTenNV(tenNV);
            // Tạo và ánh xạ đối tượng NhanVien từ dữ liệu trong ResultSet
            phieuDatBan.setNhanVienLap(nhanVien);
            
            phieuDatBan.setThoiGianDat(resultSet.getTimestamp("thoiGianDat"));
            phieuDatBan.setThoiGianNhanBan(resultSet.getTimestamp("thoiGianNhanBan"));
            phieuDatBan.setTrangThai(resultSet.getBoolean("trangThai"));
            return phieuDatBan;
        }
        
//        public List<PhieuDatBan> layDanhSachPhieuDatBan(String maPhieuDat, String sdtKhach, int trangThai, int soThuTu) {
//            List<PhieuDatBan> danhSach = new ArrayList<>();
//            String sql = "SELECT * FROM phieudatban p " +
//                         "JOIN khachhang k ON k.maKH = p.maKH" +
//                    "JOIN ban b ON b.maBan = p.maBan WHERE ";
//
//            if (!maPhieuDat.equals("")) {
//                sql += " p.maPDB = ? AND ";
//            } else if (!sdtKhach.equals("")) {
//                sql += " k.SDT = ? AND ";
//            }
//
//            if (trangThai == 1) {
//                sql += " p.trangThai = 1 ";
//            } else if (trangThai == 2) {
//                sql += " p.trangThai = 0 ";
//            } else {
//                sql += " p.trangThai IS NOT NULL ";
//            }
//
//            sql += " ORDER BY p.maPDB DESC LIMIT 50";
//            Connection connection = JDBC.getConnection();
//
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                int paramIndex = 1;
//                if (!maPhieuDat.equals("")) {
//                    preparedStatement.setString(paramIndex++, maPhieuDat);
//                } else if (!sdtKhach.equals("")) {
//                    preparedStatement.setString(paramIndex++, sdtKhach);
//                }
//                preparedStatement.setInt(paramIndex, (soThuTu - 1));
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    danhSach.add(mapResultSetToPhieuDatBan(resultSet));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } finally {
//                JDBC.closeConnection(connection);
//            }
//            return danhSach;
//        }

//        // Method to retrieve a list of PhieuDatPhong based on conditions
        public List<PhieuDatBan> layDanhSachPhieuDatBan(String maPhieuDat, String sdtKhach, int trangThai) {
            List<PhieuDatBan> danhSach = new ArrayList<>();
            String sql = "SELECT * FROM phieudatban p " +
                         "JOIN khachhang k ON k.maKH = p.maKH WHERE ";

            if (!maPhieuDat.equals("")) {
                sql += " p.maPDB = ? AND ";
            } else if (!sdtKhach.equals("")) {
                sql += " k.SDT = ? AND ";
            }

            if (trangThai == 1) {
                sql += " p.trangThai = 1 ";
            } else if (trangThai == 2) {
                sql += " p.trangThai = 0 ";
            } else {
                sql += " p.trangThai IS NOT NULL ";
            }

            sql += " ORDER BY p.maPDB DESC LIMIT 50";
            Connection connection = JDBC.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int paramIndex = 1;
                if (!maPhieuDat.equals("")) {
                    preparedStatement.setString(paramIndex++, maPhieuDat);
                } else if (!sdtKhach.equals("")) {
                    preparedStatement.setString(paramIndex++, sdtKhach);
                }

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    danhSach.add(mapResultSetToPhieuDatBan(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBC.closeConnection(connection);
            }
            return danhSach;
        }

        public List<PhieuDatBan> layPhieuDatBanTheoBan(String maBan) {
            List<PhieuDatBan> danhSachPhieu = new ArrayList<>();
            try (Connection conn = JDBC.getConnection()) {
                String sql = "SELECT * FROM PhieuDatBan WHERE maBan = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, maBan); // Sử dụng khóa ngoại để lọc các phiếu đặt của bàn
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    PhieuDatBan phieu = new PhieuDatBan();
                    phieu.setMaPDB(rs.getString("maPDB")); // Ví dụ thêm nếu có
                    phieu.setBan(new BanDAO().layThongTinBanQuaMa(rs.getString("maBan"))); 
                    phieu.setThoiGianNhanBan(rs.getTimestamp("thoiGianNhanBan"));
                    danhSachPhieu.add(phieu);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return danhSachPhieu;
        }
    
}
