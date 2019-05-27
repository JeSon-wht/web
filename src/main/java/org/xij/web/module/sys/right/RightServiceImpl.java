package org.xij.web.module.sys.right;

import org.springframework.stereotype.Service;
import org.xij.web.module.base.ServiceImpl;

@Service
public class RightServiceImpl extends ServiceImpl<Right, RightMapper> implements RightService {
    public RightServiceImpl(RightMapper mapper) {
        super(mapper);
    }

    @Override
    public Integer save(Right right) {
        return super.save(right);
    }
}
