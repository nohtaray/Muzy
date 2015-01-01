package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.PointGetHistoryResultSet;
import jp.ac.jec.jz.gr03.entity.PointGetHistory;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author yada
 */
public class PointGetHistoryDAO extends DAO {

    public PointGetHistoryResultSet selectByUserId(int userId) throws IOException {
        String sql = "select * from point_get_histories where user_id = ? order by created_at desc";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);

            return new PointGetHistoryResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    public void insert(PointGetHistory pointGetHistory) throws IOException {
        String sql = "insert into point_get_histories("
                + "user_id, "
                + "created_at, "
                + "got_points, "
                + "description) "
                + "values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, (pointGetHistory.user != null ? pointGetHistory.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, pointGetHistory.gotPoints, Types.INTEGER);
            ps.setObject(idx++, pointGetHistory.description, Types.VARCHAR);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
        pointGetHistory.createdAt = Date.now();
    }
}
