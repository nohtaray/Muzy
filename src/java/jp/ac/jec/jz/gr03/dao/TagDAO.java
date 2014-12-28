package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.TagResultSet;

/**
 *
 * @author yada
 */
public class TagDAO extends DAO {

    public TagResultSet selectByMusicId(int musicId) throws IOException {
        try {
            String sql = "select * from tags where music_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            int idx = 1;
            ps.setObject(idx++, musicId, Types.INTEGER);
            
            return new TagResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
    public TagResultSet selectByName(String name) throws IOException {
        try {
            String sql = "select * from tags where name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, name, Types.VARCHAR);

            return new TagResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public MusicResultSet selectMusicsByName(String name) throws IOException {
        try {
            String sql = "select * from musics natural join (select distinct music_id from tags where name = ?) music_ids";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, name, Types.VARCHAR);

            return new MusicResultSet(ps.executeQuery());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    
}
