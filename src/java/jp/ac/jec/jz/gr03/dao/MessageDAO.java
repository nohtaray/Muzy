package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MessageResultSet;
import jp.ac.jec.jz.gr03.entity.Message;

/**
 *
 * @author yada
 */
public class MessageDAO extends DAO {

    public Message selectById(int artistId, int messageId) throws IOException {
        String sql = "select * from messages where artist_id = ? and message_id = ? limit 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, artistId, Types.INTEGER);
            ps.setObject(idx++, messageId, Types.INTEGER);
            
            MessageResultSet results = new MessageResultSet(ps.executeQuery());
            
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public MessageResultSet selectByArtistId(int artistId) throws IOException {
        String sql = "select * from messages where artist_id = ? order by message_id desc";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, artistId, Types.INTEGER);

            return new MessageResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
