package cn.edu.zju.dao;

import cn.edu.zju.bean.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Anthony on 2017/10/30.
 *
 */
public interface OrdersRepository extends Repository<Orders, Long> {

    //查询某一个用户的所有订单
    List<Orders> findByBelong(String belong);

    //查询某只股票的买五
    @Query(value = "SELECT * from orders p where p.stockcode=?1 and p.state=?2 and p.type=\"buy\" order by price DESC", nativeQuery = true)
    List<Orders> findBuying(String stockcode, String state);

    //查询某只股票的卖五
    @Query(value = "SELECT * from orders p where p.stockcode=?1 and p.state=?2 and p.type=\"sell\" order by price", nativeQuery = true)
    List<Orders> findSelling(String stockcode, String state);

    //新增订单
    Orders save(Orders orders);

    //删除某个未完成的订单
    void delete(long id);

    //修改订单
    Orders saveAndFlush(Orders orders);
}
