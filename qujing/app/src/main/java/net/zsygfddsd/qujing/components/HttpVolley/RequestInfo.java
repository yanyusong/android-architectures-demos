package net.zsygfddsd.qujing.components.HttpVolley;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 16/3/3.
 */
public class RequestInfo implements Serializable{

    private String url;

    private Map<String, String> headers;

    private Map<String, String> bodyParams;

    public RequestInfo(String url) {
        this.url = url;
        this.headers = new HashMap<>();
        this.bodyParams = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeaders(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getBodyParams() {
        return bodyParams;
    }

    public void addBodyParams(String key, String value) {
        bodyParams.put(key, value);
    }
}
