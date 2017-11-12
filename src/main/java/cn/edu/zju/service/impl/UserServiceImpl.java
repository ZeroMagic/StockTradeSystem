package cn.edu.zju.service.impl;

import cn.edu.zju.bean.User;
import cn.edu.zju.dao.UserRepository;
import cn.edu.zju.service.IUserService;
import cn.edu.zju.util.CacheUtil;
import cn.edu.zju.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anthony.
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> login(String name, String password, String token)
    {
        //有token的话，先比对
       if(token != null && "".equals(token)) {
           CacheUtil cacheUtil = CacheUtil.getInstance();
           if(token.equals(cacheUtil.get(name))) {
               Map<String, String> data = new HashMap<>();
               data.put("name", name);
               data.put("token", token);
               return data;
           }
       }
       //若token已更新，则重新获取token
       List<User> userList = userRepository.findByNameAndPassword(name, password);
       Map<String, String> data = new HashMap<>();
       if (userList.size() > 0){
           String newToken = TokenUtil.getToken();
           CacheUtil cacheUtil = CacheUtil.getInstance();
           cacheUtil.put(userList.get(0).getName(), newToken);

           data.put("name", name);
           data.put("token", newToken);
       }
        return data;
    }

    public User register(String body) {
        User user = new User();

        JSONObject jsonObject = JSON.parseObject(body);
        user.setName(jsonObject.getString("name"));
        user.setPassword(jsonObject.getString("password"));
        user.setMoney(jsonObject.getString("money"));

        return userRepository.save(user);
    }

    public User query(String name) {
        List<User> userList = userRepository.findByName(name);
        if (userList.size() > 0){
            return userList.get(0);
        }
        return null;
    }
}
