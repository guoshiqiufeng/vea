package com.gitee.fubluesky.vea.system.department.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gitee.fubluesky.vea.system.api.domain.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-06
 */
public interface DepartmentMapper extends BaseMapper<Department> {

	/**
	 * 获取当前用户部门
	 * @param queryWrapper
	 * @return 部门列表
	 */
	List<Department> findDepartment(@Param(Constants.WRAPPER) QueryWrapper<Department> queryWrapper);

}
