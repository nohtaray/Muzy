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
        try {
            String sql = "select * from musics where title like ? or description like ?";
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
