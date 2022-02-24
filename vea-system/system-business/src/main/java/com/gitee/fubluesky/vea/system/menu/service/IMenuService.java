package com.gitee.fubluesky.vea.system.menu.service;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.Menu;
import com.gitee.fubluesky.vea.system.menu.vo.MenuNavVO;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface IMenuService extends IBaseService<Menu> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @return 菜单列表
	 */
	List<Menu> queryListParentId(Long parentId);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList 用户菜单ID
	 * @return 菜单列表
	 */
	List<Menu> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 获取用户菜单
	 * @param userId 用户id
	 * @return 菜单列表
	 */
	List<Menu> getUserMenuList(Long userId);

	/**
	 * 获取不包含按钮的菜单列表
	 * @return 菜单列表
	 */
	List<Menu> queryNotButtonList();

	/**
	 * 获取组菜单列表
	 * @param groupId 组id
	 * @return 菜单列表
	 */
	List<Menu> queryMenuList(Long groupId);

	/**
	 * 获取组菜单列表
	 * @param groupId 组id
	 * @return 菜单列表
	 */
	List<Menu> getGroupMenuList(Long groupId);

	/**
	 * 获取导航数据
	 * @return 导航数据
	 */
	MenuNavVO getNavData();

	/**
	 * 获取所有权限列表
	 * @return 权限列表
	 */
	List<String> queryAllPerm();

	/**
	 * 获取用户所有权限列表
	 * @param userId 用户id
	 * @return 权限列表
	 */
	List<String> queryUserAllPerm(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 * @param userId 用户id
	 * @return 菜单id数组
	 */
	List<Long> queryUserAllMenuId(Long userId);

	/**
	 * 删除组菜单缓存
	 * @param groupId 组id
	 */
	void deleteGroupMenuListCache(Long groupId);

	/**
	 * 删除菜单导航缓存
	 */
	void deleteMenuNavCache();

}
