package com.gitee.fubluesky.vea.system.department.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gitee.fubluesky.vea.system.api.domain.DepartmentUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门用户表 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-06
 */
public interface DepartmentUserMapper extends BaseMapper<DepartmentUser> {

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param wrapper 查询对象
	 * @return DeDepartmentUser
	 */
	IPage<DepartmentUser> findPage(IPage<DepartmentUser> page,
			@Param(Constants.WRAPPER) QueryWrapper<DepartmentUser> wrapper);

}
