package org.xij.web.module.sys.dic.item;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;

import java.util.List;

/**
 * 字典Mapper
 *
 * @author XiongKai
 * @version 0.0.1
 * @since 2016年12月16日 下午2:24:13
 */
@MybatisMapper
public interface DicItemMapper {
    List<DicItem> queryDic(@Param("type") String type, @Param("filter") String filter);
}
