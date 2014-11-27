package jp.ac.jec.jz.gr03.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author yada
 */
public class Hash {
    
    // Hash のコンストラクタに渡せるオブジェクト。
    // Hash 内部で使ってる java.security.MessageDigest.getInstance は引数に文字列を取る。
    // 任意の文字列を渡せてしまうのは嫌なので、すべて定数化しておく
    public static final class Algorithm {
        public static final Algorithm MD2 = new Algorithm("MD2");
        public static final Algorithm MD5 = new Algorithm("MD5");
        public static final Algorithm SHA1 = new Algorithm("SHA-1");
        public static final Algorithm SHA256 = new Algorithm("SHA-256");
        public static final Algorithm SHA384 = new Algorithm("SHA-384");
        public static final Algorithm SHA512 = new Algorithm("SHA-512");
        
        private final String name;
        private Algorithm(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
    }
    
    private final MessageDigest md;
    private String salt = null;
    
    /**
     * 
     * @param algorithm Hash.Algorithm.{ALGORITHM_NAME} 定数が使えます
     * @exception IllegalArgumentException 
     */
    public Hash(Algorithm algorithm) {
        MessageDigest _md = null;
        try {
            _md = MessageDigest.getInstance(algorithm.getName());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        this.md = _md;
    }
    /**
     * 
     * @param algorithm Hash.Algorithm.{ALGORITHM_NAME} 定数が使えます
     * @param salt 
     */
    public Hash(Algorithm algorithm, String salt) {
        this(algorithm);
        this.salt = salt;
    }
    /**
     * 与えられた文字列からそのハッシュを取得します
     * @param key
     * @return 
     */
    public String get(String key) {
        // java.security.MessageDigest仕様: http://www.media.osaka-cu.ac.jp/~onisi/Java/docs/ja/api/java.security.MessageDigest.html
        
        // ハッシュ取得
        md.reset();
        if (salt != null) {
            md.update(salt.getBytes()); // ソルト設定
        }
        md.update(key.getBytes());
        byte[] hash = md.digest();
        
        // 16進文字列へ変換
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
    }
}
