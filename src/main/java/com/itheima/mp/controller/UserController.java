package com.itheima.mp.controller;


import com.itheima.mp.Service.IuserService;
import com.itheima.mp.dto.UserPageQueryDTO;
import com.itheima.mp.vo.PageQueryVO;
import com.itheima.mp.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author aer
 * @since 2025-04-13
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IuserService iuserService;
    @GetMapping("/page")
    public PageQueryVO<UserVO> page(UserPageQueryDTO queryDTO){
        return iuserService.UsersPageQuery(queryDTO);
    }
}
