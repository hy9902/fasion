package com.hydt.app.mapper;

import com.hydt.app.SuperMapper;
import com.hydt.app.vo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by bean_huang on 2017/10/16.
 */
public interface UserMapper extends SuperMapper<User> {

    /**
     * 自定义注入方法
     */
    int deleteAll();

    @Select("select test_id as id, name, age, test_type from user")
    List<User> selectListBySQL();
}
