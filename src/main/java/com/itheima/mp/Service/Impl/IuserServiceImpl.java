package com.itheima.mp.Service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.Service.IuserService;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.dto.UserPageQueryDTO;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.vo.AddressVO;
import com.itheima.mp.vo.PageQueryVO;
import com.itheima.mp.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IuserServiceImpl  extends ServiceImpl<UserMapper,User> implements IuserService {
    private final UserMapper userMapper;

    public IuserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 根据挑起那进行lambda查询
     * @param name
     * @param status
     * @param maxBalance
     * @param minBalance
     * @return
     */
    @Override
    public List<User> queryUsers(String name, Integer status, Integer maxBalance, Integer minBalance) {
        List<User> list = lambdaQuery()
                .like(User::getUsername, name)
                .eq(User::getStatus, status)
                .between(User::getBalance, minBalance, maxBalance).list();
        return list;
    }

    /**
     * 加乐观锁案例 用户同时间多次修改金额可能发生并发异常
     * @param id
     * @param money
     */
    @Override
    @Transactional
    public void deduceBalance(Long id, Integer money) {
        //用户查询
        User user = getById(id);
        //校验用户状态
        if(user==null||user.getStatus()== UserStatus.Frozen){
            throw new RuntimeException("用户账号异常");
        }
        if(user.getBalance()<money){
            throw new RuntimeException("用户金额不足");
        }
        int remainBalance = user.getBalance()-money;
        lambdaUpdate().set(User::getBalance,remainBalance)
                .set(remainBalance==0,User::getStatus,2)
                .eq(User::getId,id)
                .eq(User::getBalance,user.getBalance())//乐观锁
                .update();

    }

    @Override
    public UserVO queryUserAndAddressById(long id) {
        User user = getById(id);
        if(user==null){
            throw new RuntimeException("用户不存在");
        }
        //查询地址
        List<Address> list = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        if(CollUtil.isNotEmpty(list)){
            userVO.setAddresses(BeanUtil.copyToList(list,AddressVO.class));
        }
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOByIds(List<Long> ids) {
        //查询用户
        List<User> users = listByIds(ids);
        if(CollUtil.isEmpty(users)){
            return Collections.emptyList();
        }
        List<Address> addresses = Db.lambdaQuery(Address.class).in(Address::getUserId,ids).list();
        //转地址VO
        List<AddressVO> addressVOS = BeanUtil.copyToList(addresses,AddressVO.class);
        //分组
        Map<Long, List<AddressVO>> collect = addressVOS.stream().collect(Collectors.groupingBy(AddressVO::getUserId));
        List<UserVO> userVOS = new ArrayList<>();
        for(User user:users){
            UserVO userVO = BeanUtil.copyProperties(user,UserVO.class);
            userVO.setAddresses(collect.get(user.getId()));
            userVOS.add(userVO);
        }
        return userVOS;
    }

    @Override
    public PageQueryVO<UserVO> UsersPageQuery(UserPageQueryDTO queryDTO) {
        String name = queryDTO.getName();
        Integer status = queryDTO.getStatus();
        Integer minBalance = queryDTO.getMinBalance();
        Integer maxBalance = queryDTO.getMaxBalance();
        //分页条件
        Page<User> page = queryDTO.toMpPage(OrderItem.desc("create_time"));
        //条件查询
        Page<User> pages = lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .and(minBalance != null && maxBalance != null, q -> q.between(User::getBalance, minBalance, maxBalance))
                .page(page);

        //封装VO
        return PageQueryVO.of(pages, user -> BeanUtil.copyProperties(user, UserVO.class));


    }
}
