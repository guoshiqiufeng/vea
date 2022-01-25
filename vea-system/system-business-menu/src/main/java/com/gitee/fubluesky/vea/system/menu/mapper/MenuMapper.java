package com.gitee.fubluesky.vea.system.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.fubluesky.vea.system.api.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 获取组菜单列表
	 * @param groupId 组id
	 * @return 菜单列表
	 */
	List<Menu> queryMenuList(@Param("groupId") Long groupId);

	/**
	 * 获取用户所有菜单id列表
	 * @param userId 用户id
	 * @return 菜单id列表
	 */
	List<Long> queryUserAllMenuIdList(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 * @param userId 用户id
	 * @return 菜单id数组
	 */
	List<Long> queryUserAllMenuId(@Param("userId") Long userId);

	/**
	 * 查询用户的所有权限
	 * @param userId 用户id
	 * @return 权限
	 */
	List<String> queryUserAllPerms(@Param("userId") Long userId);

}
