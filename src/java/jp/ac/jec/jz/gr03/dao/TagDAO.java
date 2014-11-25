package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.TagResultSet;

/**
 *
 * @author yada
 */
public class TagDAO extends DAO {

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
}
