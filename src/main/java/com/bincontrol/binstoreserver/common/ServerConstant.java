package com.bincontrol.binstoreserver.common;

import java.util.ArrayList;

public class ServerConstant {

    // 淘宝客服务端SDK参数
    public static String ALI_SERVER_URL = "http://gw.api.taobao.com/router/rest";
    public static String ALI_SERVER_APP_KEY = "24857356";
    public static String ALI_SERVER_APP_SECRET = "e0ed2d462025b1fd7b28a57dd339b486";
    public static long ALI_SERVER_ADZONE_ID = 453770120L;
    public static String ALI_SERVER_PID = "mm_128206999_44364607_453770120";

    // 商品类别
    public static ArrayList<String> COMMODITY_CATEGORY = new ArrayList<String>() {
        {
            add("女装");
            add("母婴");
            add("数码");
        }
    };

}
