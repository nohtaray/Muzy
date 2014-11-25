package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.entity.Music;

/**
 *
 * @author yada
 */
public class MusicResultSet extends EntityResultSet<Music> {
    
    public MusicResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected Music pickEntityFrom(Map<String, Object> row) {
        Music music = new Music();

        music.musicId = (Integer) row.get("music_id");
        Integer artistId = (Integer) row.get("artist_id");
        if (artistId != null) {
            ArtistDAO dao = new ArtistDAO();
            try {
                music.artist = dao.selectById(artistId);
            } catch (IOException ex) {
                Logger.getLogger(MusicResultSet.class.getName()).log(Level.SEVERE, null, ex);
                music.artist = null;
            }
        } else {
            music.artist = null;
        }
        music.youtubeVideoId = (String)row.get("youtube_video_id");
        music.viewCount = (Integer)row.get("view_count");
        music.title = (String)row.get("title");
        music.description = (String)row.get("description");
        music.createdAt = (Timestamp)row.get("created_at");
        music.updatedAt = (Timestamp)row.get("updated_at");
        Integer isDeleted = (Integer)row.get("is_deleted");
        music.isDeleted = isDeleted != null ? isDeleted != 0 : null;

        return music;
    }
}
