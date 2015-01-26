
package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.MyListDAO;
import jp.ac.jec.jz.gr03.entity.MyList_Details;

/**
 *
 * @author 12jz0121
 */


public class MyListDetailResultSet extends EntityResultSet<MyList_Details> {
    
    public MyListDetailResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected MyList_Details pickEntityFrom(Map<String, Object> row) {
        MyList_Details mylistDetail = new MyList_Details();

        mylistDetail.mylist_detail_id = (Integer)row.get("mylist_detail_id");
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
        mylistDetail.mylist_detail_id = (Integer)row.get("mylist_detail_id");
        mylistDetail.music_id = (Integer)row.get("music_id");
        mylistDetail.artist_id = (Integer)row.get("artist_id");
        mylistDetail.created_at = (Timestamp)row.get("created_at");
        mylistDetail.updated_at = (Timestamp)row.get("updated_at");
        
        return mylistDetail;
    }
}
