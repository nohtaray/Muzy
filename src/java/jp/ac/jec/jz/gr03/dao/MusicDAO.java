package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet;
import jp.ac.jec.jz.gr03.entity.Music;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author yada
 */
public class MusicDAO extends DAO {

    public enum Order {
        CREATED_AT,
        COMMENT_CREATED_AT,
        VIEW,
        MYLIST,
        UNSPECIFIED,
    }
    public Music selectById(Integer musicId) throws IOException {
        try {
            String sql = "select * from musics where music_id = ? limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, musicId, Types.INTEGER);

            MusicResultSet results = new MusicResultSet(ps.executeQuery());
            
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public Object selectByYoutubeVideoId(String youtubeVideoId) throws IOException {
        String sql = "select * from musics where youtube_video_id = ? limit 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, youtubeVideoId, Types.VARCHAR);
            
            MusicResultSet results = new MusicResultSet(ps.executeQuery());
            
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public MusicResultSet selectByArtistId(int artistId) throws IOException {
        try {
            String sql = "select * from musics where artist_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, artistId, Types.INTEGER);

            return new MusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public MusicResultSet selectAll() throws IOException {
        try {
            String sql = "select * from musics";
            PreparedStatement ps = conn.prepareStatement(sql);

            return new MusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    /**
     * title または description に keyword を含む楽曲をすべて返します
     *
     * @param keyword
     * @return
     * @throws IOException
     */
    public MusicResultSet selectByKeyword(String keyword) throws IOException {
        return selectByKeyword(keyword, Order.UNSPECIFIED);
    }
    public MusicResultSet selectByKeyword(String keyword, Order order) throws IOException {
        String sql;
        if (order == Order.CREATED_AT) {
            sql = "select * from musics where title like ? or description like ? order by created_at desc";
        } else if (order == Order.COMMENT_CREATED_AT) {
            sql = "select * from musics as m where title like ? or description like ? order by (select max(created_at) from comments as c where c.music_id = m.music_id) desc";
        } else if (order == Order.VIEW) {
            sql = "select * from musics where title like ? or description like ? order by view_count desc";
        } else if (order == Order.MYLIST) {
            sql = "select * from musics as m where title like ? or description like ? order by (select count(*) from mylist_details as md where md.music_id = m.music_id) desc";
        } else {
            sql = "select * from musics where title like ? or description like ?";
        }
    
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            String like = "%" + keyword.replaceAll("[\\\\%_]", "\\$0") + "%";
            int idx = 1;
            ps.setObject(idx++, like, Types.VARCHAR);
            ps.setObject(idx++, like, Types.VARCHAR);

            return new MusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public MusicResultSet selectLatests(int limit, int offset) throws IOException {
        try {
            String sql = "select * from musics order by created_at desc limit ? offset ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, limit, Types.INTEGER);
            ps.setObject(idx++, offset, Types.INTEGER);

            return new MusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public MusicResultSet selectMostViewed(int limit, int offset) throws IOException {
        String sql = "select * from musics order by view_count desc limit ? offset ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, limit, Types.INTEGER);
            ps.setObject(idx++, offset, Types.INTEGER);

            return new MusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    /**
     * music.artist は artistId のみ insert します
     *
     * @param music
     * @throws IOException
     */
    public void insert(Music music) throws IOException {
        String sql = "insert into musics("
                + "artist_id, "
                + "youtube_video_id, "
                + "view_count, "
                + "title, "
                + "description,"
                + "created_at, "
                + "updated_at, "
                + "is_deleted) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setObject(idx++, (music.artist != null ? music.artist.artistId : null), Types.INTEGER);
            ps.setObject(idx++, music.youtubeVideoId, Types.VARCHAR);
            ps.setObject(idx++, music.viewCount, Types.INTEGER);
            ps.setObject(idx++, music.title, Types.VARCHAR);
            ps.setObject(idx++, music.description, Types.VARCHAR);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, expressAsInteger(music.isDeleted), Types.INTEGER);

            ps.execute();

            music.musicId = getGeneratedKey(ps);
            music.createdAt = Date.now();
            music.updatedAt = Date.now();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    /**
     * music.artist は artistId のみ update します
     *
     * @param music
     * @throws IOException
     */
    public void update(Music music) throws IOException {
        String sql = "update musics set "
                + "artist_id = ?, "
                + "youtube_video_id = ?, "
                + "view_count = ?, "
                + "title = ?, "
                + "description = ?,"
                + "updated_at = ?, "
                + "is_deleted = ? "
                + "where music_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, (music.artist != null ? music.artist.artistId : null), Types.INTEGER);
            ps.setObject(idx++, music.youtubeVideoId, Types.VARCHAR);
            ps.setObject(idx++, music.viewCount, Types.INTEGER);
            ps.setObject(idx++, music.title, Types.VARCHAR);
            ps.setObject(idx++, music.description, Types.VARCHAR);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, expressAsInteger(music.isDeleted), Types.INTEGER);
            ps.setObject(idx++, music.musicId, Types.INTEGER);

            ps.execute();

            music.updatedAt = Date.now();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void delete(Music music) throws IOException {
        try {
            String sql = "delete from musics where music_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, music.musicId, Types.INTEGER);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

}
