package org.xij.web.module.sys.dic;

import org.xij.annotation.MybatisMapper;
import org.xij.web.module.base.Mapper;
import org.xij.web.module.base.TreeMapper;

/**
 * 字典Mapper
 *
 * @author XiongKai
 * @version 0.0.1
 * @since 2016年12月16日 下午2:24:13
 */
@MybatisMapper
public interface DicMapper extends Mapper<Dic>, TreeMapper<Dic> {
}
