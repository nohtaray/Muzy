package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.AdvertisementResultSet;
import jp.ac.jec.jz.gr03.entity.Advertisement;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author yada
 */
public class AdvertisementDAO extends DAO {
    
    public void insert(Advertisement advertisement) throws IOException {
        String sql = "insert into advertisements("
                + "user_id, "
                + "music_id, "
                + "spent_points, "
                + "created_at, "
                + "updated_at) "
                + "values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int idx = 1;
            
            ps.setObject(idx++, (advertisement.user != null ? advertisement.user.userId : null), Types.INTEGER);
            ps.setObject(idx++, (advertisement.music != null ? advertisement.music.musicId : null), Types.INTEGER);
            ps.setObject(idx++, advertisement.spentPoints, Types.INTEGER);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            
            ps.execute();
            
            advertisement.advertisementId = getGeneratedKey(ps);
            advertisement.createdAt = Date.now();
            advertisement.updatedAt = Date.now();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
