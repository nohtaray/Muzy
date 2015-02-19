
package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.MyListDAO;
import jp.ac.jec.jz.gr03.entity.MylistDetail;


public class MyListDetailResultSet extends EntityResultSet<MylistDetail> {
    
    public MyListDetailResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected MylistDetail pickEntityFrom(Map<String, Object> row) {
        MylistDetail mylistDetail = new MylistDetail();

        mylistDetail.mylistDetailId = (Integer)row.get("mylist_detail_id");
        Integer mylistId = (Integer)row.get("mylist_id");
        if (mylistId != null) {
            MyListDAO dao = new MyListDAO();
            try {
                mylistDetail.mylist = dao.selectById(mylistId);
            } catch (IOException ex) {
                Logger.getLogger(MyListResultSet.class.getName()).log(Level.SEVERE, null, ex);
                mylistDetail.mylist = null;
            }
        } else {
            mylistDetail.mylist = null;
        }
        mylistDetail.mylistDetailId = (Integer)row.get("mylist_detail_id");
        mylistDetail.musicId = (Integer)row.get("music_id");
        mylistDetail.artistId = (Integer)row.get("artist_id");
        mylistDetail.createdAt = (Timestamp)row.get("created_at");
        mylistDetail.updatedAt = (Timestamp)row.get("updated_at");
        
        return mylistDetail;
    }
}
