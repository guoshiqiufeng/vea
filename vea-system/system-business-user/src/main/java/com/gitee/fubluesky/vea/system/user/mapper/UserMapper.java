package com.gitee.fubluesky.vea.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.fubluesky.vea.system.api.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 获取角色下 用户列表
	 * @param id ownerId
	 * @param roleCode 角色编码
	 * @return
	 */
	List<User> findUserList(@Param("id") Long id, @Param("roleCode") String roleCode);

}
