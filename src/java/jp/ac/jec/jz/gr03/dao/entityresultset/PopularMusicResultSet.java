package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.entity.PopularMusic;

/**
 *
 * @author yada
 */
public class PopularMusicResultSet extends EntityResultSet<PopularMusic> {

    public PopularMusicResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected PopularMusic pickEntityFrom(Map<String, Object> row) {
        PopularMusic pm = new PopularMusic();

        Integer musicId = (Integer) row.get("music_id");
        if (musicId != null) {
            MusicDAO dao = new MusicDAO();
            try {
                pm.music = dao.selectById(musicId);
            } catch (IOException ex) {
                Logger.getLogger(PopularMusicResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Number advertisementCount = (Number) row.get("advertisement_count");
        pm.advertisementCount = advertisementCount != null ? advertisementCount.longValue() : null;
        Number spentPointsSum = (Number) row.get("spent_points_sum");
        pm.spentPointsSum = spentPointsSum != null ? spentPointsSum.longValue() : null;

        return pm;
    }

}
