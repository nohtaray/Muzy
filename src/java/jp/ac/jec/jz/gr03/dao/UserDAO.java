package jp.ac.jec.jz.gr03.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jp.ac.jec.jz.gr03.dao.entityresultset.UserResultSet;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Date;

/**
 *
 * @author yada
 */
public class UserDAO extends DAO {

    public User selectById(Integer userId) throws IOException {
        try {
            String sql = "select * from users where user_id = ? limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, userId, Types.INTEGER);
            
            UserResultSet results = new UserResultSet(ps.executeQuery());
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public User selectByGoogleUID(String googleUID) throws IOException {
        try {
            String sql = "select * from users where google_uid = ? limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, googleUID, Types.VARCHAR);

            UserResultSet results = new UserResultSet(ps.executeQuery());
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public User selectByEmail(String email) throws IOException {
        try {
            String sql = "select * from users where email = ? limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, email, Types.VARCHAR);

            UserResultSet results = new UserResultSet(ps.executeQuery());
            return results.readRow();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void update(User user) throws IOException {
        // うーん
        String sql = "update users set "
                + "email = ?, "
                + "password_hash = ?, "
                + "google_uid = ?, "
                + "google_token = ?, "
                + "google_expires_at = ?, "
                + "google_refresh_token = ?, "
                + "name = ?, "
                + "introduction = ?, "
                + "icon_image_file = ?, "
                + "is_valid = ?, "
                + "is_owner = ?, "
                + "updated_at = ? "
                + "where user_id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            // うーんｗ
            int idx = 1;
            ps.setObject(idx++, user.email, Types.VARCHAR);
            ps.setObject(idx++, user.passwordHash, Types.VARCHAR);
            ps.setObject(idx++, user.googleUID, Types.VARCHAR);
            ps.setObject(idx++, user.googleToken, Types.VARCHAR);
            ps.setObject(idx++, new Date(user.googleExpiresAt), Types.TIMESTAMP);
            ps.setObject(idx++, user.googleRefreshToken, Types.VARCHAR);
            ps.setObject(idx++, user.name, Types.VARCHAR);
            ps.setObject(idx++, user.introduction, Types.VARCHAR);
            ps.setObject(idx++, user.iconImageFile, Types.VARCHAR);
            ps.setObject(idx++, (user.isValid ? 1 : 0), Types.INTEGER);
            ps.setObject(idx++, (user.isOwner ? 1 : 0), Types.INTEGER);
            ps.setObject(idx++, user.userId, Types.INTEGER);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
        // 今アップデートしたよ
        user.updatedAt = Date.now();
    }

    public void insert(User user) throws IOException {
        // ? の数まちがえないでね
        String sql = "insert into users("
                + "email, "
                + "password_hash, "
                + "google_uid, "
                + "google_token, "
                + "google_expires_at, "
                + "google_refresh_token, "
                + "name, "
                + "introduction, "
                + "icon_image_file, "
                + "is_valid, "
                + "is_owner, "
                + "created_at, "
                + "updated_at) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            // 順番まちがえないでね
            ps.setObject(idx++, user.email, Types.VARCHAR);
            ps.setObject(idx++, user.passwordHash, Types.VARCHAR);
            ps.setObject(idx++, user.googleUID, Types.VARCHAR);
            ps.setObject(idx++, user.googleToken, Types.VARCHAR);
            ps.setObject(idx++, new Date(user.googleExpiresAt), Types.TIMESTAMP);
            ps.setObject(idx++, user.googleRefreshToken, Types.VARCHAR);
            ps.setObject(idx++, user.name, Types.VARCHAR);
            ps.setObject(idx++, user.introduction, Types.VARCHAR);
            ps.setObject(idx++, user.iconImageFile, Types.VARCHAR);
            ps.setObject(idx++, (user.isValid ? 1 : 0), Types.INTEGER);
            ps.setObject(idx++, (user.isOwner ? 1 : 0), Types.INTEGER);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);
            ps.setObject(idx++, Date.now(), Types.TIMESTAMP);

            ps.execute();
            
            user.userId = getGeneratedKey(ps);
            user.createdAt = Date.now();
            user.updatedAt = Date.now();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void delete(User user) throws IOException {
        // is_validをfalseにする予定だったけど
        // google_uid とかemailに確かunique制約ついてて、実装がめんどいので、
        // 今はdeleteする
        try {
            String sql = "delete from users where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int idx = 1;
            ps.setObject(idx++, user.userId, Types.INTEGER);

            ps.execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    
}
