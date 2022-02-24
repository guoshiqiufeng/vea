package com.gitee.fubluesky.vea.system.role.service;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.Role;
import com.gitee.fubluesky.vea.system.api.enums.RoleEnum;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface IRoleService extends IBaseService<Role> {

	/**
	 * 获取用户角色列表
	 * @param userId 用户id
	 * @return 角色列表
	 */
	List<Role> findListByUser(Long userId);

	/**
	 * 获取部门角色列表
	 * @param groupId 组id
	 * @param type 组类别
	 * @return 角色列表
	 */
	List<Role> findRoleList(Long groupId, Integer type);

	/**
	 * 获取用户角色列表
	 * @param groupId 组id
	 * @return 角色列表
	 */
	List<Role> findListByGroupId(Long groupId);

	/**
	 * 获取组角色
	 * @param groupId 组id
	 * @param roleEnum 角色
	 * @return 角色列表
	 */
	List<Role> findGroupRoles(Long groupId, RoleEnum roleEnum);

	/**
	 * 删除组下组 角色菜单数据
	 * @param groupId 组id
	 * @param menuIds 菜单id 列表
	 */
	void deleteRoleMenuData(Long groupId, List<Long> menuIds);

}
