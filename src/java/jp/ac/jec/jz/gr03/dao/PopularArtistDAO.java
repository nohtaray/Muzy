package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.PopularArtistResultSet;

/**
 *
 * @author yada
 */
public class PopularArtistDAO extends DAO {

    public PopularArtistResultSet select(int limit, int offset) throws IOException {
        String sql = "select artist_id, count(*) as vote_count, sum(spent_tickets) as spent_tickets_sum "
                + "from votes "
                + "group by artist_id "
                + "order by spent_tickets_sum desc "
                + "limit ? offset ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, limit, Types.INTEGER);
            ps.setObject(idx++, offset, Types.INTEGER);

            return new PopularArtistResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

}
