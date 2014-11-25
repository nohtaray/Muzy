
package jp.ac.jec.jz.gr03.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author yada
 */
public class Json {
    
    private final String json;
    
    public Json() {
        this.json = "{}";
    }
    public Json(String json) {
        this.json = json;
    }
    
    /**
     * 数値または文字列の値しか返せません
     * 該当するキーが複数ある場合、最初に現れたものしかreturnしません
     * 
     * @param key
     * @return String
     */
    public String get(String key) {
        // 雑、ちゃんとやるならライブラリ使おう
        // 対応する値の1文字目を正規表現で取得して、それが
        // " なら文字列、次の " まで取得
        // 数字なら数値、0-9だけ取得
        // それ以外は知らない (null)
        try {
            String regex;
            Matcher m;
            
            // 値の1文字目だけ取得
            regex = "\"" + key + "\"\\s*:\\s*(.)";
            m = Pattern.compile(regex).matcher(json);
            if (!m.find()) {
                // キーがない
                return null;
            }
            char first = m.group(1).charAt(0);
            if (first == '"') {
                // 文字列
                regex = "\"" + key + "\"\\s*:\\s*\"(.*?)(?<!\\\\)\"";
                m = Pattern.compile(regex).matcher(json);
                m.find();   // 必須
                return m.group(1);
            } else if (Character.isDigit(first)) {
                // 数値
                regex = "\"" + key + "\"\\s*:\\s*([0-9]+)";
                m = Pattern.compile(regex).matcher(json);
                if (m.find()) { // 全角の数字はマッチしない
                    return m.group(1);
                }
            }
            // その他 true, false, null, 配列、オブジェクト
            return null;
        } catch(Exception e) {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return this.json;
    }
}
