package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet;
import jp.ac.jec.jz.gr03.entity.Artist;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author yada
 */
public class ArtistDAO extends DAO {

    public Artist selectById(Integer artistId) throws IOException {
        try {
            String sql = "select * from artists where artist_id = ? limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, artistId, Types.INTEGER);

            ArtistResultSet results = new ArtistResultSet(ps.executeQuery());
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public Artist selectByUserId(int userId) throws IOException {
        try {
            String sql = "select * from artists where user_id = ? limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);

            ArtistResultSet results = new ArtistResultSet(ps.executeQuery());
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
    /**
     * artist.user は userId のみ insert します
     * @param artist
     * @throws IOException 
     */
    public void insert(Artist artist) throws IOException {
        // シンタックスエラーに気をつけて
        String sql = "insert into artists("
                + "user_id, "
                + "name, "
                + "introduction, "
                + "header_image_file, "
                + "created_at, "
                + "updated_at) "
                + "values(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int idx = 1;
            
            ps.setObject(idx++, (artist.user != null ? artist.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, artist.name, Types.VARCHAR);
            ps.setObject(idx++, artist.introduction, Types.VARCHAR);
            ps.setObject(idx++, artist.headerImageFile, Types.VARCHAR);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            
            ps.execute();
            
            artist.artistId = getGeneratedKey(ps);
            artist.createdAt = Date.now();
            artist.updatedAt = Date.now();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    /**
     * artist.user は userId のみ update します
     * @param artist
     * @throws IOException 
     */
    public void update(Artist artist) throws IOException {
        // シンタックスエラー気をつけて
        String sql = "update artists set "
                + "user_id = ?, "
                + "name = ?, "
                + "introduction = ?, "
                + "header_image_file = ?, "
                + "updated_at = ? "
                + "where artist_id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            // 数と順番に気をつけて
            ps.setObject(idx++, (artist.user != null ? artist.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, artist.name, Types.VARCHAR);
            ps.setObject(idx++, artist.introduction, Types.VARCHAR);
            ps.setObject(idx++, artist.headerImageFile, Types.VARCHAR);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, artist.artistId, Types.INTEGER);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
        // アップデートしたよ
        artist.updatedAt = Date.now();
    }
    public void delete(Artist artist) throws IOException {
        try {
            String sql = "delete from artists where artist_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, artist.artistId, Types.INTEGER);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

}
