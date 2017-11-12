package cn.edu.zju.controller;

import cn.edu.zju.service.ITradeService;
import cn.edu.zju.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Anthony on 2017/11/5.
 *
 */
@RestController
@RequestMapping("/stock/trade")
public class TradeController {

    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private ITradeService tradeService;

    @ApiOperation(value = "撮合接口", notes = "撮合订单", httpMethod = "GET")
    @RequestMapping(value = "/match", method = RequestMethod.GET)
    public ResponseUtil match(@RequestParam String stockcode) {
        tradeService.matching(stockcode);
        return ResponseUtil.returnSuccessWithDataMsg(null, "正在撮合...");
    }

}
