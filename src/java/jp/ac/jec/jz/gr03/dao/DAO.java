package jp.ac.jec.jz.gr03.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yada
 */
abstract class DAO {
    protected final Connection conn;
    
    // ファイルに切り出したほうがいい
    private final static String DRIVER_URL = "jdbc:mysql://dev.yada.jp:3306/muzy?zeroDateTimeBehavior=convertToNull&connectTimeout=5000";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "rootroot";
    
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public DAO() {
        Connection _conn;
        try {
            _conn = DriverManager.getConnection(DRIVER_URL, DB_USER, DB_PASS);
        } catch (SQLException ex) {
            _conn = null;
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.conn = _conn;
    }
    
    private void destruction() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            destruction();
        }
    }
    
    protected int getGeneratedKey(PreparedStatement ps) throws SQLException {
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
    }
}
