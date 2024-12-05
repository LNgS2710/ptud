package dao;

import database.JDBC;
import entity.KhachHang;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class KhachHangDAO {

    public static void themkhachhang(KhachHang khachhang) {
        Connection c = null;
        CallableStatement cs = null;
        try {
            c = JDBC.getConnection();
            String sql = "{Call themkhachhang(?, ?, ?, ?, ?, ?)}";
            cs = c.prepareCall(sql);
            cs.setString(1, khachhang.getMakhachhang());
            cs.setString(2, khachhang.getTenkhachhang());
            cs.setBoolean(3, khachhang.isGioitinh());
            cs.setString(4, khachhang.getSodienthoai());
            cs.setDate(5, Date.valueOf(khachhang.getNgaysinh().toLocalDate()));
            cs.setString(6, khachhang.getCmnd());
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                JDBC.closeConnection(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void suakhachhang(KhachHang khachhang) {
        Connection c = null;
        CallableStatement cs = null;
        try {
            c = JDBC.getConnection();
            String sql = "{Call suakhachhang(?, ?, ?, ?, ?, ?)}";
            cs = c.prepareCall(sql);
            cs.setString(1, khachhang.getMakhachhang());
            cs.setString(2, khachhang.getTenkhachhang());
            cs.setBoolean(3, khachhang.isGioitinh());
            cs.setString(4, khachhang.getSodienthoai());
            cs.setDate(5, Date.valueOf(khachhang.getNgaysinh().toLocalDate()));
            cs.setString(6, khachhang.getCmnd());
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                JDBC.closeConnection(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static KhachHang getkhachhangbyma(String makhachhang) {
        Connection c = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        KhachHang khachhang = null;

        try {
            c = JDBC.getConnection();
            String sql = "{Call getkhachhangbyma(?)}";
            cs = c.prepareCall(sql);
            cs.setString(1, makhachhang);

            rs = cs.executeQuery();

            if (rs.next()) {
                khachhang = new KhachHang();
                khachhang.setMakhachhang(rs.getString("MaKH"));
                khachhang.setTenkhachhang(rs.getString("TenKH"));
                khachhang.setGioitinh(rs.getBoolean("GioiTinh"));
                khachhang.setSodienthoai(rs.getString("SDT"));
                khachhang.setNgaysinh(rs.getDate("NgaySinh"));
                khachhang.setCmnd(rs.getString("CMND"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                JDBC.closeConnection(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return khachhang;
    }

    public static ArrayList<KhachHang> getallkhachhang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        Connection c = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            c = JDBC.getConnection();
            String sql = "{Call GetAllKhachHang()}";
            cs = c.prepareCall(sql);
            rs = cs.executeQuery();

            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setMakhachhang(rs.getString("MaKH"));
                khachHang.setTenkhachhang(rs.getString("TenKH"));
                khachHang.setGioitinh(rs.getBoolean("gioiTinh"));
                khachHang.setSodienthoai(rs.getString("SDT"));
                khachHang.setNgaysinh(rs.getDate("ngaySinh"));
                khachHang.setCmnd(rs.getString("CMND"));
                list.add(khachHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                JDBC.closeConnection(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void xoakhachhang(String maKhachHang) {
        Connection c = null;
        CallableStatement cs = null;
        try {
            c = JDBC.getConnection();
            String sql = "{Call xoakhachhang(?)}";
            cs = c.prepareCall(sql);
            cs.setString(1, maKhachHang);
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                JDBC.closeConnection(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<KhachHang> timkhachhang(String maKhachHang, String tenKhachHang, String soDienThoai, String cmnd, Date ngaySinh, Boolean gioiTinh) {
        Connection c = null;
        CallableStatement cs = null;
        ArrayList<KhachHang> list = new ArrayList<>();

        try {
            c = JDBC.getConnection();
            String sql = "{CALL timkiemkhachhang(?, ?, ?, ?, ?, ?)}";
            cs = c.prepareCall(sql);

            cs.setString(1, maKhachHang);
            cs.setString(2, tenKhachHang);
            cs.setString(3, soDienThoai);
            cs.setString(4, cmnd);
            cs.setDate(5, ngaySinh != null ? new java.sql.Date(ngaySinh.getTime()) : null);
            cs.setBoolean(6, gioiTinh);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setMakhachhang(rs.getString("MaKH"));
                khachHang.setTenkhachhang(rs.getString("TenKH"));
                khachHang.setGioitinh(rs.getBoolean("gioiTinh"));
                khachHang.setSodienthoai(rs.getString("SDT"));
                khachHang.setNgaysinh(rs.getDate("ngaySinh"));
                khachHang.setCmnd(rs.getString("CMND"));

                list.add(khachHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                JDBC.closeConnection(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public KhachHang layKhachHangTheoSDT(String sdt) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Step 1: Get the database connection
            connection = JDBC.getConnection();

            // Step 2: Create the SQL query
            String sql = "SELECT * FROM KhachHang WHERE SDT LIKE ?";

            // Step 3: Prepare the statement
            preparedStatement = connection.prepareStatement(sql);

            // Step 4: Set the parameter for the query (using % for LIKE search)
            preparedStatement.setString(1, "%" + sdt + "%");

            // Step 5: Execute the query
            resultSet = preparedStatement.executeQuery();

            // Step 6: Process the result set
            if (resultSet.next()) {
                KhachHang kh = new KhachHang();
                kh.setMakhachhang(resultSet.getString("MaKH"));
                kh.setTenkhachhang(resultSet.getString("TenKH"));
                kh.setGioitinh(resultSet.getBoolean("gioiTinh"));
                kh.setSodienthoai(resultSet.getString("SDT"));
                kh.setNgaysinh(resultSet.getDate("ngaySinh"));
                kh.setCmnd(resultSet.getString("CMND"));

                
                // Add other necessary fields

                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 7: Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                JDBC.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
