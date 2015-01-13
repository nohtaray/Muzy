package jp.ac.jec.jz.gr03.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yada
 */
abstract class DAO {
    // 特に短時間に連続でアクセスしたときなどに、
    // コネクションが閉じられてしまっていて
    // 例外が発生することがあるため（原因未調査）、static にしている。
    // コネクションが1つになるので、
    // 短時間に大量のアクセスがあるとサイト全体が止まるが、
    // 小規模サイトなので問題ない想定。
    protected static Connection conn;
    
    // ファイルに切り出したほうがいい
    private final static String DRIVER_URL = "jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull&connectTimeout=5000";
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
        try {
            conn = DriverManager.getConnection(DRIVER_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void destruction() {
        
    }
    @Override
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            destruction();
        }
    }
    
    protected static int getGeneratedKey(PreparedStatement ps) throws SQLException {
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
    }
    
    protected static Integer expressAsInteger(Boolean b) {
        if (b == null) {
            return null;
        } else {
            return b ? 1 : 0;
        }
    } 
}
