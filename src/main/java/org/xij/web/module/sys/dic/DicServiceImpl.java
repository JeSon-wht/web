package org.xij.web.module.sys.dic;

import org.springframework.stereotype.Service;
import org.xij.web.module.base.ServiceImpl;

import java.util.List;

/**
 * @author XiongKai
 * @version 0.0.1
 * @since 2016年11月20日 下午12:34:44
 */
@Service
public class DicServiceImpl extends ServiceImpl<Dic, DicMapper> implements DicService {
    public DicServiceImpl(DicMapper mapper) {
        super(mapper);
    }

    @Override
    public List<Dic> queryChildren(String pId) {
        return mapper.queryChildren(pId);
    }
}
