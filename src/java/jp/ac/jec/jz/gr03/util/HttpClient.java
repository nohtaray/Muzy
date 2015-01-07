
package jp.ac.jec.jz.gr03.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.*;

/**
 *
 * @author yada
 */
public class HttpClient {
    private final int connectTimeout = 3000;
    private final int socketTimeout = 3000;
    private static HttpHost proxy = null;
    private static boolean useProxy = false;
    
    public static void useProxy(String hostname, int port) {
        HttpClient.proxy = new HttpHost(hostname, port);
        HttpClient.useProxy = true;
    }
    
    public String get(String url)
            throws IOException {
        Request request = Request.Get(url);
        
        return execute(request);
    }
    public String post(String url, Map<String, String> params) 
            throws IOException {
        Request request = Request.Post(url).bodyForm(mapToParamList(params));
        
        return execute(request);
    }
    public String post(String url)
            throws IOException {
        Request request = Request.Post(url);
        
        return execute(request);
    }
    
    private String execute(Request request) 
            throws IOException {
        if (HttpClient.useProxy) {
            request.viaProxy(HttpClient.proxy);
        }
        return request.connectTimeout(connectTimeout).socketTimeout(socketTimeout).execute().returnContent().toString();
    }
    private List<NameValuePair> mapToParamList(Map<String, String> params) {
        Form f = Form.form();
        for (Map.Entry<String, String> e : params.entrySet()) {
            f.add(e.getKey(), e.getValue());
        }
        
        return f.build();
    }
}
