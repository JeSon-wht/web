package org.xij.web.module.sys.menu;

import org.springframework.stereotype.Service;
import org.xij.web.module.base.ServiceImpl;

import java.util.List;

/**
 * @author XiongKai
 * @version 0.0.1
 * @since 2016年12月6日 下午11:21:22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<Menu, MenuMapper> implements MenuService {
    public MenuServiceImpl(MenuMapper mapper) {
        super(mapper);
    }

    @Override
    public List<Menu> queryChildren(String pId) {
        return mapper.queryChildren(pId);
    }
}
