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

    public enum Order {

        CREATED_AT,
        MYLIST,
        VOTE,
        UNSPECIFIED,
    }

    public ArtistResultSet selectAll() throws IOException {
        try {
            String sql = "select * from artists";
            PreparedStatement ps = conn.prepareStatement(sql);
            return new ArtistResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

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
     * name または introduction に keyword を含むアーティストをすべて返します
     *
     * @param keyword
     * @return
     * @throws IOException
     */
    public ArtistResultSet selectByKeyword(String keyword) throws IOException {
        return selectByKeyword(keyword, Order.UNSPECIFIED);
    }

    public ArtistResultSet selectByKeyword(String keyword, Order order) throws IOException {
        String sql;
        if (order == Order.CREATED_AT) {
            sql = "select * from artists where name like ? or introduction like ? order by created_at desc";
        } else if (order == Order.MYLIST) {
            sql = "select * from artists as a where name like ? or introduction like ? order by (select count(*) from mylist_details as md where md.artist_id = a.artist_id) desc";
        } else if (order == Order.VOTE) {
            sql = "select * from artists as a where name like ? or introduction like ? order by (select sum(spent_tickets) from votes as v where v.artist_id = a.artist_id) desc";
        } else {
            sql = "select * from artists where name like ? or introduction like ?";
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            String like = "%" + keyword.replaceAll("[\\\\%_]", "\\$0") + "%";
            int idx = 1;
            ps.setObject(idx++, like, Types.VARCHAR);
            ps.setObject(idx++, like, Types.VARCHAR);

            return new ArtistResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    /**
     * artist.user は userId のみ insert します
     *
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
     *
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
