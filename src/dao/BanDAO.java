package dao;

import database.JDBC;
import entity.Ban;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class BanDAO {

    private Connection conn;

    public boolean themBan(Ban b) {
        String sql = "{CALL themBan(?, ?, ?)}";
        try {
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, b.getMaBan());
            stmt.setString(2, b.getViTri());
            stmt.setInt(3, b.getSoCho());
            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaTrangThaiBan(String maBan, boolean trangThai) {
        String sql = "CALL suaTrangThaiBan(?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maBan);
            stmt.setBoolean(2, trangThai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
