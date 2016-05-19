package net.zsygfddsd.qujing.components.HttpVolley;

import com.android.volley.VolleyError;

/**
 * Created by mac on 16/3/3.
 */
public class VolleyResponse {

    //string请求
    public interface strReqCallback{

        void success(String response);

        void error(VolleyError error);
    }

    //jsonObject请求

    //jsonArray请求

    //...
}
