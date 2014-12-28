package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.entity.Tag;

/**
 *
 * @author yada
 */
public class TagResultSet extends EntityResultSet<Tag> {

    public TagResultSet(ResultSet rs) {
        super(rs);
    }

    @Override
    protected Tag pickEntityFrom(Map<String, Object> row) {
        Tag tag = new Tag();
        
        tag.tagId = (Integer)row.get("tag_id");
        Integer musicId = (Integer)row.get("music_id");
        if (musicId != null) {
            MusicDAO dao = new MusicDAO();
            try {
                tag.music = dao.selectById(musicId);
            } catch (IOException ex) {
                Logger.getLogger(TagResultSet.class.getName()).log(Level.SEVERE, null, ex);
                tag.music = null;
            }
        } else {
            tag.music = null;
        }
        tag.name = (String)row.get("name");
        tag.scoreAverage = (Float)row.get("score_average");
        tag.scoreCount = (Integer)row.get("score_count");
        
        return tag;
    }
    
}
