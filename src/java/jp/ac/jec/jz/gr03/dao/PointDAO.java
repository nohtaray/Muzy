package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.PointResultSet;
import jp.ac.jec.jz.gr03.entity.Point;

/**
 *
 * @author yada
 */
public class PointDAO extends DAO {
    
    public Point selectByUserId(int userId) throws IOException {
        String sql = "select * from points where user_id = ? limit 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);
            
            PointResultSet results = new PointResultSet(ps.executeQuery());
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public void insert(Point point) throws IOException {
        String sql = "insert into points("
                + "user_id, "
                + "point_count, "
                + "vote_ticket_count) "
                + "values(?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, (point.user != null ? point.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, point.pointCount, Types.INTEGER);
            ps.setObject(idx++, point.voteTicketCount, Types.INTEGER);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public void update(Point point) throws IOException {
        String sql = "update points set "
                + "point_count = ?, "
                + "vote_ticket_count = ? "
                + "where user_id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, point.pointCount, Types.INTEGER);
            ps.setObject(idx++, point.voteTicketCount, Types.INTEGER);
            ps.setObject(idx++, (point.user != null ? point.user.userId : null), Types.INTEGER);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
