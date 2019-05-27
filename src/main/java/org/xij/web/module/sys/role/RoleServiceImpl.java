package org.xij.web.module.sys.role;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xij.util.Id;
import org.xij.util.StringManager;
import org.xij.util.XIJException;
import org.xij.web.core.AuthContext;
import org.xij.web.core.AuthInfo;
import org.xij.web.module.base.BusinessEntity;
import org.xij.web.module.base.ServiceImpl;
import org.xij.web.module.sys.right.Right;
import org.xij.web.module.sys.user.User;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<Role,RoleMapper> implements RoleService{
    private static final StringManager MANAGER = StringManager.getManager(ServiceImpl.class);
    public RoleServiceImpl(RoleMapper mapper){
        super(mapper);
    }

    @Override
    public List<Role> getRoleList(Role role) {
        return mapper.getList(role);
    }

    @Override
    public void updateStatus(String id) {
        mapper.updateStatus(id);
    }

    @Override
    public Role findRoleById(String id) {
        Role role = mapper.findRoleById(id);
        if(role == null){
            throw new RuntimeException("未找到该角色信息，可能已经被修改或者已经被删除");
        }else{
            return role;
        }
    }

    @Override
    public void saveRole(Role role) {
        mapper.editRole(role);
    }

    @Override
    public List<Right> getRightList(String roleId) {
        return mapper.getRoleRightList(roleId);
    }

    @Override
    public List<Right> getOtherRightList(String roleId) {
        return mapper.getOtherRoleRightList(roleId);
    }

    @Override
    public void addRoleRight(String roleId, String[] rightIds) {
        mapper.addRoleRight(roleId,rightIds);
    }

    @Override
    public void cancelRight(String roleId, String[] rightId) {
        mapper.cancelRight(roleId,rightId);
    }

    @Override
    public List<User> getUserList(String roleId) {
        return mapper.getUserList(roleId);
    }

    @Override
    public List<User> getOtherUserList(String roleId) {
        return mapper.getOtherUserList(roleId);
    }

    @Override
    public void addRoleUser(String roleId, String[] userIds) {
        mapper.addRoleUser(roleId,userIds);
    }

    @Override
    public void cancelUser(String roleId, String[] userIds) {
        mapper.cancelUser(roleId,userIds);
    }

    @Override
    @Transactional
    public Integer save(Role role) {
        Integer row = mapper.checkRepeat(role.getName());
        if (row > 0) {
            throw new RuntimeException("该角色名称已存在!");
        }

        if (role.getId() != null) {
            XIJException.throwEx(1, MANAGER.getString("pk", new Object[]{role.getClass().getName(), role.getId()}));
        }

        role.setId(Id.generateId());
        if (role instanceof BusinessEntity) {
            BusinessEntity entity = (BusinessEntity)role;
            AuthInfo authInfo = AuthContext.get();
            entity.setCreateBy(authInfo.getUserId());
            entity.setCreateDept(authInfo.getDeptId());
        }

        return mapper.insert(role);
    }

    @Override
    @Transactional
    public Integer update(Role role) {
        if (!role.getName().equals("")){
            Integer row = mapper.checkRepeat(role.getName());
            if (row > 0) {
                throw new RuntimeException("该角色名称已存在!");
            }
        }

        if (role instanceof BusinessEntity) {
            BusinessEntity entity = (BusinessEntity)role;
            AuthInfo authInfo = AuthContext.get();
            entity.setUpdateBy(authInfo.getUserId());
            entity.setUpdateDept(authInfo.getDeptId());
            entity.setUpdateTime(new Date());
        }

        Integer num = mapper.update(role);
        if (num != 1) {
            XIJException.throwEx(53, MANAGER.getString("update", new Object[]{num}));
        }

        return num;
    }
}
