package com.itheima.mp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.Service.Impl.IuserServiceImpl;
import com.itheima.mp.Service.IuserService;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Wrapper;
import java.util.List;
@SpringBootTest
@Slf4j
class MpDemoApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    IuserService iuserService;
    private BaseMapper baseMapper;

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(1L);
        System.out.println(userMapper.selectById(1L));
    }
    //wrapper自定义Sql
    @Test
    void wrapperTest(){
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("username","phone")
                .like("username","o");
        List<User> users = userMapper.selectList(wrapper);
        for(User user : users){
            System.out.println(user);
        }


    }
    @Test
    void updateWrapper(){
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getUsername,"Jack")
                .set(User::getPassword,"123456");

        User user = new User();
        user.setPhone("13234123521");
        userMapper.update(user,wrapper);
    }

    @Test
    void customSql(){
        List<Long> ids = List.of(2L,3L);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .in(User::getId,ids);
        int amount = 100;
        userMapper.customUpdate(wrapper,amount);
    }
    @Test
    void iServiceTest(){
        List<User> users = iuserService.queryUsers("o", 1, 200000,400 );
        for(User user : users){
            System.out.println(user);
        }
    }

    /**
     * 乐观锁
     */
    @Test
    void withLock(){
        Long id = 1L;
        Integer money = 100;
        iuserService.deduceBalance(id,money);
    }

    @Test
    //查询用户信息 结合了hutool包和Db静态工具
    void DBtool(){
        long id = 1;
        UserVO userVO = iuserService.queryUserAndAddressById(id);
        log.info("UserVo:{}",userVO);
    }

    @Test
    //根据id批量查询返回VO
    void queryByids(){
        List<Long> ids = List.of(1L,2L,3L,4L);
        List<UserVO> list = iuserService.getUserVOByIds(ids);
        for (UserVO userVO:list){
            System.out.println(userVO);
        }
    }
    @Test
    //逻辑删除
    void logicDelete(){
        long id = 1;
        iuserService.removeById(id);
    }

    @Test
    //枚举类型
    void enumTest(){
        User user = iuserService.getById(2L);
        System.out.println(user);
        System.out.println(UserStatus.Normal.getValue());
    }
    @Test
    void test(){
        long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUsername("张三");
        user.setPassword("12131");
        iuserService.updateById(user);
    }
    @Test
    //分页查询
    void pageQuery(){
        int pageIndex = 1;
        int pageSize = 2;
        Page<User> page = new Page<>(pageIndex,pageSize);
        //排序条件
        page.addOrder(OrderItem.asc("balance"));
        //分页查询
        Page<User> pages = iuserService.page(page);
        long recordTotal = pages.getTotal();
        long pageTotal = page.getPages();
        System.out.println(recordTotal);
        System.out.println(pageTotal);
        List<User> records = pages.getRecords();
        records.forEach(System.out::println);
    }



}
