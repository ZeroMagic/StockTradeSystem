package cn.edu.zju.util;

import org.junit.Test;

/**
 * Created by Anthony on 2017/11/6.
 */
public class CacheUtilTest {

    @Test
    public void test(){
        String token = TokenUtil.getToken();
        CacheUtil cacheUtil = CacheUtil.getInstance();
        cacheUtil.put("liujian", token);
        while (true){
            try {
                System.out.println(cacheUtil.get("liujian"));
                Thread.sleep(5000);
            }catch (Exception e){

            }
        }
    }
}
