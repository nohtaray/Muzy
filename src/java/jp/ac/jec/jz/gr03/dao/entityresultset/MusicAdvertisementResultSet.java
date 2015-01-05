package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.entity.MusicAdvertisement;

/**
 *
 * @author yada
 */
public class MusicAdvertisementResultSet extends EntityResultSet<MusicAdvertisement> {

    public MusicAdvertisementResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected MusicAdvertisement pickEntityFrom(Map<String, Object> row) {
        MusicAdvertisement ma = new MusicAdvertisement();

        Integer musicId = (Integer) row.get("music_id");
        if (musicId != null) {
            MusicDAO dao = new MusicDAO();
            try {
                ma.music = dao.selectById(musicId);
            } catch (IOException ex) {
                Logger.getLogger(MusicAdvertisementResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Number advertisementCount = (Number) row.get("advertisement_count");
        ma.advertisementCount = advertisementCount != null ? advertisementCount.longValue() : null;
        Number spentPointsSum = (Number) row.get("spent_points_sum");
        ma.spentPointsSum = spentPointsSum != null ? spentPointsSum.longValue() : null;

        return ma;
    }

}
