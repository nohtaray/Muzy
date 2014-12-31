package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.entity.PopularArtist;

/**
 *
 * @author yada
 */
public class PopularArtistResultSet extends EntityResultSet<PopularArtist> {

    public PopularArtistResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected PopularArtist pickEntityFrom(Map<String, Object> row) {
        PopularArtist pa = new PopularArtist();
        
        Integer artistId = (Integer) row.get("artist_id");
        if (artistId != null) {
            ArtistDAO dao = new ArtistDAO();
            try {
                pa.artist = dao.selectById(artistId);
            } catch (IOException ex) {
                Logger.getLogger(PopularArtistResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Number voteCount = (Number) row.get("vote_count");
        pa.voteCount = voteCount != null ? voteCount.longValue() : null;
        Number spentTicketsSum = (Number) row.get("spent_tickets_sum");
        pa.spentTicketsSum = spentTicketsSum != null ? spentTicketsSum.longValue() : null;
        
        return pa;
    }
    
}
