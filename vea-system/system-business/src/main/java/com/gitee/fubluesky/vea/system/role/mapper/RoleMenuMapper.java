package com.gitee.fubluesky.vea.system.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.fubluesky.vea.system.api.domain.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	/**
	 * 获取角色菜单列表
	 * @param roleId 角色id
	 * @return 菜单id 数组
	 */
	List<Long> queryMenuIdList(@Param("roleId") Long roleId);

}
