
package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.MylistDAO;
import jp.ac.jec.jz.gr03.entity.MylistDetail;


public class MylistDetailResultSet extends EntityResultSet<MylistDetail> {
    
    public MylistDetailResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected MylistDetail pickEntityFrom(Map<String, Object> row) {
        MylistDetail mylistDetail = new MylistDetail();

        mylistDetail.mylistDetailId = (Integer)row.get("mylist_detail_id");
        Integer mylistId = (Integer)row.get("mylist_id");
        if (mylistId != null) {
            MylistDAO dao = new MylistDAO();
            try {
                mylistDetail.mylist = dao.selectById(mylistId);
            } catch (IOException ex) {
                Logger.getLogger(MylistResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Integer musicId = (Integer)row.get("music_id");
        if (musicId != null) {
            MusicDAO dao = new MusicDAO();
            try {
                mylistDetail.music = dao.selectById(musicId);
            } catch (IOException ex) {
                Logger.getLogger(MylistDetailResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Integer artistId = (Integer)row.get("artist_id");
        if (artistId != null) {
            ArtistDAO dao = new ArtistDAO();
            try {
                mylistDetail.artist = dao.selectById(artistId);
            } catch (IOException ex) {
                Logger.getLogger(MylistDetailResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        mylistDetail.createdAt = (Timestamp)row.get("created_at");
        mylistDetail.updatedAt = (Timestamp)row.get("updated_at");
        
        return mylistDetail;
    }
}
