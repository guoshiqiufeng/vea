package com.gitee.fubluesky.vea.system.group.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.fubluesky.vea.system.api.domain.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户组 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface GroupMapper extends BaseMapper<Group> {

	/**
	 * 获取用户组
	 * @param userId 用户id
	 * @param type 用户类型
	 * @return 用户组
	 */
	List<Group> findUserGroups(@Param("userId") Long userId, @Param("type") Integer type);

}
