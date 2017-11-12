package cn.edu.zju.service.impl;

import cn.edu.zju.bean.Orders;
import cn.edu.zju.bean.Position;
import cn.edu.zju.bean.User;
import cn.edu.zju.dao.OrdersRepository;
import cn.edu.zju.dao.PositionRepository;
import cn.edu.zju.dao.UserRepository;
import cn.edu.zju.service.ITradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Anthony on.
 *
 */
@Service
@Transactional
public class TradeServiceImpl implements ITradeService{

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    public void matching(String stockcode) {

        List<Orders> buying = ordersRepository.findBuying(stockcode, "未完成");

        if(buying.size() == 0) return;

        List<Orders> selling = ordersRepository.findSelling(stockcode, "未完成");

        if(selling.size() == 0) return;

        for(int i = 0; i < (buying.size() > 5 ? 5 : buying.size()); ++i) {

            Orders buy = buying.get(i);
            User ub = userRepository.findByName(buy.getBelong()).get(0);
            List<Position> list = positionRepository.findByBelongAndStockcode(buy.getBelong(), stockcode);
            Position pb;
            //若买方之前未持有该股票，则新增position
            if(list.size() == 0) {
                List<Position> all = positionRepository.findAll();
                long id = all.get(all.size()-1).getId()+1;
                pb = new Position();
                pb.setId(id);
                pb.setBelong(ub.getName());
                pb.setStockcode(stockcode);
                pb.setStockname("");
                pb.setAvailable("0");
                pb.setTotal("0");
            } else {
                pb = list.get(0);
            }

            //买五卖五撮合
            for(int j = 0; j < (selling.size() > 5 ? 5 : selling.size()); ++j) {

                //买方订单已经交易完则退出
                if("0".equals(buy.getQuantity())) break;

                Orders sell = selling.get(j);
                if("0".equals(sell.getQuantity())) continue;
                //卖方用户信息
                User us = userRepository.findByName(sell.getBelong()).get(0);
                //卖方持仓信息
                List<Position> ll = positionRepository.findByBelongAndStockcode(sell.getBelong(), stockcode);
                Position ps = ll.get(0);

                //买的价格大于等于卖的价格
                if(Double.valueOf(buy.getPrice()) >= Double.valueOf(sell.getPrice())) {

                    //买进量
                    int qb = Integer.valueOf(buy.getQuantity());
                    //卖出量
                    int qs = Integer.valueOf(sell.getQuantity());
                    //买方总资金
                    double buyerMoney = Double.valueOf(ub.getMoney());
                    //卖方总资金
                    double sellerMoney = Double.valueOf(us.getMoney());
                    //买方出价
                    double buyPrice =  Double.valueOf(buy.getPrice());
                    //卖方售价
                    double sellPrice =  Double.valueOf(sell.getPrice());

                    //交易量
                    int volume;
                    if(qb >= qs) {
                        volume = qs;
                        if(qb == qs) buy.setState("交易成功");
                        sell.setState("交易成功");
                    } else {
                        volume = qb;
                        buy.setState("交易成功");
                    }

                    //交易成功价钱
                    double diff = volume*(buyPrice+sellPrice)/2;

                    //修改订单剩余量
                    buy.setQuantity(String.valueOf(qb-volume));
                    sell.setQuantity(String.valueOf(qs-volume));
                    //设置买卖双方的资金
                    ub.setMoney(String.valueOf(buyerMoney-diff));
                    us.setMoney(String.valueOf(sellerMoney+diff));
                    //设置买卖双方的持仓情况
                    pb.setTotal(String.valueOf(Integer.valueOf(pb.getTotal())+volume));
                    ps.setTotal(String.valueOf(Integer.valueOf(ps.getTotal())-volume));
                    ps.setAvailable(String.valueOf(Integer.valueOf(ps.getAvailable())-volume));
                    pb.setStockname(ps.getStockname());

                    //修改交易价格
                    //买方之前订单交易完成的股票数量
                    int numberb = Integer.valueOf(buy.getFinish());
                    //买方之前交易总金额
                    double preb = Double.valueOf(buy.getAverageprice())*(numberb*1.0);
                    //买方现在的交易价格
                    double nowb = (preb+diff)/(numberb*1.0+volume*1.0);
                    buy.setFinish(String.valueOf(numberb+volume));
                    buy.setAverageprice(String.valueOf(nowb));
                    //卖方
                    int numbers = Integer.valueOf(sell.getFinish());
                    double pres = Double.valueOf(sell.getAverageprice())*(numbers*1.0);
                    double nows = (pres+diff)/(numbers*1.0+volume*1.0);
                    sell.setFinish(String.valueOf(numbers+volume));
                    sell.setAverageprice(String.valueOf(nows));


                    userRepository.saveAndFlush(ub);
                    userRepository.saveAndFlush(us);
                    ordersRepository.saveAndFlush(buy);
                    ordersRepository.saveAndFlush(sell);
                    positionRepository.saveAndFlush(ps);
                    positionRepository.saveAndFlush(pb);


                }

            }

        }
    }
}
