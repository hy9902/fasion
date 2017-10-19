package com.hydt.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.hydt.app.vo.User;

import java.util.List;

/**
 * Created by bean_huang on 2017/10/16.
 */
public interface IUserService extends IService<User> {

    boolean deleteAll();

    List<User> selectListBySQL();
}
