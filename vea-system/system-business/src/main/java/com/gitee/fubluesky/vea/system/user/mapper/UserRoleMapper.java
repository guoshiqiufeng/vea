package com.gitee.fubluesky.vea.system.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gitee.fubluesky.vea.system.api.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

	/**
	 * 获取用户角色列表
	 * @param queryWrapper 查询对象
	 * @return 用户角色列表
	 */
	List<UserRole> findUserRoleList(@Param(Constants.WRAPPER) QueryWrapper<UserRole> queryWrapper);

	/**
	 * 获取角色id
	 * @param userId 用户id
	 * @return 角色id数组
	 */
	List<Long> findRoleIdList(@Param("userId") Long userId);

	/**
	 * 获取用户角色
	 * @param userId 用户id
	 * @return 角色
	 */
	List<String> findRoleTypesByUserId(@Param("userId") Long userId);

}
