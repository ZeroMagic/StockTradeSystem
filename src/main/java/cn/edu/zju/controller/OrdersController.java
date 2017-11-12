package cn.edu.zju.controller;

import cn.edu.zju.bean.Orders;
import cn.edu.zju.service.IOrdersService;
import cn.edu.zju.service.ITradeService;
import cn.edu.zju.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Anthony on 2017/10/30.
 *
 */
@RestController
@RequestMapping("/stock/order")
public class OrdersController {

    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private IOrdersService ordersService;

    @Autowired
    private ITradeService tradeService;

    @ApiOperation(value = "查询用户订单接口", notes = "查询用户订单", httpMethod = "GET")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseUtil queryOrder(@RequestParam String belong) {
        List<Orders> data = ordersService.query(belong);
        return ResponseUtil.returnSuccessWithData(data);
    }

    @ApiOperation(value = "新增订单接口", notes = "新增订单", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseUtil addOrder(@RequestParam String price, @RequestParam String quantity, @RequestParam String belong, @RequestParam String stockcode, @RequestParam String type) {
        Orders data = null;
        try {
            data = ordersService.add(price, quantity, belong, stockcode, type);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage(), e);
        }
        if(data != null) {
            tradeService.matching(stockcode);
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnErrorWithMsg("新增失败，信息错误");
    }

    @ApiOperation(value = "删除订单接口", notes = "删除订单", httpMethod = "GET")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseUtil deleteOrder(@RequestParam long id) {
        boolean data = false;
        try {
            ordersService.delete(id);
            data = true;
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage(), e);
        }
        if(data) {
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnErrorWithMsg("删除失败，信息错误");
    }

    @ApiOperation(value = "修改订单接口", notes = "修改订单", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseUtil updateOrder(@RequestBody String body) {
        Orders data = null;
        try {
            data = ordersService.update(body);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage(), e);
        }
        if(data != null) {
            return ResponseUtil.returnSuccessWithData(data);
        }
        return ResponseUtil.returnErrorWithMsg("更新失败，信息错误");
    }


}
