package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.TagScoreResultSet;
import jp.ac.jec.jz.gr03.entity.TagScore;

/**
 *
 * @author yada
 */
public class TagScoreDAO extends DAO {

    public TagScore selectById(int userId, int tagId) throws IOException {
        String sql = "select * from tag_scores where user_id = ? and tag_id = ? limit 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);
            ps.setObject(idx++, tagId, Types.INTEGER);

            TagScoreResultSet results = new TagScoreResultSet(ps.executeQuery());
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void insert(TagScore tagScore) throws IOException {
        String sql = "insert into tag_scores("
                + "user_id, "
                + "tag_id, "
                + "score) "
                + "values(?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, (tagScore.user != null ? tagScore.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, (tagScore.tag != null ? tagScore.tag.tagId : null), Types.INTEGER);
            ps.setObject(idx++, tagScore.score, Types.INTEGER);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void update(TagScore tagScore) throws IOException {
        String sql = "update tag_scores set "
                + "score = ? "
                + "where user_id = ? and tag_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, tagScore.score, Types.INTEGER);
            ps.setObject(idx++, (tagScore.user != null ? tagScore.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, (tagScore.tag != null ? tagScore.tag.tagId : null), Types.INTEGER);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
