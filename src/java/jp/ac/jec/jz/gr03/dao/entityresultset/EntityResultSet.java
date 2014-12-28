package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author yada
 * @param <T>
 */
abstract public class EntityResultSet<T> implements Iterator<T>, Iterable<T> {
    
    private final ResultSet rs;
    
    public EntityResultSet(ResultSet rs) {
        this.rs = rs;
    }
    
    /**
     * ResultSet を1行進め、その行をエンティティとして返す。
     * 取り出せない場合は null を返す。
     * @return
     * @throws SQLException 
     */
    public T readRow() throws SQLException {
        // resultSet.getXXX(colName) すると、colName が存在しない場合例外が発生するので
        // まず存在する列名をすべて Map に入れて、そこから取得を試みる。
        Map<String, Object> row = readMapRow(rs);
        if (row != null) {
            return pickEntityFrom(row);
        } else {
            return null;
        }
    }
    
    /**
     * MapからEntityを取り出す。MapのキーはDBのテーブル名。
     * 型参考: http://dounanda.s140.xrea.com/mapping.html
     * @param row
     * @return 
     */
    abstract protected T pickEntityFrom(Map<String, Object> row);
    
    /**
     * ResultSet を1行進め、その行を Map として返す。
     * 取り出せない場合は null を返す。
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Map<String, Object> readMapRow(ResultSet rs)
            throws SQLException {
        boolean rowExists = rs.next();
        if (!rowExists) return null;
        
        Map<String, Object> row = new HashMap<>();
        ResultSetMetaData meta = rs.getMetaData();
        
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            row.put(meta.getColumnName(i), rs.getObject(i));
        }
        
        return row;
    }

    @Override
    public boolean hasNext() {
        try {
            boolean has = rs.next();
            rs.previous(); // カーソルが進んじゃうので戻す
            return has;
        } catch (SQLException ex) {
            Logger.getLogger(EntityResultSet.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public T next() {
        try {
            return readRow();
        } catch (SQLException ex) {
            Logger.getLogger(EntityResultSet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }
}
