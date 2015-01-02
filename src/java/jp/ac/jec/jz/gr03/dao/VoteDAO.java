package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.entity.Vote;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author yada
 */
public class VoteDAO extends DAO {

    public void insert(Vote vote) throws IOException {
        String sql = "insert into votes("
                + "user_id, "
                + "artist_id, "
                + "spent_tickets, "
                + "created_at) "
                + "values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setObject(idx++, (vote.user != null ? vote.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, (vote.artist != null ? vote.artist.artistId : null), Types.INTEGER);
            ps.setObject(idx++, vote.spentTickets, Types.INTEGER);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            
            ps.execute();
            
            vote.voteId = getGeneratedKey(ps);
        } catch (SQLException e) {
            throw new IOException(e);
        }
        vote.createdAt = Date.now();
    }
}
