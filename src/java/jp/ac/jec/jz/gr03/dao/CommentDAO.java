package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.CommentResultSet;
import jp.ac.jec.jz.gr03.entity.Comment;

/**
 *
 * @author yada
 */
public class CommentDAO extends DAO {
    public Comment selectById(int id) throws IOException {
        String sql = "select * from comments where comment_id = ? limit 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, id, Types.INTEGER);
            
            CommentResultSet results = new CommentResultSet(ps.executeQuery());
            
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
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
    public void delete(int commentId) throws IOException {
        try {
            String sql = "delete from comments where comment_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, commentId, Types.INTEGER);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public void update(Comment comment) throws IOException {
        String sql = "update comments set "
                + "user_id = ?, "
                + "content = ?, "
                + "music_id = ?, "
                + "score_plus_count = ?, "
                + "score_minus_count = ?, "
                + "is_deleted = ? "
                + "where comment_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, (comment.user != null ? comment.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, comment.content, Types.VARCHAR);
            ps.setObject(idx++, (comment.music != null ? comment.music.musicId : null), Types.INTEGER);
            ps.setObject(idx++, comment.scorePlusCount, Types.INTEGER);
            ps.setObject(idx++, comment.scoreMinusCount, Types.INTEGER);
            ps.setObject(idx++, expressAsInteger(comment.isDeleted), Types.INTEGER);
            ps.setObject(idx++, comment.commentId, Types.INTEGER);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
