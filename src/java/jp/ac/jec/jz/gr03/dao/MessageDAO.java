package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MessageResultSet;
import jp.ac.jec.jz.gr03.entity.Message;
import jp.ac.jec.jz.gr03.util.Date;

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
    public void insert(Message message) throws IOException {
        String sql = "insert into messages("
                + "artist_id, "
                + "message_id, "
                + "user_id, "
                + "content, "
                + "response_to_id, "
                + "created_at, "
                + "is_deleted) "
                + "values(?, "
                + "(select ifnull(max(message_id) + 1, 1) from messages as tmp where artist_id = ?), "
                + "?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, (message.artist != null ? message.artist.artistId : null), Types.INTEGER);
            ps.setObject(idx++, (message.artist != null ? message.artist.artistId : null), Types.INTEGER);
            ps.setObject(idx++, (message.user != null ? message.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, message.content, Types.VARCHAR);
            ps.setObject(idx++, (message.responseTo != null ? message.responseTo.messageId : null), Types.INTEGER);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, expressAsInteger(message.isDeleted), Types.INTEGER);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
        message.createdAt = Date.now();
    }
}
