package com.itheima.mp.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.dto.UserPageQueryDTO;
import com.itheima.mp.vo.PageQueryVO;
import com.itheima.mp.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface IuserService extends IService<User> {
    List<User> queryUsers(String name,Integer status,Integer maxBalance,Integer minBalance);

    void deduceBalance(Long id, Integer money);

    UserVO queryUserAndAddressById(long id);

    List<UserVO> getUserVOByIds(List<Long> ids);

    PageQueryVO<UserVO> UsersPageQuery(UserPageQueryDTO queryDTO);
}
