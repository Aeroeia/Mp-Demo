package com.itheima.mp.dto;

import lombok.Data;

@Data
public class UserPageQueryDTO extends pageQueryDTO {
    private String name;
    private Integer status;
    private Integer minBalance;
    private Integer maxBalance;
}
