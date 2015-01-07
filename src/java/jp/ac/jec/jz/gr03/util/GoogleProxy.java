
package jp.ac.jec.jz.gr03.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yada
 */
public class GoogleProxy {
    // gr03.jz.jec.ac.jp 用
    public final static String CLIENT_ID = "559025456855-gajv2tv65v98n6mbnb9ucv67sha9srni.apps.googleusercontent.com";
    public final static String CLIENT_SECRET = "sfdCLPDuhIupeTrPOx4SYU12";
//    // localhost 用
//    public final static String CLIENT_ID = "166700389458-3uhb2ncoloh1dstm2fact283csovd1us.apps.googleusercontent.com";
//    public final static String CLIENT_SECRET = "bPmqDaslssGTLVvPByJDQLxj";
    private final HttpClient http;
    
    public GoogleProxy() {
        this.http = new HttpClient();
    }
    
    /**
     * 戻り値の内容: https://developers.google.com/accounts/docs/OAuth2WebServer#offline
     * @param authorizationCode
     * @return json
     * @throws java.io.IOException
     */
    public String retrieveTokens(String authorizationCode)
            throws IOException {
        // API参考：https://developers.google.com/+/web/signin/server-side-flow
        // https://developers.google.com/accounts/docs/OAuth2WebServer#offline
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri" ,"postmessage");
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("code", authorizationCode);
        
        try {
            String url = "https://accounts.google.com/o/oauth2/token";
            
            return http.post(url, params);
        } catch (IOException e) {
            throw new IOException("authorization code が不正です", e);
        }
    }
    
    /**
     * 戻り値の内容: https://developers.google.com/accounts/docs/OAuth2UserAgent#validatetoken
     * @param accessToken
     * @return json
     * @throws IOException 
     */
    public String retrieveTokenInfo(String accessToken)
            throws IOException {
        // API参考：https://developers.google.com/+/web/signin/server-side-flow
        // https://developers.google.com/accounts/docs/OAuth2UserAgent#validatetoken
        try {
            String url = "https://www.googleapis.com/oauth2/v1/tokeninfo"
                    + "?access_token=" + accessToken;
            
            return http.get(url);
        } catch (IOException e) {
            throw new IOException("access token が不正です", e);
        }
    }
    
    /**
     * 内容はこのへん: https://developers.google.com/youtube/v3/docs/playlistItems
     * @param accessToken
     * @return json
     * @throws IOException 
     */
    public String retrieveVideosUploaded(String accessToken)
            throws IOException {
        // API Reference: https://developers.google.com/youtube/v3/docs/playlistItems
        Json channels;
        try {
            String url = "https://www.googleapis.com/youtube/v3/channels"
                    + "?part=contentDetails"
                    + "&mine=true"
                    + "&bearer_token=" + accessToken;
            channels = new Json(http.get(url));
        } catch (IOException e) {
            throw new IOException("access token が不正です", e);
        }
        
        // キー"uploads"はネストされていて複数含まれるかもしれない（未調査）ので特定の状況でバグるかも
        String uploadsListId = channels.get("uploads");
        try {
            String url = "https://www.googleapis.com/youtube/v3/playlistItems"
                    + "?part=snippet"
                    + "&maxResults=20"
                    + "&playlistId=" + uploadsListId
                    + "&bearer_token=" + accessToken;   
            
            return http.get(url);
        } catch (IOException e) {
            // uploadsListId が変？
            throw e;
        }
    }
}
