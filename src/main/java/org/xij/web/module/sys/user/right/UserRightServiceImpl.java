package org.xij.web.module.sys.user.right;

import org.springframework.stereotype.Service;
import org.xij.web.module.sys.right.Right;

import java.util.List;

@Service
public class UserRightServiceImpl implements UserRightService {
    private UserRightMapper mapper;

    public UserRightServiceImpl(UserRightMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Right> query(String userId, String own, String name, String type) {
        return mapper.query(userId, own, name, type);
    }

    @Override
    public Integer grant(String userId, List<String> rightIds) {
        return mapper.insert(userId, rightIds);
    }

    @Override
    public Integer cancel(String userId, List<String> rightIds) {
        return mapper.delete(userId, rightIds);
    }
}
