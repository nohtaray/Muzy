package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.VoteTicketGetHistoryResultSet;

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
}
