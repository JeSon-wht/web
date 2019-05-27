package org.xij.web.module.sys.role;

import org.xij.web.module.base.Service;
import org.xij.web.module.sys.right.Right;
import org.xij.web.module.sys.user.User;

import java.util.List;

public interface RoleService extends Service<Role> {
    /**
     * 得到角色信息列表
     * @param role 角色信息
     * @return 角色信息列表
     */
    List<Role> getRoleList(Role role);

    /**
     * 根据id更新角色的状态
     * @param id 角色id
     */
    void updateStatus(String id);

    /**
     * 通过id查找角色
     * @param id 角色的id
     * @return 角色的信息
     */
    Role findRoleById(String id);

    /**
     * 保存角色
     * @param role 角色信息
     */
    void saveRole(Role role);

    /**
     * 通过角色的id得到权限列表
     * @param roleId 角色id
     * @return 角色权限
     */
    List<Right> getRightList(String roleId);

    /**
     * 通过角色id得到该角色未拥有的权限列表
     * @param roleId 角色的id
     * @return 权限列表
     */
    List<Right> getOtherRightList(String roleId);

    /**
     * 增加权限
     * @param roleId 角色的id
     * @param rightIds 权限的id
     */
    void addRoleRight(String roleId, String[] rightIds);

    /**
     * 取消权限
     * @param roleId 角色id
     * @param rightId 权限id
     */
    void cancelRight(String roleId, String[] rightId);

    /**
     * 根据角色id查找用户列表
     * @param roleId 角色id
     * @return 查找的用户列表
     */
    List<User> getUserList(String roleId);

    /**
     * 查找其他用户列表
     * @param roleId 角色id
     * @return 其他用户的列表
     */
    List<User> getOtherUserList(String roleId);

    /**
     * 给角色分配用户
     * @param roleId  角色的id
     * @param userIds 用户的id
     */
    void addRoleUser(String roleId, String[] userIds);

    /**
     * 取消用户
     * @param roleId 角色的id
     * @param userIds 用户的id
     */
    void cancelUser(String roleId, String[] userIds);
}
