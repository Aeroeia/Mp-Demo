package com.itheima.mp.vo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageQueryVO<T> {
    private Long total;
    private Long pages;
    private List<T> list;

    public static <PO,VO> PageQueryVO<VO> of(Page<PO> page, Function<PO,VO> convertor){
        PageQueryVO<VO> pageQueryVO = new PageQueryVO<>();
        pageQueryVO.setPages(page.getPages());
        pageQueryVO.setTotal(page.getTotal());
        List<PO> records = page.getRecords();
        if(CollUtil.isEmpty(records)){
            pageQueryVO.setList(List.of());
            return pageQueryVO;
        }
        List<VO> collect = records.stream().map(convertor).collect(Collectors.toList());
        pageQueryVO.setList(collect);
        return pageQueryVO;
    }

}
