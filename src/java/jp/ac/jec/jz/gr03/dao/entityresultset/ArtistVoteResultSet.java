package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.entity.ArtistVote;

/**
 *
 * @author yada
 */
public class ArtistVoteResultSet extends EntityResultSet<ArtistVote> {

    public ArtistVoteResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected ArtistVote pickEntityFrom(Map<String, Object> row) {
        ArtistVote vote = new ArtistVote();
        
        Integer artistId = (Integer) row.get("artist_id");
        if (artistId != null) {
            ArtistDAO dao = new ArtistDAO();
            try {
                vote.artist = dao.selectById(artistId);
            } catch (IOException ex) {
                Logger.getLogger(ArtistVoteResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Number voteCount = (Number) row.get("vote_count");
        vote.voteCount = voteCount != null ? voteCount.longValue() : null;
        Number spentTicketsSum = (Number) row.get("spent_tickets_sum");
        vote.spentTicketsSum = spentTicketsSum != null ? spentTicketsSum.longValue() : null;
        
        return vote;
    }
    
}
