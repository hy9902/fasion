package com.hydt.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hydt.app.mapper.UserMapper;
import com.hydt.app.service.IUserService;
import com.hydt.app.vo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bean_huang on 2017/10/16.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public boolean deleteAll() {
        return retBool(baseMapper.deleteAll());
    }

    @Override
    public List<User> selectListBySQL() {
        return baseMapper.selectListBySQL();
    }
}
