package cn.edu.zju.util;

import java.util.HashMap;

/**
 * Created by Anthony.
 */
public class CacheUtil {

    private static CacheUtil instance;

    private HashMap<String, String> cache;

    private CacheUtil(){
        cache = new HashMap<>();
    }

    public synchronized static CacheUtil getInstance(){
        if(instance == null){
            instance = new CacheUtil();
        }
        return instance;
    }

    public HashMap<String, String> getCache(){
        return this.cache;
    }

    public void put(String username, String token){
            this.cache.put(username, token);
    }

    public String get(String username){
        if(this.cache.containsKey(username)){
            return this.cache.get(username);
        }
        return null;
    }

    public void remove(String username){
        this.cache.remove(username);
    }

}
