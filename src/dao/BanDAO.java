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

    public BanDAO() {
        conn = JDBC.getConnection();
        if (conn == null) {
            System.out.println("Database connection failed.");
        } else {
            System.out.println("Database connected.");
        }
    }

    public boolean themBan(Ban b) {
        String sql = "{CALL themBan(?, ?, ?)}";
        try {
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, b.getMaBan());
            cs.setString(2, b.getViTri());
            cs.setInt(3, b.getSoCho());
            int n = cs.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaTrangThaiBan(String maBan, String trangThai) {
        String sql = "CALL suaTrangThaiBan(?, ?)";
        try {
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, maBan);
            cs.setString(2, trangThai);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ban> dsBan() {
        List<Ban> danhSachBan = new ArrayList<>();
        String sql = "{CALL dsBan()}";

        try {
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Ban ban = new Ban();
                ban.setMaBan(rs.getString("maBan"));
                ban.setSoThuTu(rs.getInt("soThuTu"));
                ban.setViTri(rs.getString("viTri"));
                ban.setSoCho(rs.getInt("soCho"));
                ban.setTrangThai(rs.getString("trangThai"));
                danhSachBan.add(ban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachBan;
    }
    
    public Ban getBanById(String maBan) {
        Ban ban = null;
        String sql = "{CALL getBanByID(?)}";
        try {
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, maBan);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                ban = new Ban();
                ban.setMaBan(rs.getString("maBan"));
                ban.setSoThuTu(rs.getInt("soThuTu"));
                ban.setViTri(rs.getString("viTri"));
                ban.setSoCho(rs.getInt("soCho"));
                ban.setTrangThai(rs.getString("trangThai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ban;
    }
    
    public Ban layThongTinBanQuaMa(String maBan) {
    // JDBC connection variables
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        // Step 1: Get a connection to the database using your JDBC class
        connection = JDBC.getConnection();

        // Step 2: Create a SQL query
        String sql = "SELECT * FROM Ban WHERE maBan = ?";
        preparedStatement = connection.prepareStatement(sql);

        // Step 3: Set the parameter value
        preparedStatement.setString(1, maBan);

        // Step 4: Execute the query
        resultSet = preparedStatement.executeQuery();

        // Step 5: Process the result set
        if (resultSet.next()) {
            // Assuming your Phong class has a suitable constructor or setters
            Ban ban = new Ban();
            ban.setMaBan(resultSet.getString("maBan"));
            ban.setSoThuTu(resultSet.getInt("soThuTu"));
            ban.setViTri(resultSet.getString("viTri"));
            ban.setSoCho(resultSet.getInt("soCho"));
            ban.setViTri(resultSet.getString("viTri"));
            ban.setTrangThai(resultSet.getString("trangThai"));
            // Add more fields if necessary

            return ban;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Step 6: Close resources
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            JDBC.closeConnection(connection); // Use your JDBC utility method to close the connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return null;
}
}
