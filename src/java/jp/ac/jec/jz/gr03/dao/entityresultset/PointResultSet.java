package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.Point;

/**
 *
 * @author yada
 */
public class PointResultSet extends EntityResultSet<Point> {

    public PointResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected Point pickEntityFrom(Map<String, Object> row) {
        Point point = new Point();
        
        Integer userId = (Integer)row.get("user_id");
        point.user = null;
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                point.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(PointResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        point.pointCount = (Integer)row.get("point_count");
        point.voteTicketCount = (Integer)row.get("vote_ticket_count");
        
        return point;
    }
    
}
