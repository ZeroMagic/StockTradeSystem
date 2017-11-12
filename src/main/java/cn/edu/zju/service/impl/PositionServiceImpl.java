package cn.edu.zju.service.impl;

import cn.edu.zju.bean.Position;
import cn.edu.zju.dao.PositionRepository;
import cn.edu.zju.service.IPositionService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Anthony on 2017/10/29.
 *
 */
@Service
@Transactional
public class PositionServiceImpl implements IPositionService {


    @Autowired
    private PositionRepository positionRepository;

    @Override
    public List<Position> query(String belong) {
        return positionRepository.findByBelong(belong);
    }

    @Override
    public Position add(String body) {
        Position position = new Position();

        JSONObject jsonObject = JSON.parseObject(body);
        position.setStockname(jsonObject.getString("stockname"));
        position.setStockcode(jsonObject.getString("stockcode"));
        position.setTotal(jsonObject.getString("total"));
        position.setAvailable(jsonObject.getString("available"));
        position.setBelong(jsonObject.getString("belong"));

        return positionRepository.save(position);
    }

    @Override
    public void delete(String stockcode, String belong) {
        positionRepository.delete(stockcode, belong);
    }

    @Override
    public Position update(String body) {
        Position position = new Position();

        JSONObject jsonObject = JSON.parseObject(body);
        position.setId(jsonObject.getLong("id"));
        position.setStockname(jsonObject.getString("stockname"));
        position.setStockcode(jsonObject.getString("stockcode"));
        position.setTotal(jsonObject.getString("total"));
        position.setAvailable(jsonObject.getString("available"));
        position.setBelong(jsonObject.getString("belong"));

        return positionRepository.saveAndFlush(position);
    }
}
