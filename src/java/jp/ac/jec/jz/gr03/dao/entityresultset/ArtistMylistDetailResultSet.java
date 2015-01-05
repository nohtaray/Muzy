package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.entity.ArtistMylistDetail;

/**
 *
 * @author yada
 */
public class ArtistMylistDetailResultSet extends EntityResultSet<ArtistMylistDetail> {

    public ArtistMylistDetailResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected ArtistMylistDetail pickEntityFrom(Map<String, Object> row) {
        ArtistMylistDetail amd = new ArtistMylistDetail();

        Integer artistId = (Integer) row.get("artist_id");
        if (artistId != null) {
            ArtistDAO dao = new ArtistDAO();
            try {
                amd.artist = dao.selectById(artistId);
            } catch (IOException ex) {
                Logger.getLogger(ArtistMylistDetailResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Number mylistDetailCount = (Number) row.get("mylist_detail_count");
        amd.mylistDetailCount = mylistDetailCount != null ? mylistDetailCount.longValue() : null;

        return amd;
    }

}
