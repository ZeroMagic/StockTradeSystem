package cn.edu.zju.service;

import cn.edu.zju.bean.Orders;

import java.util.List;

/**
 * Created by Anthony on 2017/10/30.
 *
 */
public interface IOrdersService {

    List<Orders> query(String belong);

    Orders add(String price, String quantity, String belong, String stockcode, String type);

    void delete(long id);

    Orders update(String body);

}
