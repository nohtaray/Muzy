package jp.ac.jec.jz.gr03.entity;

import java.util.Date;
import jp.ac.jec.jz.gr03.util.Hash;
import jp.ac.jec.jz.gr03.util.Hash.Algorithm;

/**
 *
 * @author yada
 */
public class User {
    public Integer userId = null;
    public String email = null;
    public String passwordHash = null;
    public String googleUID = null;
    public String googleToken = null;
    public Date googleExpiresAt = null;
    public String googleRefreshToken = null;
    public String name = null;
    public String introduction = null;
    public String iconImageFile = null;
    public Date createdAt = null;
    public Date updatedAt = null;
    public Boolean isValid = null;
    public Boolean isOwner = null;
    
    // 設定ファイルに分けるべき
    private final String SALT = "jzmuzy98765";
    private final int STRETCHING_ROUNDS = 1;
    private final Algorithm HASH_ALGORITHM = Algorithm.SHA512;
    
    
    public void setPassword(String rawPassword) {
        passwordHash = hash(rawPassword);
    }
    public boolean passwordEquals(String rawPassword) {
        return passwordHash.equals(hash(rawPassword));
    }
    
    /**
     * ハッシュ化
     * @param key
     * @return ハッシュ
     */
    private String hash(String key) {
        Hash hash = new Hash(HASH_ALGORITHM, SALT);
        
        String result = key;
        // ハッシュ化・ストレッチング
        for (int i = 0; i < STRETCHING_ROUNDS; i++) {
            result = hash.get(result);
        }
        
        return result;
    }
}
