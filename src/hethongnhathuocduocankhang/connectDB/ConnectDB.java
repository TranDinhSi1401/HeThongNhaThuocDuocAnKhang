/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.connectDB;
import java.sql.*;

/**
 *
 * @author trand
 */
public class ConnectDB {
    public static Connection con = null;
    private static ConnectDB instance = new ConnectDB();
    
    public static ConnectDB getInstance() {
        return instance;
    }
    
    public void connect() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databasename=DuocAnKhang";
        String user = "sa";
        String password = "123456";
        con = DriverManager.getConnection(url, user, password);
    }
    
    public void disconnect() throws SQLException {
        if(con != null) {
            con.close();
        }
    }
    public static Connection getConnection() {
        return con;
    }
}
