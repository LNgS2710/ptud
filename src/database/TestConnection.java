package database;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args){
        Connection conn = JDBC.getConnection();
        System.out.println(conn);
        JDBC.closeConnection(conn);
    }
}
