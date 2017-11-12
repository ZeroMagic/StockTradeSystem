package cn.edu.zju.controller;

import cn.edu.zju.bean.User;
import cn.edu.zju.service.IUserService;
import cn.edu.zju.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Anthony on 2017/10/20.
 *
 */
@RestController
@RequestMapping("/stock/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登陆接口", notes = "登陆接口", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseUtil login(@RequestParam String name, @RequestParam String password, @RequestParam String token) {
        Map<String, String> data = userService.login(name, password, token);
        if(data.size() > 0) {
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnSuccessWithData("信息错误");
    }

    @ApiOperation(value = "注册用户信息接口", notes = "注册用户基本信息", httpMethod = "POST")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseUtil register(@RequestBody String body) {
        User data = null;
        try {
            data = userService.register(body);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage(), e);
        }
        if(data != null) {
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnErrorWithMsg("注册失败，用户名已存在");
    }

    @ApiOperation(value = "查询用户信息接口", notes = "查询用户信息接口", httpMethod = "GET")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseUtil query(@RequestParam String name) {
        User data = userService.query(name);
        if(data != null) {
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnSuccessWithData("该用户不存在");
    }


}
