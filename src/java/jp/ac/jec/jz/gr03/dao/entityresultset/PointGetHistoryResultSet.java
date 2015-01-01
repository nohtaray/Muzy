package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.PointGetHistory;

/**
 *
 * @author yada
 */
public class PointGetHistoryResultSet extends EntityResultSet<PointGetHistory> {

    public PointGetHistoryResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected PointGetHistory pickEntityFrom(Map<String, Object> row) {
        PointGetHistory history = new PointGetHistory();
        
        Integer userId = (Integer)row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                history.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(PointGetHistoryResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        history.createdAt = (Timestamp)row.get("created_at");
        history.gotPoints = (Integer)row.get("got_points");
        history.description = (String)row.get("description");
        
        return history;
    }
    
}
