package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MyListDetailResultSet;
import jp.ac.jec.jz.gr03.entity.MyList_Details;

/**
 *
 * @author 12jz0121
 */


public class MyListDetailDAO extends DAO {
    public MyListDetailResultSet selectLatestsByMylistId(int mylistId) throws IOException, SQLException {
        try {
            String sql = "select * from mylist_details where mylist_id = ? order by created_at desc limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, mylistId, Types.INTEGER);
            return new MyListDetailResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public void insert(MyList_Details mylistDetail) throws IOException {
        String sql = "insert into mylist_details("
                + "mylist_id,"
                + "music_id, "
                + "created_at, "
                + "updated_at) "
                + "values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int idx = 1;
            
            ps.setObject(idx++, (mylistDetail.mylist != null ? mylistDetail.mylist.mylist_id : null), Types.INTEGER);
            ps.setObject(idx++, mylistDetail.music_id , Types.INTEGER);
            ps.setObject(idx++, mylistDetail.created_at, Types.DATE);
            ps.setObject(idx++, mylistDetail.updated_at, Types.DATE);
            
            ps.execute();
            
            mylistDetail.mylist_detail_id = getGeneratedKey(ps);
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
    public MyListDetailResultSet selectByMylistId(int mylistId) throws IOException, SQLException {
        try {
            String sql = "select * from mylist_details where mylist_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, mylistId, Types.INTEGER);
            return new MyListDetailResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public ResultSet musicThumbnailById(int userId) throws SQLException{
        String sql = "select youtube_video_id, music_id, title, mylist_detail_id from mylist_details join musics using(music_id) where mylist_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        int idx = 1;
        ps.setObject(idx++, userId, Types.INTEGER);
        return ps.executeQuery();
    }
    public ResultSet artistThumbnailById(int mylistId) throws SQLException{
        String sql = "select artist_id, name, header_image_file, mylist_detail_id from artists left join mylist_details using(artist_id) where mylist_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        int idx = 1;
        ps.setObject(idx++, mylistId, Types.INTEGER);
        return ps.executeQuery();
    }
    
}
