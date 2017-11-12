package cn.edu.zju.service.impl;

import cn.edu.zju.bean.Orders;
import cn.edu.zju.bean.Position;
import cn.edu.zju.bean.User;
import cn.edu.zju.dao.OrdersRepository;
import cn.edu.zju.dao.PositionRepository;
import cn.edu.zju.dao.UserRepository;
import cn.edu.zju.service.IOrdersService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Anthony on 2017/10/30.
 *
 */
@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public List<Orders> query(String belong) {
        return ordersRepository.findByBelong(belong);
    }

    @Override
    public Orders add(String price, String quantity, String belong, String stockcode, String type) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Orders orders = new Orders();

        orders.setPrice(price);
        orders.setQuantity(quantity);
        orders.setState("未完成");
        orders.setTime(timestamp.substring(0, timestamp.length()-3));
        orders.setBelong(belong);
        orders.setStockcode(stockcode);
        orders.setType(type);
        orders.setTotal(quantity);
        orders.setFinish("0");
        orders.setAverageprice("0.0");

        String name = belong;

        if("buy".equals(type)) {
            List<User> list = userRepository.findByName(name);
            if(list.size() == 0) {
                return null;
            }
            User user = list.get(0);
            double pay = Double.valueOf(price)*Integer.valueOf(quantity);
            double money = Double.valueOf(user.getMoney());
            if(pay > money) return null;
        } else {
            List<Position> list = positionRepository.findByBelongAndStockcode(name, stockcode);
            if(list.size() == 0) {
                return null;
            }
            Position position = list.get(0);
            int have = Integer.valueOf(position.getAvailable());
            int sell = Integer.valueOf(quantity);
            if(sell > have) return null;
        }

        return ordersRepository.save(orders);
    }

    @Override
    public void delete(long id) {
        ordersRepository.delete(id);
    }

    @Override
    public Orders update(String body) {
        Orders orders = new Orders();

        JSONObject jsonObject = JSON.parseObject(body);
        orders.setId(Long.valueOf(jsonObject.getString("id")));
        orders.setPrice(jsonObject.getString("price"));
        orders.setQuantity(jsonObject.getString("quantity"));
        orders.setState(jsonObject.getString("state"));
        orders.setTime(jsonObject.getString("time"));
        orders.setBelong(jsonObject.getString("belong"));
        orders.setStockcode(jsonObject.getString("stockcode"));
        orders.setType(jsonObject.getString("type"));
        orders.setTotal(jsonObject.getString("total"));
        orders.setFinish(jsonObject.getString("finish"));
        orders.setAverageprice(jsonObject.getString("averageprice"));

        return ordersRepository.saveAndFlush(orders);
    }
}
