package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.Artist;

/**
 *
 * @author yada
 */
public class ArtistResultSet extends EntityResultSet<Artist> {
    
    public ArtistResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected Artist pickEntityFrom(Map<String, Object> row) {
        Artist artist = new Artist();

        artist.artistId = (Integer)row.get("artist_id");
        Integer userId = (Integer)row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                artist.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(ArtistResultSet.class.getName()).log(Level.SEVERE, null, ex);
                artist.user = null;
            }
        } else {
            artist.user = null;
        }
        artist.name = (String)row.get("name");
        artist.introduction = (String)row.get("introduction");
        artist.headerImageFile = (String)row.get("header_image_file");
        artist.createdAt = (Timestamp)row.get("created_at");
        artist.updatedAt = (Timestamp)row.get("updated_at");

        return artist;
    }
}
