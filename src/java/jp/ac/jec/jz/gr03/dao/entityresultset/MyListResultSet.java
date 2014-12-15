 //Error reading included file Templates/Classes/Templates/Licenses/license-default_1.txt
package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.MyList;

/**
 *
 * @author 12jz0121
 */
public class MyListResultSet extends EntityResultSet<MyList> {
    
    public MyListResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected MyList pickEntityFrom(Map<String, Object> row) {
        MyList mylist = new MyList();

        mylist.mylist_id = (Integer)row.get("mylist_id");
        Integer userId = (Integer)row.get("user_id");
        if (userId != null) {
            UserDAO dao = new UserDAO();
            try {
                mylist.user = dao.selectById(userId);
            } catch (IOException ex) {
                Logger.getLogger(MyListResultSet.class.getName()).log(Level.SEVERE, null, ex);
                mylist.user = null;
            }
        } else {
            mylist.user = null;
        }
        mylist.mylist_id = (Integer)row.get("mylist_id");
        mylist.created_at = (Timestamp)row.get("created_at");
        mylist.updated_at = (Timestamp)row.get("updated_at");
        mylist.name = (String)row.get("name");
        
        return mylist;
    }
}
