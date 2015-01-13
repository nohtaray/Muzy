package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.VoteTicketGetHistory;

/**
 *
 * @author yada
 */
public class VoteTicketGetHistoryResultSet extends EntityResultSet<VoteTicketGetHistory> {

    public VoteTicketGetHistoryResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected VoteTicketGetHistory pickEntityFrom(Map<String, Object> row) {
        VoteTicketGetHistory history = new VoteTicketGetHistory();
        
        Integer userId = (Integer)row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                history.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(VoteTicketGetHistoryResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        history.createdAt = (Timestamp)row.get("created_at");
        history.gotTickets = (Integer)row.get("got_tickets");
        history.description = (String)row.get("description");
        
        return history;
    }
    
}
