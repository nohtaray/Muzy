package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.TagDAO;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.TagScore;

/**
 *
 * @author yada
 */
public class TagScoreResultSet extends EntityResultSet<TagScore> {

    public TagScoreResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected TagScore pickEntityFrom(Map<String, Object> row) {
        TagScore ts = new TagScore();

        Integer userId = (Integer) row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                ts.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(TagScoreResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Integer tagId = (Integer) row.get("tag_id");
        if (tagId != null) {
            TagDAO dao = new TagDAO();
            try {
                ts.tag = dao.selectById(tagId);
            } catch (IOException ex) {
                Logger.getLogger(TagScoreResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ts.score = (Integer) row.get("score");

        return ts;
    }

}
