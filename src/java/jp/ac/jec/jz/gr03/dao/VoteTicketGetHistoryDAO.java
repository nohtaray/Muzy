package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.VoteTicketGetHistoryResultSet;
import jp.ac.jec.jz.gr03.entity.VoteTicketGetHistory;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author yada
 */
public class VoteTicketGetHistoryDAO extends DAO {
    
    public VoteTicketGetHistoryResultSet selectByUserId(int userId) throws IOException {
        String sql = "select * from vote_ticket_get_histories where user_id = ? order by created_at desc";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);

            return new VoteTicketGetHistoryResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
    public void insert(VoteTicketGetHistory voteTicketGetHistory) throws IOException {
        String sql = "insert into vote_ticket_get_histories("
                + "user_id, "
                + "created_at, "
                + "got_tickets, "
                + "description) "
                + "values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, (voteTicketGetHistory.user != null ? voteTicketGetHistory.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, voteTicketGetHistory.gotTickets, Types.INTEGER);
            ps.setObject(idx++, voteTicketGetHistory.description, Types.VARCHAR);
            
            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
        voteTicketGetHistory.createdAt = Date.now();
    }
}
