package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.TagResultSet;
import jp.ac.jec.jz.gr03.entity.Tag;

/**
 *
 * @author yada
 */
public class TagDAO extends DAO {

    public Tag selectById(int tagId) throws IOException {
        String sql = "select * from tags where tag_id = ? limit 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, tagId, Types.INTEGER);

            TagResultSet results = new TagResultSet(ps.executeQuery());

            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public TagResultSet selectByMusicId(int musicId) throws IOException {
        try {
            String sql = "select * from tags where music_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, musicId, Types.INTEGER);

            return new TagResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public TagResultSet selectByName(String name) throws IOException {
        try {
            String sql = "select * from tags where name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, name, Types.VARCHAR);

            return new TagResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public MusicResultSet selectMusicsByName(String name) throws IOException {
        try {
            String sql = "select * from musics natural join (select distinct music_id from tags where name = ?) music_ids";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, name, Types.VARCHAR);

            return new MusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void insert(Tag tag) throws IOException {
        String sql = "insert into tags("
                + "music_id, "
                + "name, "
                + "score_average, "
                + "score_count) "
                + "values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;

            ps.setObject(idx++, (tag.music != null ? tag.music.musicId : null), Types.INTEGER);
            ps.setObject(idx++, tag.name, Types.VARCHAR);
            ps.setObject(idx++, tag.scoreAverage, Types.DOUBLE);
            ps.setObject(idx++, tag.scoreCount, Types.INTEGER);

            ps.execute();

            tag.tagId = getGeneratedKey(ps);
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
    public void update(Tag tag) throws IOException {
        String sql = "update tags set "
                + "music_id = ?, "
                + "name = ?, "
                + "score_average = ?, "
                + "score_count = ? "
                + "where tag_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, (tag.music != null ? tag.music.musicId : null), Types.INTEGER);
            ps.setObject(idx++, tag.name, Types.VARCHAR);
            ps.setObject(idx++, tag.scoreAverage, Types.DOUBLE);
            ps.setObject(idx++, tag.scoreCount, Types.INTEGER);
            ps.setObject(idx++, tag.tagId, Types.INTEGER);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public void updateScores(Tag tag) throws IOException {
        String sql = "update tags, (select avg(score) as average, count(*) as count from tag_scores where tag_id = ? group by tag_id) as score "
                + "set tags.score_average = score.average, tags.score_count = score.count "
                + "where tag_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, tag.tagId, Types.INTEGER);
            ps.setObject(idx++, tag.tagId, Types.INTEGER);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

}
