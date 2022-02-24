package com.gitee.fubluesky.vea.system.role.service;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.RoleMenu;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface IRoleMenuService extends IBaseService<RoleMenu> {

	/**
	 * 根据角色id 获取菜单ID列表
	 * @param id 角色id
	 * @return 菜单ID列表
	 */
	List<Long> queryMenuIdList(Long id);

	/**
	 * 保存/修改
	 * @param roleId 角色id
	 * @param menuIdList 菜单ID列表
	 */
	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	/**
	 * 根据角色ID数组，批量删除
	 * @param roleIds 角色ID数组
	 * @return {@code Boolean} 是否成功
	 */
	Boolean deleteBatch(Long[] roleIds);

	/**
	 * 删除角色下菜单
	 * @param roleId 角色ID
	 * @param menuIds 菜单ID列表
	 */
	void removeRoleMenu(Long roleId, List<Long> menuIds);

}
