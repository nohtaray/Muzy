package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.Advertisement;

/**
 *
 * @author yada
 */
public class AdvertisementResultSet extends EntityResultSet<Advertisement> {

    public AdvertisementResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected Advertisement pickEntityFrom(Map<String, Object> row) {
        Advertisement advertisement = new Advertisement();
        
        advertisement.advertisementId = (Integer)row.get("advertisement_id");
        Integer userId = (Integer)row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                advertisement.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(AdvertisementResultSet.class.getName()).log(Level.SEVERE, null, ex);
                advertisement.user = null;
            }
        } else {
            advertisement.user = null;
        }
        Integer musicId = (Integer)row.get("music_id");
        if (musicId != null) {
            MusicDAO dao = new MusicDAO();
            try {
                advertisement.music = dao.selectById(musicId);
            } catch (IOException ex) {
                Logger.getLogger(AdvertisementResultSet.class.getName()).log(Level.SEVERE, null, ex);
                advertisement.music = null;
            }
        } else {
            advertisement.music = null;
        }
        advertisement.spentPoints = (Integer)row.get("spent_points");
        advertisement.createdAt = (Timestamp)row.get("created_at");
        advertisement.updatedAt = (Timestamp)row.get("updated_at");
        
        return advertisement;
    }
    
}
