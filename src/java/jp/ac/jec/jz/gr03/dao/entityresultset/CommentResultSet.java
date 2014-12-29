package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.Comment;

/**
 *
 * @author yada
 */
public class CommentResultSet extends EntityResultSet<Comment> {

    public CommentResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected Comment pickEntityFrom(Map<String, Object> row) {
        Comment comment = new Comment();

        comment.commentId = (Integer)row.get("comment_id");
        Integer userId = (Integer)row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                comment.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(CommentResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        comment.content = (String)row.get("content");
        Integer musicId = (Integer)row.get("music_id");
        if (musicId != null) {
            MusicDAO dao = new MusicDAO();
            try {
                comment.music = dao.selectById(musicId);
            } catch (IOException ex) {
                Logger.getLogger(CommentResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        comment.scorePlusCount = (Integer)row.get("score_plus_count");
        comment.scoreMinusCount = (Integer)row.get("score_minus_count");
        comment.createdAt = (Timestamp)row.get("created_at");
        Integer isDeleted = (Integer)row.get("is_deleted");
        comment.isDeleted = isDeleted != null ? isDeleted != 0 : null;
        
        return comment;
    }
    
}
