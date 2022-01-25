package com.gitee.fubluesky.vea.system.department.service;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.Department;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
public interface IDepartmentService extends IBaseService<Department> {

	/**
	 * 判断当前用户部门
	 * @param userId 用户id
	 * @return 部门
	 */
	Department getUserDepartment(Long userId);

}
