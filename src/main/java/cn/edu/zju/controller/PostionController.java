package cn.edu.zju.controller;

import cn.edu.zju.bean.Position;
import cn.edu.zju.service.IPositionService;
import cn.edu.zju.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Anthony on 2017/10/29.
 *
 */
@RestController
@RequestMapping("/stock/position")
public class PostionController {

    private static final Logger logger = LoggerFactory.getLogger(PostionController.class);

    @Autowired
    private IPositionService postionService;

    @ApiOperation(value = "查询用户持仓信息接口", notes = "查询持仓", httpMethod = "GET")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseUtil queryPosition(@RequestParam String belong) {
        List<Position> data = postionService.query(belong);
        return ResponseUtil.returnSuccessWithData(data);
    }

    @ApiOperation(value = "新增持仓接口", notes = "新增持仓", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseUtil addPosition(@RequestBody String body) {
        Position data = null;
        try {
            data = postionService.add(body);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage(), e);
        }
        if(data != null) {
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnErrorWithMsg("新增失败，信息错误");
    }

    @ApiOperation(value = "某只股票全部抛出接口", notes = "某只股票全部抛出", httpMethod = "GET")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseUtil deletePosition(@RequestParam String stockcode, @RequestParam String belong) {
        boolean data = false;
        try {
            postionService.delete(stockcode, belong);
            data = true;
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage(), e);
        }
        if(data) {
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnErrorWithMsg("抛出失败，信息错误");
    }

    @ApiOperation(value = "更新持仓接口", notes = "更新持仓", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseUtil updatePosition(@RequestBody String body) {
        Position data = null;
        try {
            data = postionService.update(body);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage(), e);
        }
        if(data != null) {
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnErrorWithMsg("更新失败，信息错误");
    }

}
