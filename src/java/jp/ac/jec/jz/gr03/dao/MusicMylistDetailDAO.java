package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicMylistDetailResultSet;

/**
 *
 * @author yada
 */
public class MusicMylistDetailDAO extends DAO {
    public MusicMylistDetailResultSet selectMostCounts(int limit, int offset) throws IOException {
        String sql = "select music_id, count(*) as mylist_detail_count "
                + "from mylist_details "
                + "group by music_id "
                + "order by mylist_detail_count desc "
                + "limit ? offset ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, limit, Types.INTEGER);
            ps.setObject(idx++, offset, Types.INTEGER);
            
            return new MusicMylistDetailResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
