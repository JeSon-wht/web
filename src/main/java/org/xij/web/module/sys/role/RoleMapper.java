package org.xij.web.module.sys.role;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.base.Mapper;
import org.xij.web.module.sys.right.Right;
import org.xij.web.module.sys.user.User;

import java.util.List;

@MybatisMapper
public interface RoleMapper extends Mapper<Role> {
    /**
     * 得到角色信息列表
     * @param role 角色信息
     * @return 返回角色列表的信息
     */
    List<Role> getList(Role role);

    /**
     * 根据角色id来进行更改角色状态,实现删除功能
     * @param id 角色id
     * @return 受影响的行数
     */
    Integer updateStatus(String id);

    /**
     * 根据角色id查找角色信息
     * @param id 角色id
     * @return 角色信息
     */
    Role findRoleById(String id);

    /**
     * 更改角色信息
     * @param role 角色信息
     * @return 受影响的行数
     */
    Integer editRole(Role role);

    /**
     * 根据角色id查找对应已有的权限列表
     * @param roleId 角色的id
     * @return 返回该角色已有的权限列表
     */
    List<Right> getRoleRightList(String roleId);

    /**
     * 根据角色id查找其他没有的权限
     * @param roleId 角色id
     * @return 该角色没有的权限列表
     */
    List<Right> getOtherRoleRightList(String roleId);

    /**
     * 给角色增加权限
     * @param roleId 角色id
     * @param rightIds 权限id
     * @return 受影响的行数判断成功还是失败
     */
    Integer addRoleRight(@Param("roleId") String roleId, @Param("rightIds") String[] rightIds);

    /**
     * 取消角色的权限
     * @param roleId 角色id
     * @param rightIds 权限id
     * @return 受影响的行数
     */
    Integer cancelRight(@Param("roleId") String roleId, @Param("rightIds") String[] rightIds);

    /**
     * 根据角色的id查找用户列表
     * @param roleId
     * @return
     */
    List<User> getUserList(String roleId);

    /**
     * 根据角色的id查找其他用户列表
     * @param roleId 角色的id
     * @return 其他用户的列表
     */
    List<User> getOtherUserList(String roleId);

    /**
     * 分配用户
     * @param roleId 角色的id
     * @param userIds 用户的id
     * @return 受影响的行数判断成功与否
     */
    Integer addRoleUser(@Param("roleId") String roleId, @Param("userIds") String[] userIds);

    /**
     * 取消用户
     * @param roleId 角色的id
     * @param userIds 用户的id
     * @return 受影响的行数来判断成功与否
     */
    Integer cancelUser(@Param("roleId") String roleId, @Param("userIds") String[] userIds);

    Integer checkRepeat(String name);
}
