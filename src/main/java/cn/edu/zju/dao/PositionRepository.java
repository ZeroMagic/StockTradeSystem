package cn.edu.zju.dao;

import cn.edu.zju.bean.Position;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Anthony on 2017/10/29.
 *
 */
public interface PositionRepository extends Repository<Position, Long> {

    //根据所属用户查询其持仓
    List<Position> findByBelong(String belong);

    //查询某个用户某只股票的情况
    @Query(value = "SELECT * from position where belong=?1 and stockcode=?2", nativeQuery = true)
    List<Position> findByBelongAndStockcode(String belong, String stockcode);

    //增加新的持仓
    Position save(Position position);

    //根据股票代码删除某个用户的某个持仓
    @Modifying
    @Query(value = "delete from position where stockcode=?1 and belong=?2", nativeQuery = true)
    int delete(String stockcode, String belong);

    //更新持仓
    Position saveAndFlush(Position position);

    //获取所有持仓
    @Query(value = "SELECT * from position", nativeQuery = true)
    List<Position> findAll();
}
