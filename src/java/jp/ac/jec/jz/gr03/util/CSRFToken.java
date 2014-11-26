package jp.ac.jec.jz.gr03.util;

import javax.servlet.http.HttpSession;

/**
 *
 * @author yada
 */
public class CSRFToken {
    
    private final String token;
    
    public CSRFToken(HttpSession session) {
        this.token = generateToken(session);
    }
    public boolean equals(String token) {
        return this.token.equals(token);
    }
    
    @Override
    public String toString() {
        return token;
    }
    
    /**
     * トークン文字列を生成します。同じセッションからは毎回同じ値が返ります
     * @return 
     */
    private String generateToken(HttpSession session) {
        Hash md5 = new Hash(Hash.Algorithm.MD5);
        
        return md5.get(session.getId());
    }
}
