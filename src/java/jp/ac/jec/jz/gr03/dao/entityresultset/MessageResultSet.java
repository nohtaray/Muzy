package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.dao.MessageDAO;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.Message;

/**
 *
 * @author yada
 */
public class MessageResultSet extends EntityResultSet<Message> {

    public MessageResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected Message pickEntityFrom(Map<String, Object> row) {
        Message message = new Message();
        
        Integer artistId = (Integer) row.get("artist_id");
        if (artistId != null) {
            ArtistDAO dao = new ArtistDAO();
            try {
                message.artist = dao.selectById(artistId);
            } catch (IOException ex) {
                Logger.getLogger(MessageResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        message.messageId = (Integer) row.get("message_id");
        Integer userId = (Integer) row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                message.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(MessageResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        message.content = (String) row.get("content");
        Integer responseToId = (Integer) row.get("response_to_id");
        if (artistId != null && responseToId != null) {
            MessageDAO dao = new MessageDAO();
            try {
                message.responseTo = dao.selectById(artistId, responseToId);
            } catch (IOException ex) {
                Logger.getLogger(MessageResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        message.createdAt = (Timestamp) row.get("created_at");
        Integer isDeleted = (Integer) row.get("is_deleted");
        message.isDeleted = isDeleted != null ? isDeleted != 0 : null;
        
        return message;
    }
    
}
