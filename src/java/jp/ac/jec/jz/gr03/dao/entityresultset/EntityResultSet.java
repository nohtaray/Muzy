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
abstract public class EntityResultSet<T> implements Iterable<T> {

    private final ResultSet rs;
    private Iterator<T> it;

    public EntityResultSet(ResultSet rs) {
        this.rs = rs;
        this.it = null;
    }

    /**
     * ResultSet を1行進め、その行をエンティティとして返す。 取り出せない場合は null を返す。
     *
     * @return
     */
    public T readRow() {
        if (it == null) {
            it = iterator();
        }
        return it.next();
    }

    /**
     * MapからEntityを取り出す。MapのキーはDBのテーブル名。 型参考:
     * http://dounanda.s140.xrea.com/mapping.html
     *
     * @param row
     * @return
     */
    abstract protected T pickEntityFrom(Map<String, Object> row);
    
    @Override
    public Iterator<T> iterator() {
        return new EntityResultSetIterator(rs);
    }

    private class EntityResultSetIterator implements Iterator<T> {

        private final ResultSet rs;

        public EntityResultSetIterator(ResultSet rs) {
            try {
                rs.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(EntityResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.rs = rs;
        }

        @Override
        public boolean hasNext() {
            try {
                boolean has = rs.next();
                rs.previous(); // カーソルが進んじゃうので戻す
                return has;
            } catch (SQLException ex) {
                Logger.getLogger(EntityResultSetIterator.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            
            // resultSet.getXXX(colName) すると、colName が存在しない場合例外が発生するので
            // まず存在する列名をすべて Map に入れて、そこから取得を試みる。
            Map<String, Object> row = new HashMap<>();
            try {
                rs.next();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
            } catch (SQLException ex) {
                // 例外の握り潰し（どうするのがいいんだろう）
                Logger.getLogger(EntityResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
            return pickEntityFrom(row);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }
}
