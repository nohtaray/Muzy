package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.entity.MusicMylistDetail;

/**
 *
 * @author yada
 */
public class MusicMylistDetailResultSet extends EntityResultSet<MusicMylistDetail> {

    public MusicMylistDetailResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected MusicMylistDetail pickEntityFrom(Map<String, Object> row) {
        MusicMylistDetail mmd = new MusicMylistDetail();
        
        Integer musicId = (Integer) row.get("music_id");
        if (musicId != null) {
            MusicDAO dao = new MusicDAO();
            try {
                mmd.music = dao.selectById(musicId);
            } catch (IOException ex) {
                Logger.getLogger(MusicMylistDetailResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Number mylistDetailCount = (Number) row.get("mylist_detail_count");
        mmd.mylistDetailCount = mylistDetailCount != null ? mylistDetailCount.longValue() : null;
        
        return mmd;
    }
    
}
