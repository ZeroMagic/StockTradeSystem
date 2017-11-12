package cn.edu.zju.util;

import java.util.UUID;

/**
 * Created by Anthony on.
 */
public class TokenUtil {
    public static String getToken(){
        return UUID.randomUUID ().toString ().replace ("-", "");
    }
}
