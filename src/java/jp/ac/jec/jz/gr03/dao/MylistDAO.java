// Error reading included file Templates/Classes/Templates/Licenses/license-default_1.txt
package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MylistResultSet;
import jp.ac.jec.jz.gr03.entity.Mylist;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author 12jz0121
 */
public class MylistDAO extends DAO {

    
    public Mylist selectById(Integer myListId) throws IOException {
        try {
            String sql = "select * from mylists where mylist_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, myListId, Types.INTEGER);

            MylistResultSet results = new MylistResultSet(ps.executeQuery());
            
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
    public MylistResultSet selectByUserId(int userId) throws IOException {
        try {
            String sql = "select * from mylists where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);
            
            return new MylistResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
    public ResultSet mylistDetailById(int userId) throws SQLException{
        String sql = "select mylist_id, name, youtube_video_id, mylists.updated_at from mylists left join mylist_details using(mylist_id) join musics using(music_id) where user_id = ?";
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
     * @param mylist
     * @throws IOException 
     */
    public void insert(Mylist mylist) throws IOException {
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
            
            mylist.mylistId = getGeneratedKey(ps);
            mylist.createdAt = Date.now();
            mylist.updatedAt = Date.now();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    /**
     * @param mylist
     * @throws IOException 
     */
    public void update(Mylist mylist) throws IOException {
        String sql = "update mylists set "
                + "user_id = ?, "
                + "name = ?, "
                + "updated_at = ? "
                + "where mylist_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, (mylist.user != null ? mylist.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, mylist.name, Types.VARCHAR);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, mylist.mylistId, Types.INTEGER);
            
            ps.setObject(idx++, mylist.mylistId, Types.INTEGER);
            ps.execute();
            
            mylist.updatedAt = Date.now();
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
}