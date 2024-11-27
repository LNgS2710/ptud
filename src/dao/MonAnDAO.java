package dao;

import database.JDBC;
import entity.MonAn;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MonAnDAO {

    public static void insertMonAn(String ma, String ten, String loai, int soluong, double gia, boolean trangThai, String imgPath) {
        Connection conn = JDBC.getConnection();
        CallableStatement stmt = null;
        try {
            String sql = "{CALL insertMonAn(?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(sql);
            stmt.setString(1, ma);
            stmt.setString(2, ten);
            stmt.setString(3, loai);
            stmt.setInt(4, soluong);
            stmt.setDouble(5, gia);
            stmt.setBoolean(6, trangThai);
            stmt.setString(7, imgPath);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(stmt, conn);
        }
    }

    public static void updateMonAn(MonAn monAn) {
        Connection conn = JDBC.getConnection();
        CallableStatement stmt = null;
        try {
            String sql = "{CALL updateMonAn(?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(sql);
            stmt.setString(1, monAn.getMa());
            stmt.setString(2, monAn.getTen());
            stmt.setString(3, monAn.getLoai());
            stmt.setInt(4, monAn.getSoLuong());
            stmt.setDouble(5, monAn.getGia());
            stmt.setBoolean(6, monAn.isTrangThai());
            stmt.setString(7, monAn.getImgPath());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt, conn);
        }
    }

    public static void deleteMonAn(String ma) {
        Connection conn = JDBC.getConnection();
        CallableStatement stmt = null;
        try {
            String sql = "{CALL deleteMonAn(?)}";
            stmt = conn.prepareCall(sql);
            stmt.setString(1, ma);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt, conn);
        }
    }

    private static void close(CallableStatement stmt, Connection conn) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            JDBC.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<MonAn> getDSMonAn() {
        ArrayList<MonAn> list = new ArrayList<MonAn>();
        Connection conn = JDBC.getConnection();
        String sql = "select * from monan";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String ma = rs.getString("ma");
                String ten = rs.getString("ten");
                String loai = rs.getString("loai");
                boolean trangThai = rs.getBoolean("trang_thai");
                MonAn monan = new MonAn(ten, ma, loai, trangThai);
                list.add(monan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC.closeConnection(conn);
        }
        return list;
    }

    public static MonAn getMonAn(String ma) {
        MonAn monAn = null;
        Connection conn = JDBC.getConnection();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "{CALL getMonAn(?)}"; // Call the stored procedure
            stmt = conn.prepareCall(sql);
            stmt.setString(1, ma); // Set the ma parameter

            rs = stmt.executeQuery();

            // Check if result set contains a row
            if (rs.next()) {
                String ten = rs.getString("ten");
                String loai = rs.getString("loai");
                int soluong = rs.getInt("soluong");
                double gia = rs.getDouble("gia");
                boolean trangThai = rs.getBoolean("trang_thai");
                String imgPath = rs.getString("imgPath");

                // Create a new MonAn object with retrieved values
                monAn = new MonAn(ten, ma, gia, loai, soluong, trangThai, imgPath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                JDBC.closeConnection(conn); // Close the connection
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return monAn;
    }

    public static boolean isMaExists(String ma) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM monan WHERE ma = ?"; // Adjust the table name if necessary

        try (Connection conn = JDBC.getConnection(); // Make sure to replace with your connection method
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, ma);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0; // If the count is greater than 0, it means it exists
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions properly
        }

        return exists;
    }

    public static ArrayList<MonAn> searchMonAn(String ma, String ten, Double gia, Boolean trangThai, String loai) {
        ArrayList<MonAn> results = new ArrayList<MonAn>();

        // Define your SQL query
        String sql = "{CALL searchMonAn(?, ?, ?, ?, ?)}";

        try (Connection conn = JDBC.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, ma);
            stmt.setString(2, ten);
            stmt.setObject(3, gia); // Use setObject to handle null values
            stmt.setBoolean(4, trangThai);
            stmt.setString(5, loai);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MonAn monAn = new MonAn();
                monAn.setMa(rs.getString("ma"));
                monAn.setTen(rs.getString("ten"));
                monAn.setLoai(rs.getString("loai"));
                monAn.setSoLuong(rs.getInt("soluong"));
                monAn.setGia(rs.getDouble("gia"));
                monAn.setTrangThai(rs.getBoolean("trang_thai"));
                results.add(monAn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

}
