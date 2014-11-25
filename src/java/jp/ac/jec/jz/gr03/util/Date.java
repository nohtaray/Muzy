package jp.ac.jec.jz.gr03.util;

/**
 * java.sql.Date のラッパー
 * @author yada
 */
public class Date extends java.sql.Date {
    // NOTE: java.sql.Date extends java.util.Date
    
    public Date() {
        // 今を生成するよ
        this(new java.util.Date());
    }
    public Date(java.util.Date date) {
        this(date.getTime());
    }
    public Date(long date) {
        super(date);
    }
    
    public static Date now() {
        return new Date();
    }
}
