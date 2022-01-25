package com.gitee.fubluesky.vea.system.role.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.fubluesky.vea.system.api.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 获取角色列表
	 * @param queryWrapper 查询条件
	 * @return 角色列表
	 */
	List<Role> findRoleList(@Param(Constants.WRAPPER) QueryWrapper<Role> queryWrapper);

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param queryWrapper 查询条件
	 * @return 角色列表
	 */
	IPage<Role> queryPage(Page<Role> page, @Param(Constants.WRAPPER) Wrapper<Role> queryWrapper);

}
