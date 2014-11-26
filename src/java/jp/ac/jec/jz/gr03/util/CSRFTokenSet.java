package jp.ac.jec.jz.gr03.util;

import javax.servlet.http.HttpSession;

/**
 *
 * @author yada
 */
public class CSRFTokenSet {
    
    private CSRFTokenSet() {}
    
    public static String get(HttpSession session) {
        Hash md5 = new Hash(Hash.Algorithm.MD5);
        
        return md5.get(session.getId());
    }
}
