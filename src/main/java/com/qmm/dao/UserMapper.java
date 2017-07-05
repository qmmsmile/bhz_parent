package com.qmm.dao;

import com.qmm.entity.User;
import com.qmm.common.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper extends BaseMapper<User,Integer> {

}