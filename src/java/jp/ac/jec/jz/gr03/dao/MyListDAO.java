// Error reading included file Templates/Classes/Templates/Licenses/license-default_1.txt
package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import static jp.ac.jec.jz.gr03.dao.DAO.expressAsInteger;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.MyListResultSet;
import jp.ac.jec.jz.gr03.entity.MyList;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author 12jz0121
 */
public class MyListDAO extends DAO {

    
    public MyList selectById(Integer myListId) throws IOException {
        try {
            String sql = "select * from mylists where mylist_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, myListId, Types.INTEGER);

            MyListResultSet results = new MyListResultSet(ps.executeQuery());
            
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
    public MyListResultSet selectByUserId(int userId) throws IOException {
        try {
            String sql = "select * from mylists where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);
            
            return new MyListResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
    public ResultSet mylistDetailById(int userId) throws SQLException{
        String sql = "select mylist_id, name, youtube_video_id, mylists.updated_at from musics left join mylist_details using(music_id) left join mylists using(mylist_id) where user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        int idx = 1;
        ps.setObject(idx++, userId, Types.INTEGER);
        return ps.executeQuery();
    }
    public ResultSet musicThumbnailById(int userId) throws SQLException{
        String sql = "select youtube_video_id , title  from mylist_details join musics using(music_id) where mylist_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        int idx = 1;
        ps.setObject(idx++, userId, Types.INTEGER);
        return ps.executeQuery();
    }
    
    /**
     * music.artist は artistId のみ insert します
     * @param music
     * @throws IOException 
     */
    public void insert(MyList mylist) throws IOException {
        String sql = "insert into mylists("
                + "user_id, "
                + "created_at, "
                + "updated_at, "
                + "name)"
                + "values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setObject(idx++, (mylist.user != null ? mylist.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, mylist.name, Types.VARCHAR);
            
            ps.execute();
            
            mylist.mylist_id = getGeneratedKey(ps);
            mylist.created_at = Date.now();
            mylist.updated_at = Date.now();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    /**
     * music.artist は artistId のみ update します
     * @param music
     * @throws IOException 
     */
    public void update(MyList mylist) throws IOException {
        String sql = "update mylists set "
                + "updated_at = ?, "
                + "name = ? "
                + "where mylist_id_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, mylist.name, Types.VARCHAR);
            ps.setObject(idx++, mylist.mylist_id, Types.INTEGER);
            
            ps.execute();
            
            mylist.updated_at = Date.now();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public void delete(int mylistid) throws IOException {
        try {
            String sql = "delete from mylists where mylist_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, mylistid, Types.INTEGER);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public MyList selectNameByUserId(Integer userId) throws IOException {
        try {
            String sql = "select name from mylists where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);

            MyListResultSet results = new MyListResultSet(ps.executeQuery());
            
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}