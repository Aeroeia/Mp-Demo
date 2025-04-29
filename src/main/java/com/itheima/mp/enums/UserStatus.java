package com.itheima.mp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {
    Normal(1,"正常"),
    Frozen(0,"冻结");

    //注解告知mybatis
    @EnumValue
    //使得返回前端的数据类型为int
    @JsonValue
    private final int value;
    private final String desc;

    UserStatus(int val,String desc){
        this.value = val;
        this.desc = desc;
    }
}
