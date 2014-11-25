package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet;

/**
 *
 * @author yada
 */
public class RankingDAO extends DAO {

    public MusicResultSet selectMusics(int limit, int offset) throws IOException {
        try {
            // TODO: 順番を決める
            // limit, offset は良くないらしい togetter.com/li/640847
            String sql = "select * from musics order by updated_at desc limit ? offset ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, limit, Types.INTEGER);
            ps.setObject(idx++, offset, Types.INTEGER);

            return new MusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public ArtistResultSet selectArtists(int limit, int offset) throws IOException {
        try {
            // TODO: 順番を決める
            String sql = "select * from artists order by updated_at desc limit ? offset ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, limit, Types.INTEGER);
            ps.setObject(idx++, offset, Types.INTEGER);

            return new ArtistResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
