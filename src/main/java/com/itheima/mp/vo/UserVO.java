package com.itheima.mp.vo;

import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.UserInfo;
import com.itheima.mp.enums.UserStatus;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {

    private Long id;

    private String username;

    private UserInfo info;

    private UserStatus status;

    private Integer balance;

    private List<AddressVO> addresses;

}
