package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.CommentResultSet;

/**
 *
 * @author yada
 */
public class CommentDAO extends DAO {
    public CommentResultSet selectByMusicId(int musicId) throws IOException {
        try {
            String sql = "select * from comments where music_id = ? order by created_at desc";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, musicId, Types.INTEGER);
            
            return new CommentResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
