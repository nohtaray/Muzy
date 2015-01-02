package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.Vote;

/**
 *
 * @author yada
 */
public class VoteResultSet extends EntityResultSet<Vote> {

    public VoteResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected Vote pickEntityFrom(Map<String, Object> row) {
        Vote vote = new Vote();
        
        vote.voteId = (Integer) row.get("vote_id");
        Integer userId = (Integer) row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                vote.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(VoteResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Integer artistId = (Integer) row.get("artist_id");
        if (artistId != null) {
            ArtistDAO dao = new ArtistDAO();
            try {
                vote.artist = dao.selectById(artistId);
            } catch (IOException ex) {
                Logger.getLogger(VoteResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        vote.spentTickets = (Integer) row.get("spent_tickets");
        vote.createdAt = (Timestamp) row.get("created_at");
        
        return vote;
    }
    
}
