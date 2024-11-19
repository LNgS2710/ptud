package dao;

import database.JDBC;
import entity.Ban;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class BanDAO {

    public static boolean insertBan(Ban ban) {
        String sql = "{CALL insertBan(?, ?, ?, ?)}";
        try (Connection conn = JDBC.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, ban.getMa());
            stmt.setString(2, ban.getViTri());
            stmt.setInt(3, ban.getSoCho());
            stmt.setBoolean(4, ban.isTrangThai());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Ban> searchBan(String ma, String viTri, Integer soCho, Boolean trangThai) {
        ArrayList<Ban> results = new ArrayList<>();
        String sql = "{CALL searchBan(?, ?, ?, ?)}";
        try (Connection conn = JDBC.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, ma);
            stmt.setString(2, viTri);
            stmt.setObject(3, soCho);
            stmt.setBoolean(4, trangThai);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static boolean deleteBan(String ma) {
        String sql = "{CALL deleteBan(?)}";
        try (Connection conn = JDBC.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, ma);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateBan(Ban ban) {
        String sql = "{CALL updateBan(?, ?, ?, ?)}";
        try (Connection conn = JDBC.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, ban.getMa());
            stmt.setString(2, ban.getViTri());
            stmt.setInt(3, ban.getSoCho());
            stmt.setBoolean(4, ban.isTrangThai());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Ban getBan(String ma) {
        String sql = "{CALL getBan(?)}";
        try (Connection conn = JDBC.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Ban ban = new Ban();
                ban.setMa(rs.getString("ma"));
                ban.setViTri(rs.getString("viTri"));
                ban.setSoCho(rs.getInt("soCho"));
                ban.setTrangThai(rs.getBoolean("trangThai"));
                return ban;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Ban> getAllBans() {
        ArrayList<Ban> dsBan = new ArrayList<Ban>();
        String query = "SELECT * FROM ban";

        try (Connection connection = JDBC.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ban ban = new Ban();
                ban.setMa(rs.getString("ma"));
                ban.setViTri(rs.getString("viTri"));
                ban.setSoCho(rs.getInt("soCho"));
                ban.setTrangThai(rs.getBoolean("trangThai"));

                dsBan.add(ban);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsBan;
    }
}
