package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MylistDetailResultSet;
import jp.ac.jec.jz.gr03.entity.MylistDetail;

public class MylistDetailDAO extends DAO {

    public MylistDetailResultSet selectLatestsByMylistId(int mylistId) throws IOException, SQLException {
        try {
            String sql = "select * from mylist_details where mylist_id = ? order by created_at desc limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, mylistId, Types.INTEGER);
            return new MylistDetailResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    /**
     * メソッド名がわかりづらい
     *
     * @param userId
     * @return
     * @throws IOException
     */
    public MylistDetailResultSet select1FromEachMylists(int userId) throws IOException {
        // SQL 汚すぎ
        // 自分の持ってるマイリストの中の最新のdetailを1つ以下取り出し、マイリストの作成順に返す
        String sql = "select * from (select * from (select mylist_id, created_at as mylist_created_at from mylists where user_id = ?) as m left join mylist_details using(mylist_id) order by created_at desc) as d group by mylist_id order by mylist_created_at desc";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);

            return new MylistDetailResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void insert(MylistDetail mylistDetail) throws IOException {
        String sql = "insert into mylist_details("
                + "mylist_id,"
                + "music_id, "
                + "artist_id, "
                + "created_at, "
                + "updated_at) "
                + "values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;

            ps.setObject(idx++, (mylistDetail.mylist != null ? mylistDetail.mylist.mylistId : null), Types.INTEGER);
            ps.setObject(idx++, (mylistDetail.music != null ? mylistDetail.music.musicId : null), Types.INTEGER);
            ps.setObject(idx++, (mylistDetail.artist != null ? mylistDetail.artist.artistId : null), Types.INTEGER);
            ps.setObject(idx++, mylistDetail.createdAt, Types.DATE);
            ps.setObject(idx++, mylistDetail.updatedAt, Types.DATE);

            ps.execute();

            mylistDetail.mylistDetailId = getGeneratedKey(ps);
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void delete(int detailId) throws IOException {
        try {
            String sql = "delete from mylist_details where mylist_detail_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, detailId, Types.INTEGER);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public MylistDetailResultSet selectByMylistId(int mylistId) throws IOException, SQLException {
        try {
            String sql = "select * from mylist_details where mylist_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, mylistId, Types.INTEGER);
            return new MylistDetailResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public ResultSet musicThumbnailById(int userId) throws SQLException {
        String sql = "select youtube_video_id, music_id, title, mylist_detail_id from mylist_details join musics using(music_id) where mylist_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        int idx = 1;
        ps.setObject(idx++, userId, Types.INTEGER);
        return ps.executeQuery();
    }

    public ResultSet artistThumbnailById(int mylistId) throws SQLException {
        String sql = "select artist_id, name, header_image_file, mylist_detail_id from artists left join mylist_details using(artist_id) where mylist_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        int idx = 1;
        ps.setObject(idx++, mylistId, Types.INTEGER);
        return ps.executeQuery();
    }

}
