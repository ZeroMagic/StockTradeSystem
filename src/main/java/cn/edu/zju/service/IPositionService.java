package cn.edu.zju.service;

import cn.edu.zju.bean.Position;

import java.util.List;

/**
 * Created by Anthony on 2017/10/29.
 *
 */
public interface IPositionService {

    List<Position> query(String belong);

    Position add(String body);

    void delete(String stockcode, String belong);

    Position update(String body);

}
