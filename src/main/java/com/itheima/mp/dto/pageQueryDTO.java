package com.itheima.mp.dto;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class pageQueryDTO {
    private Long pageIndex = 1L;
    private Long pageSize = 5L;
    //根据什么字段进行排序
    private String sortBy;
    //是否升序
    private boolean isAsc = true;
    public <T>Page<T> toMpPage(OrderItem... orderItems){

        Page<T> page = new Page<T>(pageIndex,pageSize);
        if(StrUtil.isNotEmpty(sortBy)){
            if(isAsc){
                page.addOrder(OrderItem.asc(sortBy));
            }
            else{
                page.addOrder(OrderItem.desc(sortBy));
            }
            return page;
        }
        page.addOrder(orderItems);
        return page;
    }
    public <T>Page<T> toMpPageDefaultSortByCreateTime(){
        return toMpPage(OrderItem.desc("create_time"));
    }
    public <T>Page<T> toMpPageDefaultSortByUpdateTime(){
        return toMpPage(OrderItem.desc("update_time"));
    }
}
