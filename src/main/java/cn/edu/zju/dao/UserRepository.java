package cn.edu.zju.dao;

import cn.edu.zju.bean.User;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Anthony on 2017/10/20.
 *
 */
public interface UserRepository extends Repository<User, Long> {

    List<User> findByNameAndPassword(String name, String password);

    List<User> findByName(String name);

    User save(User user);

    User saveAndFlush(User user);

    //@Query(value = "select * from #{#entityName} u where u.name=?1", nativeQuery = true)

}
