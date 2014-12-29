package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.AdvertisementResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.PopularMusicResultSet;

/**
 *
 * @author yada
 */
public class PopularMusicDAO extends DAO {
    public PopularMusicResultSet selectPopularMusics(int limit, int offset) throws IOException {
        String sql = "select music_id, count(*) as advertisement_count, sum(spent_points) as spent_points_sum "
                + "from advertisements "
                + "group by music_id "
                + "order by spent_points_sum desc "
                + "limit ? offset ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, limit, Types.INTEGER);
            ps.setObject(idx++, offset, Types.INTEGER);
            
            return new PopularMusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
