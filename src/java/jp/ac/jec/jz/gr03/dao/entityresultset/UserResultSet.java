package jp.ac.jec.jz.gr03.dao.entityresultset;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import jp.ac.jec.jz.gr03.entity.User;

/**
 *
 * @author yada
 */
public class UserResultSet extends EntityResultSet<User> {
    
    public UserResultSet(ResultSet rs) {
        super(rs);
    }
    
    @Override
    protected User pickEntityFrom(Map<String, Object> row) {
        User user = new User();

        // キャストで例外発生しそう
        // 型参考: http://dounanda.s140.xrea.com/mapping.html
        // mysql の datetime 型は java.sql.Timestamp（extends java.util.Date）になるよ
        user.userId         = (Integer)row.get("user_id");
        user.email          = (String)row.get("email");
        user.passwordHash   = (String)row.get("password_hash");
        user.googleUID          = (String)row.get("google_uid");
        user.googleToken        = (String)row.get("google_token");
        user.googleExpiresAt    = (Timestamp)row.get("google_expires_at");
        user.googleRefreshToken = (String)row.get("google_refresh_token");
        user.name           = (String)row.get("name");
        user.introduction   = (String)row.get("introduction");
        user.iconImageFile  = (String)row.get("icon_image_file");
        Integer isValid = (Integer)row.get("is_valid");
        user.isValid = isValid != null ? isValid != 0 : null;
        Integer isOwner = (Integer)row.get("is_owner");
        user.isOwner = isOwner != null ? isOwner != 0 : null;
        user.createdAt  = (Timestamp)row.get("created_at");
        user.updatedAt  = (Timestamp)row.get("updated_at");

        return user;
    }
}
