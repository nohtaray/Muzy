 //Error reading included file Templates/Classes/Templates/Licenses/license-default_1.txt
package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.Mylist;

/**
 *
 * @author 12jz0121
 */
public class MyListResultSet extends EntityResultSet<Mylist> {
    
    public MyListResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected Mylist pickEntityFrom(Map<String, Object> row) {
        Mylist mylist = new Mylist();

        mylist.mylistId = (Integer)row.get("mylist_id");
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
        mylist.mylistId = (Integer)row.get("mylist_id");
        mylist.createdAt = (Timestamp)row.get("created_at");
        mylist.updatedAt = (Timestamp)row.get("updated_at");
        mylist.name = (String)row.get("name");
        
        return mylist;
    }
}
