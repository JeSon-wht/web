package org.xij.web.module.sys.role.right;

import org.springframework.stereotype.Service;
import org.xij.web.module.sys.menu.MenuMapper;
import org.xij.web.module.sys.right.Right;

import java.util.List;

@Service
public class RoleRightServiceImpl implements RoleRightService {
    private RoleRightMapper mapper;
    private MenuMapper menuMapper;

    public RoleRightServiceImpl(RoleRightMapper mapper, MenuMapper menuMapper) {
        this.mapper = mapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public List<Right> query(String roleId, String own, String name, String type) {
        return mapper.query(roleId, own, name, type);
    }

    @Override
    public Integer grant(String roleId, List<String> rightIds) {
        return mapper.insert(roleId, rightIds);
    }

    @Override
    public Integer cancel(String roleId, List<String> rightIds) {
        return mapper.delete(roleId, rightIds);
    }
}
