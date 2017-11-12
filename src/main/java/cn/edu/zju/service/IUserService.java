package cn.edu.zju.service;

import cn.edu.zju.bean.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Anthony on 2017/10/20.
 *
 */
public interface IUserService {

    Map<String, String> login(String name, String password, String token);

    User register(String body);

    User query(String name);
}
