package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.TagResultSet;
import jp.ac.jec.jz.gr03.entity.Tag;

/**
 *
 * @author yada
 */
public class TagDAO extends DAO {

    public enum Order {
        SCORE_AVERAGE,
        MUSIC_CREATED_AT,
        MUSIC_COMMENT_CREATED_AT,
        MUSIC_VIEW,
        MUSIC_MYLIST,
        UNSPECIFIED,
    }
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
        return selectByName(name, Order.UNSPECIFIED);
    }
    public TagResultSet selectByName(String name, Order order) throws IOException {
        String sql;
        if (order == Order.SCORE_AVERAGE) {
            sql = "select * from tags where name = ? order by score_average desc";
        } else if (order == Order.MUSIC_CREATED_AT) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? order by m.created_at desc";
        } else if (order == Order.MUSIC_COMMENT_CREATED_AT) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? order by (select max(created_at) from comments as c where c.music_id = m.music_id) desc";
        } else if (order == Order.MUSIC_VIEW) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? order by m.view_count desc";
        } else if (order == Order.MUSIC_MYLIST) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? order by (select count(*) from mylist_details as md where md.music_id = m.music_id) desc";
        } else {
            sql = "select * from tags where name = ?";
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, name, Types.VARCHAR);

            return new TagResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public TagResultSet selectByNameAndMusicKeyword(String tagName, String musicKeyword) throws IOException {
        return selectByNameAndMusicKeyword(tagName, musicKeyword, Order.UNSPECIFIED);
    }
    public TagResultSet selectByNameAndMusicKeyword(String tagName, String musicKeyword, Order order) throws IOException {
        String sql;
        if (order == Order.SCORE_AVERAGE) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? and (m.title like ? or m.description like ?) order by t.score_average desc";
        } else if (order == Order.MUSIC_CREATED_AT) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? and (m.title like ? or m.description like ?) order by m.created_at desc";
        } else if (order == Order.MUSIC_COMMENT_CREATED_AT) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? and (m.title like ? or m.description like ?) order by (select max(created_at) from comments as c where c.music_id = m.music_id) desc";
        } else if (order == Order.MUSIC_VIEW) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? and (m.title like ? or m.description like ?) order by m.view_count desc";
        } else if (order == Order.MUSIC_MYLIST) {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? and (m.title like ? or m.description like ?) order by (select count(*) from mylist_details as md where md.music_id = m.music_id) desc";
        } else {
            sql = "select * from tags as t join musics as m using(music_id) where t.name = ? and (m.title like ? or m.description like ?)";
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            String like = "%" + musicKeyword.replaceAll("[\\\\%_]", "\\$0") + "%";
            int idx = 1;
            ps.setObject(idx++, tagName, Types.VARCHAR);
            ps.setObject(idx++, like, Types.VARCHAR);
            ps.setObject(idx++, like, Types.VARCHAR);

            return new TagResultSet(ps.executeQuery());
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

    public void delete(Tag tag) throws IOException {
        String sql = "delete from tags where tag_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, tag.tagId, Types.INTEGER);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
